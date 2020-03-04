package com.xs.enums;

import com.xs.exception.ICustomizeErrorCode;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"未找到相关问题，要不宁换一个？"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复。"),
    NO_LOGIN(2003,"当前操作需要登陆，请先进行登录。"),
    SYSTEM_ERROR(2004,"服务器炸了，宁先等一下吧"),
    TYPE_PARAM_WRONG(2005,"请选择正确的问题或者评论进行回复"),
    COMMENT_NOT_FOUNT(2006,"该评论不存在或已被删除"),
    CONTENT_IS_EMPTY(2007,"输入内容不能为空");

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;


    CustomizeErrorCode(Integer code,String message){
        this.message=message;
        this.code=code;
    }
}
