package com.snowruin.mybatis.Excutor;

import com.snowruin.mybatis.Mapper.Function;

/**
 * @ClassName Excutor
 * @Description TODO  执行器
 * @Author zxm
 * @Date 2018/11/28 10:40
 * @Version 1.0
 **/
public interface Executor {
    Object execute(Function function, Object[] params);
}
