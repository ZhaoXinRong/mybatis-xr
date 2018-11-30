package com.snowruin.mybatis.Sql;

import com.snowruin.mybatis.Mapper.Function;
import com.snowruin.mybatis.enums.EnumSqlParamType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName SqlParserBean
 * @Description TODO
 * @Author zxm
 * @Date 2018/11/30 16:54
 * @Version 1.0
 **/

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class SqlParserBean {
    private int order = 0;
    private List<Object> params;
    private EnumSqlParamType sqlParamType;
}
