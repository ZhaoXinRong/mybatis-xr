package com.snowruin.mybatis.exception;

/**
 * @ClassName MybatisException
 * @Description TODO
 * @Author zxm
 * @Date 2018/11/30 18:09
 * @Version 1.0
 **/
public class MybatisException extends  RuntimeException {

    public MybatisException() {
        super();
    }

    public MybatisException(String message) {
        super(message);
    }

    public MybatisException(String message, Throwable cause) {
        super(message, cause);
    }
}
