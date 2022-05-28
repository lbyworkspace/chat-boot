package com.lby.chat.ws;

import com.google.gson.Gson;
import com.lby.chat.dao.UserDao;
import com.lby.chat.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: laishao
 * Date: 2022/5/21
 */
@Component
@Slf4j
@ServerEndpoint(value = "/Global/{account}")
public class GlobalSocket {

    public static ConcurrentHashMap<String,GlobalSocket> GlobalMap = new ConcurrentHashMap<>();
    public Session session;
    private String account;

    private static UserDao userDao;

    @Autowired
    public void setChatService(UserDao userDao) {
        GlobalSocket.userDao = userDao;
    }


    @OnOpen
    public void onOpen(Session session,@PathParam("account") String account){
        this.session = session;
        this.account = account;
        GlobalMap.put(account,this);
        log.info(account+"进入聊天室,当前聊天室人数为"+GlobalMap.size());
        GlobalSend(false);
    }

    @OnClose
    public void onClose(){
        GlobalMap.remove(account);
        log.info(account+"离开聊天室,当前聊天室人数为"+GlobalMap.size());
        GlobalSend(true);

    }

    @OnError
    public void OnError(Throwable throwable){
        throwable.printStackTrace();
    }


    private void GlobalSend(Boolean isRemove){
        List<User> users = new ArrayList<>();
        GlobalMap.keySet().forEach(key->{
            User user = userDao.findByAccount(key).orElse(null);
            users.add(user);
        });
        GlobalMap.keySet().forEach(key->{
            if(key==account&&isRemove) return;
            String json = new Gson().toJson(users);
            GlobalMap.get(key).session.getAsyncRemote().sendText(json);
        });
    }

}
