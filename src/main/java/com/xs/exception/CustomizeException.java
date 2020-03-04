package com.xs.exception;

import lombok.Data;

/**
 *自定义异常类
 */
public class CustomizeException extends RuntimeException{
    private String message;//错误信息
    private Integer code;//错误码

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code=errorCode.getCode();
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}