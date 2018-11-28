package com.snowruin.mybatis.demo.dao;

import com.snowruin.mybatis.demo.entity.User;

import java.util.List;

/**
 * @ClassName UserMapper
 * @Description TODO
 * @Author zxm
 * @Date 2018/11/27 13:52
 * @Version 1.0
 **/
public interface UserMapper {

    public List<User> selectList();

    public User getUserById(Integer id);

}
