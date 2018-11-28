package com.snowruin;

import com.snowruin.mybatis.demo.dao.UserMapper;
import com.snowruin.mybatis.demo.entity.User;
import com.snowruin.mybatis.session.SqlSession;

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
        User user =  mapper.getUserById(0);
        System.out.println(user);
    }
}
