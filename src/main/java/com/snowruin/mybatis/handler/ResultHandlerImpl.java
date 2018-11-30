package com.snowruin.mybatis.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.snowruin.mybatis.util.MapUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * @ClassName ResultHandlerImpl
 * @Description TODO
 * @Author zxm
 * @Date 2018/11/28 13:06
 * @Version 1.0
 **/
public class ResultHandlerImpl extends  AbstractResultHandler {

    @Override
    protected <T>Map mapHandler(ResultSet resultSet) {
        try {
            return resultSetMap(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(resultSet);
        }
        return null;
    }

    @Override
    protected <T> List<Map<String, Object>> listHandler(ResultSet resultSet) {
        try {
            return resultList(resultSet,null);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(resultSet);
        }
        return null;
    }

    @Override
    protected <T> List<T> listHandler(ResultSet resultSet, Class<T> clazz) {
        try {
            return resultList(resultSet,clazz);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(resultSet);
        }
        return null;
    }

    @Override
    protected <T> T pojoHandler(ResultSet resultSet, Class<?> clazz) {
        if(!"void" .equals(clazz.getSimpleName())  ){
            try {
                T o = (T) clazz.newInstance();
                Map<String,Object> map = resultSetMap(resultSet);
                return MapUtils.mapToObject(map,o);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                close(resultSet);
            }
            return null;
        }
        return null;
    }


    private Map resultSetMap(ResultSet resultSet) throws  SQLException{
        Map<String, Object> resultMap = Maps.newHashMap();
        if(resultSet.next()){
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i= 1;i<=columnCount;i++){
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(i);
                resultMap.put(columnName,value);
            }
            return resultMap;
        }
        return null;

    }

    private <T>List<T> resultList(ResultSet resultSet,Class clazz)  throws  SQLException{

        if(clazz == null){
           return (List<T>) resultList(resultSet);
        }else{
            return resultListPojo(resultSet,clazz);
        }
    }

    public <T>List<T> resultList(ResultSet resultSet) throws  SQLException{
        ResultSetMetaData metaData = resultSet.getMetaData();
        List<String> columnNames = Lists.newArrayList();
        List<Map<String,Object>> resultList =   Lists.newArrayList();
        int columnCount =  metaData.getColumnCount();
        for (int i = 0; i < columnCount; i++){
            columnNames.add(metaData.getColumnName(i+1));
        }

        Map<String,Object> map =  Maps.newHashMap();
        while (resultSet.next()){
            map.clear();
            for (int i = 0; i < columnCount ; i++) {
                  String columnName = columnNames.get(i);
                  Object value = resultSet.getObject(columnName);
                  map.put(columnName,value);
            }
            resultList.add(map);
        }
        return (List<T>) resultList;
    }

    public <T> List<T> resultListPojo(ResultSet resultSet,Class<T> clazz){
        try {
            List<T> resultList = Lists.newArrayList();

            List<Map<String,Object>> maps = resultList(resultSet);
            T t = null;
            for (Map<String,Object> map : maps){
                t = clazz.newInstance();
                T t1 = MapUtils.mapToObject(map,t);
                resultList.add(t1);
            }
            return resultList;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
