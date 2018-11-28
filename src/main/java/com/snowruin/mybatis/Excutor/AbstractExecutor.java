package com.snowruin.mybatis.Excutor;

import com.google.common.collect.Lists;
import com.snowruin.mybatis.Mapper.Function;
import com.snowruin.mybatis.session.Configuration;
import com.snowruin.mybatis.util.SqlUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName AbstractExecutor
 * @Description TODO 抽象执行器
 * @Author zxm
 * @Date 2018/11/28 10:43
 * @Version 1.0
 **/
public abstract class AbstractExecutor implements Executor {

    private Configuration configuration = new Configuration();

    @Override
    public Object execute(Function function, Object[] params) {
        Object result = null;
        Connection connection = this.getConnection();
        PreparedStatement preparedStatement= null;
        try {
            SqlParser parser =  SqlUtils.sqlParamsParse(function.getSql());
            function.setSql(parser.getSql());
            preparedStatement = connection.prepareStatement(parser.getSql().trim());

            int order = parser.getOrder();
            for (int i = 1;i<=order;i++ ){
                preparedStatement.setObject(i,params[i-1]);
            }

            String sqlType = function.getSqlType();

            switch (sqlType){
                case "select" :
                    List<Object> list = selectList(function,params,preparedStatement);
                    result = getResultFromList(list,function,params,preparedStatement);
                    break;
                case "update" :
                    result = update(function,params,preparedStatement);
                    break;
                case "delete" :
                    result = delete(function,params,preparedStatement);
                    break;
                case "insert" :
                    result = insert(function,params,preparedStatement);
                    break;
                default:
                    throw  new RuntimeException("请检查Mapper中配置是否正确");
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    protected  abstract  <T> T selectOne(Function function,Object [] params,PreparedStatement ps);

    protected  abstract  <T> List<T> selectList(Function function,Object [] params,PreparedStatement ps);

    protected  abstract  <T> List<java.util.Map<String,Object>> selectListMap(Function function,Object [] params,PreparedStatement ps);

    protected  abstract  <T> java.util.Map<String,Object> selectMap(Function function,Object [] params,PreparedStatement ps);


    protected  abstract  int update(Function function,Object [] params,PreparedStatement ps);

    protected  abstract  int delete(Function function,Object [] params,PreparedStatement ps);

    protected  abstract  int insert(Function function,Object [] params,PreparedStatement ps);


    private Connection getConnection(){
        return configuration.build("mybatis-config.xml");
    }


    private Object getResultFromList(List<Object> list,Function function,Object [] params,PreparedStatement ps) throws IllegalAccessException {
        Object value = null;
        try {
            Class  targetType = Class.forName(function.getResultType());
            if(targetType .isAssignableFrom(List.class)){
                List<java.util.Map> mapList =  Lists.newArrayList();
                Object obj = list.get(0);
                if(obj.getClass().getSimpleName().equals("Map")){
                    value = selectListMap(function,params,ps);
                }else{
                    value = list;
                }
            }else{
                if(list != null && list.size() == 1){
                    Object obj = list.get(0);
                    if(obj.getClass().getSimpleName().equals("Map")){
                        value = this.selectMap(function,params,ps);
                    }else{
                        value = this.selectOne(function,params,ps);
                    }
                }
            }
            return value;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
