package com.github.yt.web.test.exception;

/**
 * 异常枚举
 *
 * @author liujiasheng
 */
public enum MyBusinessExceptionEnum {

    // business
    CODE_1001("业务异常，{0}"),
    CODE_1002("参数错误:{0}"),
    CODE_1003("参数错误"),
    CODE_1004("已知异常"),
    ;

    private String message;
    private String description;

    MyBusinessExceptionEnum(String message) {
        this.message = message;
    }

    MyBusinessExceptionEnum(String message, String description) {
        this.message = message;
        this.description = description;
    }

    public String getMessage() {
        return message;
    }
}
