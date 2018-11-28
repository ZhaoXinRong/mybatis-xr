package com.snowruin.mybatis.session;

import com.snowruin.mybatis.Excutor.Executor;
import com.snowruin.mybatis.Excutor.AbstractExecutor;
import com.snowruin.mybatis.Excutor.SimpleExecutor;
import com.snowruin.mybatis.Mapper.Function;
import com.snowruin.mybatis.Mapper.MapperProxy;

import java.lang.reflect.Proxy;

/**
 * @ClassName SqlSession
 * @Description TODO
 * @Author zxm
 * @Date 2018/11/27 14:15
 * @Version 1.0
 **/
public class SqlSession {

    Executor executor = new SimpleExecutor();

    Configuration configuration = new Configuration();

    public Object execute(Function function, Object[] params){
        return executor.execute(function,params);
    }

    public <T> T getMapper(Class clazz){
       return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},
        new MapperProxy(this,configuration));
    }
}
