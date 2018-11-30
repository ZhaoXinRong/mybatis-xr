package com.snowruin.mybatis.Sql;

import com.google.common.collect.Lists;
import com.snowruin.mybatis.Mapper.Function;
import com.snowruin.mybatis.consts.Consts;
import com.snowruin.mybatis.util.StringUtils;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName ObjectSqlParser
 * @Description TODO  PoJO 对象参数sql 解析器
 * @Author zxm
 * @Date 2018/11/30 11:44
 * @Version 1.0
 **/
@Getter
public class ObjectSqlParser implements  BasicSqlParser {

    private Object param;

    private Function function;

    private List<Object> params;

    private int order = 0;

    public ObjectSqlParser(Function function,Object param){
        this . param = param;
        this . function = function;
    }

    public String parser(){
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

        for(String str :  paramsNameList){
            String strName = "";
            if(str .contains( " .")){
                strName = str.replace(str . substring(0,str.lastIndexOf(".")),"");
            }else{
                strName = str;
            }

            Method method = null;
            try {
                method = this.param.getClass().getMethod("get" + strName.substring(0, 1).toUpperCase() + strName.substring(1, strName.length()));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            try {
                Object result =  method.invoke((this.param));
                values.add(result);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        this.params = values;
        return function.getSql();
    }


//    public static void main(String[] args) {
//        String sql = "select * from t_user where username = #{username} and password = #{password}";
//        Pattern pattern = Pattern.compile(Consts.sqlParamsReg);
//        Matcher matcher = pattern.matcher(sql);
//
//        List<String>  paramsNameList = Lists.newArrayList();
//
//        User user = new User().setPassword("999999999").setUsername("李四");
//
//        while(matcher.find()){
//            String group = matcher.group();
//            if(StringUtils.isNotEmpty(group)){
//                group =  group.replace("#{","").replace("}","");
//                paramsNameList.add(group);
//            }
//        }
//
//        sql = matcher.replaceAll("?");
//        List<Object> values = Lists.newArrayList();
//
//        Class<? extends User> userClass = user.getClass();
//
//        for(String str :  paramsNameList){
//            String strName = "";
//            if(str .contains( " .")){
//                strName = str.replace(str . substring(0,str.lastIndexOf(".")),"");
//            }else{
//                strName = str;
//            }
//
//            Method method = null;
//            try {
//                method = userClass.getMethod("get" + strName.substring(0, 1).toUpperCase() + strName.substring(1, strName.length()));
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            }
//            try {
//                Object result =  method.invoke((user));
//                values.add(result);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        }
//    }


}
