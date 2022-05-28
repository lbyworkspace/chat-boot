package com.lby;

import com.lby.chat.dao.UserDao;
import com.lby.chat.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Author: laishao
 * Date: 2022/5/20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class Testclass {

    @Resource
    private UserDao userDao;

    @Test
    public void test1(){
        userDao.save(User.builder().account("admin").password("admin").nickName("laishao").build());
    }
}
