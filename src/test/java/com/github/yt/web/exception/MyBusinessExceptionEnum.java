package com.github.yt.web.exception;

/**
 * 异常枚举
 * @author liujiasheng
 */
public enum MyBusinessExceptionEnum {

    // business
    CODE_1001("业务异常，{0}"),
    CODE_1002("参数错误:{0}"),
    ;

    public int code = 222;
    public String message;
    public String description;
    MyBusinessExceptionEnum(String message) {
        this.message = message;
    }

    MyBusinessExceptionEnum(String message, String description) {
        this.message = message;
        this.description = description;
    }
}
