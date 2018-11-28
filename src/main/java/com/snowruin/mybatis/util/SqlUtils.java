package com.snowruin.mybatis.util;

import com.snowruin.mybatis.Excutor.SqlParser;
import com.snowruin.mybatis.consts.Consts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName SqlUtils
 * @Description TODO
 * @Author zxm
 * @Date 2018/11/28 11:34
 * @Version 1.0
 **/
public class SqlUtils {


    private SqlUtils (){}

    /**
     * 解析sql
     * @param sqlString
     * @return
     */
    public static SqlParser sqlParamsParse(String sqlString){
        if(StringUtils.isEmpty(sqlString)){
            throw  new RuntimeException("sql语句不能为空");
        }

        Pattern compile = Pattern.compile(Consts.sqlParamsReg);
        Matcher matcher = compile.matcher(sqlString);
        int order = 0;
        while(matcher.find()){
           order++;
        }
        sqlString = matcher.replaceAll("?");

        return new SqlParser().setOrder(order).setSql(sqlString);
    }

}
