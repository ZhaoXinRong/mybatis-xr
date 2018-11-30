package com.snowruin.mybatis.Excutor;

import com.snowruin.mybatis.Mapper.Function;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.PreparedStatement;

/**
 * @ClassName ResultExstractBean
 * @Description TODO
 * @Author zxm
 * @Date 2018/11/30 10:34
 * @Version 1.0
 **/
@AllArgsConstructor
@Getter
public class ResultExstractBean {
    private PreparedStatement preparedStatement;
    private Object[] arags;
    private Function function;
}
