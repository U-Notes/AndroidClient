package com.example.unotes.bean;


import lombok.Data;

/**
 * 用户实体类
 *
 * @author witer
 * @date 2023/07/30
 */
@Data
public class User {
    private String username;
    private String account;
    private String icon;
    private String password;
    private String describe;
}
