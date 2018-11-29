package com.github.yt.web.result;

/**
 * http请求返回体的默认实现
 * @author liujiasheng
 */
public class SimpleResultConfig implements BaseResultConfig {


    @Override
    public String getErrorCodeField() {
        return "errorCode";
    }

    @Override
    public String getMessageField() {
        return "message";
    }

    @Override
    public String getResultField() {
        return "result";
    }

    @Override
    public String getMoreResultField() {
        return "moreResult";
    }

    @Override
    public Object getDefaultSuccessCode() {
        return 0;
    }

    @Override
    public Object getDefaultSuccessMessage() {
        return "操作成功";
    }

    @Override
    public Object getDefaultErrorCode() {
        return 1;
    }

    @Override
    public Object getDefaultErrorMessage() {
        return "系统异常";
    }

    @Override
    public Object convertErrorCode(Object errorCode) {
        if (errorCode instanceof String) {
            String strErrorCode = (String)errorCode;
            if (strErrorCode.contains("_")) {
                return Integer.parseInt(strErrorCode.split("_")[1]);
            } else {
                return Integer.parseInt(strErrorCode);
            }
        } else if (errorCode instanceof Integer) {
            return errorCode;
        } else {
            return errorCode;
        }
    }


}
