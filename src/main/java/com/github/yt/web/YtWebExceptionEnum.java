package com.github.yt.web;

/**
 * 异常枚举
 *
 * @author liujiasheng
 */
public enum YtWebExceptionEnum {

    // business
    CODE_11("参数错误:{0}"),
    CODE_12("参数错误:{0}"),
    CODE_13("上传的文件过大"),

    // error
    CODE_79("实例化resultConfig对象异常，{0}"),
    ;

    private String message;
    private String description;

    YtWebExceptionEnum(String message) {
        this.message = message;
    }

    YtWebExceptionEnum(String message, String description) {
        this.message = message;
        this.description = description;
    }
}
