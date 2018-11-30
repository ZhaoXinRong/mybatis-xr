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
    public <T> Map resultMapHandler(ResultSet resultSet)  {
        return mapHandler(resultSet);
    }

    protected  abstract  <T> java.util.Map mapHandler(ResultSet resultSet) ;


    @Override
    public <T> List<T> resultListHandler(ResultSet resultSet, Class<T> clazz) throws SQLException {
        return listHandler(resultSet,clazz);
    }

    @Override
    public <T>List<Map<String, Object>> resultListHandler(ResultSet resultSet){
        return listHandler(resultSet);
    }

    protected abstract <T> List<Map<String, Object>> listHandler(ResultSet resultSet);

    protected abstract <T> List listHandler(ResultSet resultSet, Class<T> clazz);

    @Override
    public <T> T resultPoJoHandler(ResultSet resultSet, Class<?> clazz){
        return pojoHandler(resultSet,clazz);
    }

    protected  abstract  <T> T pojoHandler(ResultSet resultSet, Class<?> clazz);

    @Override
    public Integer resultIntHandler(ResultSet resultSet) {
        try {
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String resultStringHandler(ResultSet resultSet) {
        try {
            if(resultSet.next()){
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close(ResultSet resultSet){
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
