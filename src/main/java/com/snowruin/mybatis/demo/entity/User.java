package com.snowruin.mybatis.demo.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @ClassName User
 * @Description TODO  用户实体类
 * @Author zxm
 * @Date 2018/11/27 13:45
 * @Version 1.0
 **/
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class User {

    private String id;

    private String username;

    private String password;
}
