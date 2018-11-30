package com.snowruin.mybatis.Mapper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.lang.reflect.Method;

/**
 * @ClassName Function
 * @Description TODO 接口下的方法
 * @Author zxm
 * @Date 2018/11/28 9:55
 * @Version 1.0
 **/
@Getter
@Setter
@Accessors(chain = true)
public class Function {

    private String sqlType;

    private String funcName;

    private String sql;

    private String resultType;

    private String parameterType;

    private Class<?> methodReturnType;

    private Method method;

}
