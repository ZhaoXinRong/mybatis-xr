package com.snowruin.mybatis.Excutor;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @ClassName SqlParser
 * @Description TODO sql 解析器
 * @Author zxm
 * @Date 2018/11/28 11:54
 * @Version 1.0
 **/
@Getter
@Setter
@Accessors(chain = true)
public class SqlParser {

    private int order = 0;

    private String sql ;

}
