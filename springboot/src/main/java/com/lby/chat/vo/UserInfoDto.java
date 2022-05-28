package com.lby.chat.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Author: laishao
 * Date: 2022/5/21
 */
@Data
public class UserInfoDto implements Serializable {
    private Long id;

    private String oldpassword;

    private String account;

    private String password;

    private String nickName;

    private String avatarUrl;
}
