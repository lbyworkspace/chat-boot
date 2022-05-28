package com.lby.chat.controller;

import com.google.gson.Gson;
import com.lby.chat.dao.UserDao;
import com.lby.chat.entity.User;
import com.lby.chat.utils.BeanUtils;
import com.lby.chat.vo.UserInfoDto;
import com.lby.chat.ws.GlobalSocket;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Author: laishao
 * Date: 2022/5/20
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserDao userDao;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User dto){
        User user  = userDao.findByAccount(dto.getAccount()).orElseThrow(()->new RuntimeException("用户不存在"));
        if (user.getPassword().equals(dto.getPassword())) {
            if(GlobalSocket.GlobalMap.keySet().contains(dto.getAccount())) throw new RuntimeException("该用户已在别地登录，你无法登录");
            return ResponseEntity.ok("ok");
        }else throw new RuntimeException("密码错误");
    }

    @PostMapping("/register")
    public void register(@RequestBody User dto){
        userDao.findByAccount(dto.getAccount()).ifPresent(res->{
            throw new RuntimeException("用户名已注册");
        });
        userDao.save(BeanUtils.CopyOne(dto, User.class));
    }

    @PostMapping("/update/{type}")
    public void updateInfo(@RequestBody UserInfoDto dto, @PathVariable String type){
        if(type.equals("password")){
            User user  = userDao.findById(dto.getId()).orElseThrow(()->new RuntimeException("用户不存在"));
            if (!user.getPassword().equals(dto.getOldpassword())) throw new RuntimeException("原密码不正确");
        }
        userDao.save(BeanUtils.CopyOne(dto,User.class));
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName = UUID.randomUUID().toString();
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String filePath = "/Users/laishao/Desktop/chat-boot/src/main/resources/upload/";
        File dest = new File(filePath + fileName + fileType);
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new RuntimeException("上传失败");
        }
        Configuration cfg = new Configuration(Region.region2());
        UploadManager uploadManager = new UploadManager(cfg);
        String accessKey = "sYCIyCtgDytolURtNGtMNO4O5We2g-jcPNsMOV34";
        String secretKey = "J8cJhFNl3PGgejOAtw19YAgQTDjSECpWM0DF8vwi";
        String bucket = "hedexin";
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(dest.getPath(), key, upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return "https://rckmj5bw1.hn-bkt.clouddn.com/"+putRet.key;
        } catch (QiniuException ex) {
            throw new RuntimeException(ex);
        }
    }
}
