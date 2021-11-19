package com.github.yt.web.result;

/**
 * http请求返回体的接口类
 * @author liujiasheng
 */
public interface BaseResultConfig {

    // key

    /**
     * 错误码字段
     * @return 错误码字段
     */
    String getErrorCodeField();
    /**
     * 信息字段
     * @return 信息字段
     */
    String getMessageField();
    /**
     * 结果字段
     * @return 结果字段
     */
    String getResultField();

    /**
     * 更多结果字段
     *
     * @return 更多结果字段
     */
    String getMoreResultField();

    /**
     * 异常栈字段
     *
     * @return 异常栈字段
     */
    default String getStackTraceField() {
        return "stackTrace";
    }


    // default

    /**
     * 默认正常返回码
     *
     * @return 默认正常返回码
     */
    Object getDefaultSuccessCode();

    /**
     * 默认正常返回信息
     * @return 默认正常返回信息
     */
    Object getDefaultSuccessMessage();
    /**
     * 默认异常返回码
     * @return 默认异常返回码
     */
    Object getDefaultErrorCode();
    /**
     * 默认异常返回信息
     * @return 默认异常返回信息
     */
    Object getDefaultErrorMessage();

    // 异常类转换
    /**
     * 异常码
     * @param errorCode 异常枚举的name
     * @return 异常码
     */
    Object convertErrorCode(Object errorCode);



}
