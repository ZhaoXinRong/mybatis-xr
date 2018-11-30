package com.snowruin.mybatis.Sql;

import com.snowruin.mybatis.Mapper.Function;
import com.snowruin.mybatis.enums.EnumSqlParamType;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SqlParser
 * @Description TODO  sql 解析器
 * @Author zxm
 * @Date 2018/11/30 16:52
 * @Version 1.0
 **/
public class SqlParser {

    private SqlParser(){}

    public static final SqlParserBean parser(Function function , Object params[]){
        if(params == null || params.length == 0){
            return new SqlParserBean();
        }

        if(params.length == 1){
            Object param =  params[0];
            if(isBaseType(param) || (param instanceof  List) || param.getClass().isArray()){
                ArraySqlParser arraySqlParser = new ArraySqlParser(function);
                arraySqlParser.parser();
                List<Object> paramsList = new ArrayList<>();
                paramsList.add(param);
                return new SqlParserBean(arraySqlParser.getOrder(),paramsList ,EnumSqlParamType.ARRAY);
            }else if ((param instanceof  java.util.Map)) {
                MapSqlParser mapSqlParser = new MapSqlParser(function, param);
                mapSqlParser.parser();
                return new SqlParserBean(mapSqlParser.getOrder(),mapSqlParser.getParams(),EnumSqlParamType.MAP);
            }else{
                ObjectSqlParser objectSqlParser = new ObjectSqlParser(function,param);
                objectSqlParser.parser();
                return new SqlParserBean(objectSqlParser.getOrder(),objectSqlParser.getParams(),EnumSqlParamType.OBJECT);
            }
        }
        throw  new RuntimeException("改参数不能解析");
    }


    private static final boolean isBaseType(Object object) {
        Class className = object.getClass();
        if (className.equals(java.lang.Integer.class) ||
                className.equals(java.lang.Byte.class) ||
                className.equals(java.lang.Long.class) ||
                className.equals(java.lang.Double.class) ||
                className.equals(java.lang.Float.class) ||
                className.equals(java.lang.Character.class) ||
                className.equals(java.lang.Short.class) ||
                className.equals(java.lang.Boolean.class)) {
            return true;
        }
        return false;
    }

}
