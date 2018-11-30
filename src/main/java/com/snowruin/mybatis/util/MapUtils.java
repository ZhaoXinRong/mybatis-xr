package com.snowruin.mybatis.util;

import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @ClassName MapUtils
 * @Description TODO
 * @Author zxm
 * @Date 2018/11/28 13:51
 * @Version 1.0
 **/
public class MapUtils {

    private MapUtils(){}

    /**
     * map 转 对象
     * @param map
     * @param t
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    public static  <T> T mapToObject(java.util.Map<String,Object> map, T t) throws IllegalAccessException {
        if(isEmpty(map) || isNull(map)){
            return null;
        }

        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields){
            int modifiers = field.getModifiers();
            if(Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)){
                continue;
            }
            field.setAccessible(true);
            field.set(t,map.get(field.getName()));
        }
        return t;
    }

    /**
     * 对象转map
     * @param t
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    public static  <T>java.util.Map<String ,Object> objectToMap(T t) throws IllegalAccessException {
        if(t == null){
            return null;
        }
        java.util.Map map =  Maps.newHashMap();
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(),field.get(t));
        }
        return map;
    }


    public static  boolean isEmpty(java.util.Map<String,Object> map){
        return map.isEmpty();
    }

    public static  boolean isNull(java.util.Map<String,Object> map){
        return map == null;
    }
}
