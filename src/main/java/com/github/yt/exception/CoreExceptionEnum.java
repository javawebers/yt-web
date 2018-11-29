package com.github.yt.exception;

/**
 * 异常枚举
 * @author liujiasheng
 */
public enum CoreExceptionEnum {

    // error
    CODE_79("实例化resultConfig对象异常，{0}"),
    ;

    public String message;
    public String description;
    CoreExceptionEnum(String message) {
        this.message = message;
    }

    CoreExceptionEnum(String message, String description) {
        this.message = message;
        this.description = description;
    }
}
