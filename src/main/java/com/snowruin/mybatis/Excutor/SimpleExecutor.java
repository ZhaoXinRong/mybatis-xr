package com.snowruin.mybatis.Excutor;

import com.snowruin.mybatis.Mapper.Function;
import com.snowruin.mybatis.handler.ResultHandler;
import com.snowruin.mybatis.handler.ResultHandlerImpl;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SimpleExecutor
 * @Description TODO
 * @Author zxm
 * @Date 2018/11/28 15:07
 * @Version 1.0
 **/
@Slf4j
public class SimpleExecutor extends  AbstractExecutor {

    private ResultHandler resultHandler = new ResultHandlerImpl();

    @Override
    protected <T> T selectOne(ResultExstractBean resultExstractBean) {
        logger(resultExstractBean.getFunction(),resultExstractBean.getArags());
        ResultSet resultSet = null;
        try {
            resultSet = resultExstractBean.getPreparedStatement().executeQuery();
            Class<?> methodReturnType = resultExstractBean.getFunction().getMethodReturnType();
             if(methodReturnType .isAssignableFrom(String.class)){
                return (T) resultHandler.resultStringHandler(resultSet);
            }else if(methodReturnType. isAssignableFrom(int.class) || methodReturnType.isAssignableFrom(Integer .class) ) {
                return (T)resultHandler.resultIntHandler(resultSet);
            }else{
                 return resultHandler.resultPoJoHandler(resultSet, resultExstractBean.getFunction().getMethodReturnType());
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected <T> List<T> selectList(ResultExstractBean resultExstractBean) {
        logger(resultExstractBean.getFunction(),resultExstractBean.getArags());
        ResultSet resultSet = null;
        try {
            resultSet = resultExstractBean.getPreparedStatement().executeQuery();
            return (List<T>) resultHandler.resultListHandler(resultSet,Class.forName(resultExstractBean.getFunction().getResultType()));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected <T> List<Map<String, Object>> selectListMap(ResultExstractBean resultExstractBean) {
        logger(resultExstractBean.getFunction(),resultExstractBean.getArags());
        ResultSet resultSet = null;
        try {
            resultSet = resultExstractBean.getPreparedStatement().executeQuery();
            return resultHandler.resultListHandler(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected <T> Map<String, Object> selectMap(ResultExstractBean resultExstractBean) {
        logger(resultExstractBean.getFunction(),resultExstractBean.getArags());
        ResultSet resultSet = null;
        try {
            resultSet = resultExstractBean.getPreparedStatement().executeQuery();
            return resultHandler.resultMapHandler(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected int update(ResultExstractBean resultExstractBean) {
        logger(resultExstractBean.getFunction(),resultExstractBean.getArags());
        try {
            int i  = resultExstractBean.getPreparedStatement().executeUpdate();
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected int delete(ResultExstractBean resultExstractBean) {
        logger(resultExstractBean.getFunction(),resultExstractBean.getArags());
        try {
            int i  = resultExstractBean.getPreparedStatement().executeUpdate();
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected int insert(ResultExstractBean resultExstractBean) {
        logger(resultExstractBean.getFunction(),resultExstractBean.getArags());
        try {
            int i  = resultExstractBean.getPreparedStatement().executeUpdate();
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private void logger(Function function, Object[] params){
        log.info(">>>>>>>>>>>> : {}",function.getSql().trim());
        StringBuilder sb = new StringBuilder();
        if(params != null && params.length > 0){
            for (Object param : params){
                sb.append(param)
                        .append("(")
                        .append(param.getClass().getSimpleName())
                        .append("),");
            }
            log.info(">>>>>>>>>>>>> : {}",sb.toString().length() > 0 ? sb.toString().substring(0,sb.toString().lastIndexOf(",")) : "" );
        }

    }

}
