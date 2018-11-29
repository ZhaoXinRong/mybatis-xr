package com.snowruin;

import com.snowruin.mybatis.demo.dao.UserMapper;
import com.snowruin.mybatis.demo.entity.User;
import com.snowruin.mybatis.enums.EnumMapper;
import com.snowruin.mybatis.session.SqlSession;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        SqlSession sqlSession = new SqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
       // User user =  mapper.getUserById(1);

        List<User> users =  mapper.selectList();
        System.out.println(users);



    }
}
