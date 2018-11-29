package com.snowruin.mybatis.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AbstractResultHandler
 * @Description TODO
 * @Author zxm
 * @Date 2018/11/28 12:59
 * @Version 1.0
 **/
public abstract class AbstractResultHandler implements ResultHandler{

    @Override
    public <T> Map resultMapHandler(List<T> resultList)  {
        return mapHandler(resultList);
    }

    protected  abstract  <T> java.util.Map mapHandler(List<T> resultList) ;


    @Override
    public <T> List<T> resultListHandler(ResultSet resultSet, Class<T> clazz) throws SQLException {
        return listHandler(resultSet,clazz);
    }

    @Override
    public <T>List<Map<String, Object>> resultListHandler(List<T> list){
        return listHandler(list);
    }

    protected abstract <T> List<Map<String, Object>> listHandler(List<T> list);

    protected abstract <T> List listHandler(ResultSet resultSet, Class<T> clazz);

    @Override
    public <T> T resultPoJoHandler(List<T> list, Class<?> clazz){
        return pojoHandler(list,clazz);
    }

    protected  abstract  <T> T pojoHandler(List<T> list, Class<?> clazz);

    @Override
    public int resultIntHandler(Integer result) {
        return result;
    }

    @Override
    public String resultStringHandler(String result) {
        return result;
    }
}
