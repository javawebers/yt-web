package com.github.yt.web;

/**
 * 异常枚举
 * @author liujiasheng
 */
public enum YtWebExceptionEnum {

    // error
    CODE_79("实例化resultConfig对象异常，{0}"),
    ;

    public String message;
    public String description;
    YtWebExceptionEnum(String message) {
        this.message = message;
    }

    YtWebExceptionEnum(String message, String description) {
        this.message = message;
        this.description = description;
    }
}
