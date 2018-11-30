package com.snowruin.mybatis.Sql;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.snowruin.mybatis.Mapper.Function;
import com.snowruin.mybatis.consts.Consts;
import com.snowruin.mybatis.util.StringUtils;
import lombok.Getter;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @ClassName MapSqlParser
 * @Description TODO
 * @Author zxm
 * @Date 2018/11/30 15:33
 * @Version 1.0
 **/
@Getter
public class MapSqlParser implements  BasicSqlParser {

    private Function function;

    private Object param;

    private List<Object> params;

    private int order = 0;

    public MapSqlParser(Function function,Object param){
        this . function = function;
    }


    @Override
    public String parser() {
        if(!(this.param instanceof  java.util.Map)){
            throw  new RuntimeException("解析器错误");
        }

        java.util.Map paramsMap =  (java.util.Map)param;

        Pattern pattern = Pattern.compile(Consts.sqlParamsReg);
        Matcher matcher = pattern.matcher(function.getSql());
        List<String>  paramsNameList = Lists.newArrayList();
        while(matcher.find()){
            String group = matcher.group();
            if(StringUtils.isNotEmpty(group)){
                group =  group.replace("#{","").replace("}","");
                paramsNameList.add(group);
                this.order++;
            }
        }
        function.setSql(matcher.replaceAll("?"));

        List<Object> values = Lists.newArrayList();
        Set<Map.Entry<String, Object>> entries = paramsMap.entrySet();
        for (String str: paramsNameList){
            String key = "";
            if(str .contains(".") ){
                key = str.replace(str . substring(0,str.lastIndexOf(".")),"");
            }else{
                key = str;
            }
            for(Map.Entry<String, Object> entry : entries){
                if(entry.getKey() .equals(key)){
                    values.add(entry.getValue());
                }
            }
        }
        this.params = values;
        return function.getSql();
    }


   /* public static void main(String[] args) {
        String sql = "select * from t_user where username = #{aaaa} and password = #{bbbb}";

        Pattern pattern = Pattern.compile(Consts.sqlParamsReg);
        Matcher matcher = pattern.matcher(sql);

        List<String>  paramsNameList = Lists.newArrayList();
        Map<String, Object> paramsMap = Maps.newHashMap();
        paramsMap.put("aaaa","1111");
        paramsMap.put("bbbb",232234);

        while(matcher.find()){
            String group = matcher.group();
            if(StringUtils.isNotEmpty(group)){
               group =  group.replace("#{","").replace("}","");
               paramsNameList.add(group);
            }
        }

        sql = matcher.replaceAll("?");

        List<Object> values = Lists.newArrayList();

        Set<Map.Entry<String, Object>> entries = paramsMap.entrySet();

        for (String param: paramsNameList){
            String key = "";
            if(param .contains(".") ){
                key = param.replace(param . substring(0,param.lastIndexOf(".")),"");
            }else{
                key = param;
            }
            for(Map.Entry<String, Object> entry : entries){
                if(entry.getKey() .equals(key)){
                    values.add(entry.getValue());
                }
            }
        }

        System.out.println(values);
    }*/
}
