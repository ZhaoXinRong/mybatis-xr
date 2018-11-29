package com.snowruin.mybatis.enums;

import lombok.Getter;

/**
 * @ClassName EnumMapper
 * @Description TODO
 * @Author zxm
 * @Date 2018/11/28 18:06
 * @Version 1.0
 **/
@Getter
public enum  EnumMapper {

    SQL_OPTION("id","resultType","parameterType");

    private String id;

    private String resultType;

    private String parameterType;


    EnumMapper ( String id,String resultType,String parameterType){
        this.id = id ;
        this.resultType = resultType;
        this.parameterType = parameterType;
    }


}
