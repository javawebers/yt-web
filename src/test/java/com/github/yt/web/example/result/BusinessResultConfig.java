package com.github.yt.web.example.result;

import com.github.yt.web.result.BaseResultConfig;

/**
 * http请求返回体的默认实现
 *
 * @author liujiasheng
 */
public class BusinessResultConfig implements BaseResultConfig {

    @Override
    public String getErrorCodeField() {
        return "code";
    }

    @Override
    public String getMessageField() {
        return "msg";
    }

    @Override
    public String getResultField() {
        return "data";
    }

    @Override
    public String getMoreResultField() {
        return "moreResult";
    }

    @Override
    public Object getDefaultSuccessCode() {
        return "200";
    }

    @Override
    public Object getDefaultSuccessMessage() {
        return "操作成功";
    }

    @Override
    public Object getDefaultErrorCode() {
        return "error";
    }

    @Override
    public Object getDefaultErrorMessage() {
        return "系统异常";
    }

    @Override
    public Object convertErrorCode(Object errorCode) {
        return errorCode;
    }
}
