package com.lby.chat.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: laishao
 * Date: 2022/5/22
 */
@Component
@Slf4j
@ServerEndpoint(value = "/chat/{groupId}/{uid}")
public class ChatRoomSocket {

    private static ConcurrentHashMap<String,ChatRoomSocket> ChatMap = new ConcurrentHashMap<>();
    private Session session;
    private String groupId; // |1-2|
    private Integer uid;

    @OnOpen
    public void onOpen(Session session,@PathParam("groupId") String groupId,@PathParam("uid") Integer uid){
        this.session = session;
        this.groupId = groupId;
        this.uid = uid;
        ChatMap.put(groupId.concat("/"+uid),this);  // |1-2|/1
        log.info(uid+"进入"+groupId+"聊天室,当前ChatSocket人数为"+ChatMap.size());
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
        ChatMap.keySet().forEach(key->{
            if(key.split("/")[0].equals(this.groupId)){
                ChatMap.get(key).session.getAsyncRemote().sendText(message);
            }
        });
    }

    @OnClose
    public void onClose(){
        ChatMap.remove(this.groupId.concat("/"+this.uid));
        log.info(this.uid+"离开"+this.groupId+"聊天室,当前ChatSocket人数为"+ChatMap.size());
    }

    @OnError
    public void OnError(Throwable throwable){
        throwable.printStackTrace();
    }

}
