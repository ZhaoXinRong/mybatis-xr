package com.snowruin.mybatis.Mapper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @ClassName MapperBean
 * @Description TODO
 * @Author zxm
 * @Date 2018/11/28 9:54
 * @Version 1.0
 **/
@Getter
@Setter
@Accessors(chain = true)
public class MapperBean {

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 接口下的所有方法
     */
    private List<Function> list;
}
