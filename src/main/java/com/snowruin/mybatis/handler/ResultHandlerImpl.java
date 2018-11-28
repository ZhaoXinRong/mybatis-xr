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
    protected <T>Map mapHandler(T t) {
        if(t != null){
            try {
                return MapUtils.objectToMap(t);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected <T> List<Map<String, Object>> listHandler(List<T> list) {
        if(list != null && list.size() > 0){
            List<Map<String, Object>> list1 =  Lists.newArrayList();
            try {
                for (T t : list){
                    Map<String, Object> stringObjectMap = MapUtils.objectToMap(t);
                    list1.add(stringObjectMap);
                }
                return list1;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected <T> List<T> listHandler(ResultSet resultSet, Class<T> clazz) {
        try {
            return resultList(resultSet,clazz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected <T> T pojoHandler(List<T> list, Class<?> clazz) {
        T t1 = list.get(0);
        if(t1 instanceof  java.util.Map){
            try {
               return (T) MapUtils.mapToObject((Map)t1,clazz);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return t1;
    }


    private Map resultSetMap(ResultSet resultSet) throws  SQLException{
        Map<String, Object> resultMap = Maps.newHashMap();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i= 1;i<columnCount;i++){
            String columnName = metaData.getColumnName(i);
            Object value = resultSet.getObject(i);
            resultMap.put(columnName,value);
        }
        resultSet.close();
        return resultMap;
    }

    private List resultList(ResultSet resultSet,Class clazz)  throws  SQLException{
        if(clazz == null){
           return  resultList(resultSet);
        }else{
            return resultListPojo(resultSet,clazz);
        }
    }

    public List<Map<String,Object>> resultList(ResultSet resultSet) throws  SQLException{
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
        return resultList;
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
