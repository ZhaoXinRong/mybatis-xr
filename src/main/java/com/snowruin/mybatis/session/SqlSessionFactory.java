package com.snowruin.mybatis.session;

/**
 * @ClassName SqlSessionFactory
 * @Description TODO
 * @Author zxm
 * @Date 2018/12/3 11:58
 * @Version 1.0
 **/
public class SqlSessionFactory {

    public SqlSession openSession(){
      return new SqlSession();
    }

}
