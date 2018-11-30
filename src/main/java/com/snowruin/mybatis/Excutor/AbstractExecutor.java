package com.snowruin.mybatis.Excutor;

import com.snowruin.mybatis.Mapper.Function;
import com.snowruin.mybatis.consts.Consts;
import com.snowruin.mybatis.session.Configuration;
import com.snowruin.mybatis.util.SqlUtils;
import com.snowruin.mybatis.util.StringUtils;

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
            return  handlerResult(new ResultExstractBean(preparedStatement,params,function));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
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


    protected  abstract  <T> T selectOne(ResultExstractBean resultExstractBean);

    protected  abstract  <T> List<T> selectList(ResultExstractBean resultExstractBean);

    protected  abstract  <T> List<java.util.Map<String,Object>> selectListMap(ResultExstractBean resultExstractBean);

    protected  abstract  <T> java.util.Map<String,Object> selectMap(ResultExstractBean resultExstractBean);


    protected  abstract  int update(ResultExstractBean resultExstractBean);

    protected  abstract  int delete(ResultExstractBean resultExstractBean);

    protected  abstract  int insert(ResultExstractBean resultExstractBean);


    private Connection getConnection(){
        return configuration.build("mybatis-config.xml");
    }


    private Object handlerResult(ResultExstractBean resultExstractBean){
        Object result = null;
        String sqlType = resultExstractBean.getFunction().getSqlType();
        switch (sqlType){
            case Consts. SELECT :
                String resultType = resultExstractBean.getFunction().getResultType();
                Class<?> methodReturnType = resultExstractBean.getFunction().getMethodReturnType();
                if(methodReturnType .isAssignableFrom(List.class) ){
                    if(StringUtils.isEmpty(resultType)){
                        String typeName = resultExstractBean.getFunction().getMethod().getGenericReturnType().getTypeName();
                        if(typeName .contains("Map")){
                            result = selectListMap(resultExstractBean);
                        }else{
                            result = selectList(resultExstractBean);
                        }
                    }else{
                        if(resultType . contains("Map")){
                            result = selectListMap(resultExstractBean);
                        }else{
                            result = selectList(resultExstractBean);
                        }
                    }
                }else if(methodReturnType .isAssignableFrom(java.util.Map.class)){
                    result = this.selectMap(resultExstractBean);
                }else if(methodReturnType .isAssignableFrom(String.class)){
                    result = this.selectOne(resultExstractBean);
                }else if(methodReturnType. isAssignableFrom(int.class) || methodReturnType.isAssignableFrom(Integer .class) ){
                    result = selectOne(resultExstractBean);
                }else {
                    result = this.selectOne(resultExstractBean);
                }
                break;
            case Consts . UPDATE :
                result = update(resultExstractBean);
                break;
            case Consts . DELETE :
                result = delete(resultExstractBean);
                break;
            case Consts . INSERT  :
                result = insert(resultExstractBean);
                break;
            default:
                throw  new RuntimeException("请检查Mapper中配置是否正确");
        }
        return result;
    }
}
