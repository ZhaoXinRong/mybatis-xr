package com.snowruin.mybatis.jdbc;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @ClassName Jdbc
 * @Description TODO
 * @Author zxm
 * @Date 2018/12/1 10:04
 * @Version 1.0
 **/
@Getter
@Setter
@Accessors(chain = true)
public class Jdbc {

    private String driverClassName;

    private String username;

    private String password;

    private String url;

    private int poolInitSize = 10;
}
