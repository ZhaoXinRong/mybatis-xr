package com.snowruin.mybatis.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ResultHandler
 * @Description TODO  结果集处理
 * @Author zxm
 * @Date 2018/11/28 12:51
 * @Version 1.0
 **/
public interface ResultHandler {

    <T> java.util.Map resultMapHandler(T t) ;

    <T> List<T> resultListHandler(ResultSet resultSet,Class<T> clazz)  throws SQLException;

    <T> List<Map<String, Object>> resultListHandler(List<T> list);

    <T> T resultPoJoHandler(List<T> list, Class<?> clazz);

    int resultIntHandler(Integer result);

    String resultStringHandler(String result);
}
