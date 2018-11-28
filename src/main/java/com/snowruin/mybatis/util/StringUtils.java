package com.snowruin.mybatis.util;

/**
 * @ClassName StringUtils
 * @Description TODO  String 工具类
 * @Author zxm
 * @Date 2018/11/28 9:43
 * @Version 1.0
 **/
public class StringUtils {
    private  StringUtils(){};

    public static  boolean isEmpty(String str){
        return null==str || "".equals(str);
    }


    public static  boolean isNotEmpty(String str){
        return !StringUtils.isEmpty(str);
    }


}
