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
    protected <T> T selectOne(Function function, Object[] params, PreparedStatement ps) {
        List<T> list = selectList(function, params, ps);
        try {
            return resultHandler.resultPoJoHandler(list,Class.forName(function.getResultType()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected <T> List<T> selectList(Function function, Object[] params, PreparedStatement ps) {
        logger(function, params);
        ResultSet resultSet = null;
        try {
            resultSet = ps.executeQuery();
            return (List<T>) resultHandler.resultListHandler(resultSet,Class.forName(function.getResultType()));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            close(resultSet);
        }
        return null;
    }

    @Override
    protected <T> List<Map<String, Object>> selectListMap(Function function, Object[] params, PreparedStatement ps) {
        List<T> list = selectList(function, params, ps);
        return resultHandler.resultListHandler(list);
    }

    @Override
    protected <T> Map<String, Object> selectMap(Function function, Object[] params, PreparedStatement ps) {
        Object o = selectOne(function, params, ps);
        return resultHandler.resultMapHandler(o);
    }

    @Override
    protected int update(Function function, Object[] params, PreparedStatement ps) {
        logger(function, params);
        try {
            int i  = ps.executeUpdate();
            return resultHandler.resultIntHandler(i);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected int delete(Function function, Object[] params, PreparedStatement ps) {
        logger(function, params);
        try {
            int i  = ps.executeUpdate();
            return resultHandler.resultIntHandler(i);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected int insert(Function function, Object[] params, PreparedStatement ps) {
        logger(function, params);
        try {
            int i  = ps.executeUpdate();
            return resultHandler.resultIntHandler(i);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private void logger(Function function, Object[] params){
        log.info(">>>>>>>>>>>> : {}",function.getSql().trim());
        StringBuilder sb = new StringBuilder();
        for (Object param : params){
            sb.append(param)
                    .append("(")
                    .append(param.getClass().getSimpleName())
                    .append("),");
        }
        log.info(">>>>>>>>>>>>> : {}",sb.toString().length() > 0 ? sb.toString().substring(0,sb.toString().lastIndexOf(",")) : "" );
    }


    private void close(ResultSet resultSet){
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
