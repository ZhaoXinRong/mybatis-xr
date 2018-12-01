package com.snowruin;

import com.google.common.collect.Maps;
import com.snowruin.mybatis.demo.dao.UserMapper;
import com.snowruin.mybatis.demo.entity.User;
import com.snowruin.mybatis.enums.EnumMapper;
import com.snowruin.mybatis.session.SqlSession;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
//        User user =  mapper.getUserById("3dcebbc1-6f71-4ebc-aa24-3df58e70d910");
        //List<User> users =  mapper.selectList();

//        Map<String, Object> userMap = mapper.getUserMap();

       // String username = mapper.getUsername();

       // int count = mapper.selectCount();

//       int i =  mapper.insert(new User().
//             setUsername("李四").setPassword("11111").setId(UUID.randomUUID().toString()));
//        int i = mapper.deleteByUsername();

        //User user = mapper.selectA(new User().setUsername("李四1").setPassword("11111"));

//        int 王五 = mapper.updateById(new User().setPassword("222").setUsername("王五").setId("b6616d46-122e-4866-8ca3-356f05861c76"));
//
        HashMap<String, Object> objectObjectHashMap = Maps.newHashMap();
        objectObjectHashMap.put("name","王五");
        objectObjectHashMap.put("pass","222");
        java.util.Map user = mapper.selectMap(objectObjectHashMap);
        System.out.println(user);












    }
}
