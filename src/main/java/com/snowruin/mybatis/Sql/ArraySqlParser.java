package com.snowruin.mybatis.Sql;

import com.snowruin.mybatis.Mapper.Function;
import com.snowruin.mybatis.consts.Consts;
import com.snowruin.mybatis.exception.MybatisException;
import com.snowruin.mybatis.util.StringUtils;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName ArraySqlParser
 * @Description TODO 数组sql 解析器
 * @Author zxm
 * @Date 2018/11/30 15:14
 * @Version 1.0
 **/
@Getter
public class ArraySqlParser implements  BasicSqlParser {

    private int order = 0;
    private Function function;

    public ArraySqlParser(Function function){
        this.function = function;
    }

    @Override
    public String parser() {

        if(StringUtils.isEmpty(function.getSql())){
            throw  new MybatisException("sql语句不能为空");
        }

        Pattern compile = Pattern.compile(Consts.sqlParamsReg);
        Matcher matcher = compile.matcher(function.getSql());
        int order = 0;
        while(matcher.find()){
            order++;
        }
        this.order = order;
        function.setSql(matcher.replaceAll("?")) ;

        return function.getSql();
    }
}
