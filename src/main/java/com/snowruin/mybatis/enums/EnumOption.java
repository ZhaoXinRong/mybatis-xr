package com.snowruin.mybatis.enums;

import lombok.Getter;

/**
 * @ClassName EnumOption
 * @Description TODO
 * @Author zxm
 * @Date 2018/11/28 17:49
 * @Version 1.0
 **/
@Getter
public enum EnumOption {

    SELECT("select","查询"),
    UPDATE("update","更新"),
    DELETE("delete","删除"),
    INSERT("insert","插入");

    private String name;

    private String description;


    EnumOption(String name,String description){
        this.name = name;
        this.description = description;
    }
}
