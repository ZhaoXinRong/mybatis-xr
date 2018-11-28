package com.snowruin.mybatis.Mapper;

import com.snowruin.mybatis.session.Configuration;
import com.snowruin.mybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @ClassName MapperProxy
 * @Description TODO mapper 代理
 * @Author zxm
 * @Date 2018/11/28 10:57
 * @Version 1.0
 **/
public class MapperProxy implements InvocationHandler {

    private SqlSession sqlSession;

    private Configuration configuration;

    public MapperProxy(SqlSession sqlSession,Configuration configuration){
        this.sqlSession = sqlSession;
        this.configuration = configuration;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperBean mapperBean = configuration.readMapper("mapper/UserMapper.xml");
        String methodName = method.getDeclaringClass().getName();

        if(!methodName.equals(mapperBean.getInterfaceName())){
            return null;
        }

        List<Function> list = mapperBean.getList();
        if(list != null && list.size() > 0){
            for(Function function : list){
                if(method.getName() .equals(function.getFuncName())){
                    return sqlSession.execute(function,args);
                }
            }
        }
        return null;
    }
}
