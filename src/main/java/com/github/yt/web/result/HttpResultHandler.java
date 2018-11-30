package com.github.yt.web.result;

import com.github.yt.commons.exception.BaseErrorException;
import com.github.yt.web.YtWebConfig;
import com.github.yt.commons.exception.BaseException;
import com.github.yt.web.YtWebExceptionEnum;
import org.springframework.stereotype.Component;

/**
 * http请求返回实体处理类
 *
 * @author liujiasheng
 */
public class HttpResultHandler {
    private static BaseResultConfig resultConfig;
    private static BaseResultConfig getResultConfig(){
        if (resultConfig == null) {
            try {
                Class<?> aClass = Class.forName(YtWebConfig.resultClass);
                resultConfig = (BaseResultConfig) aClass.newInstance();
            } catch (Exception e) {
                throw new BaseErrorException(YtWebExceptionEnum.CODE_79, YtWebConfig.resultClass, e);
            }
        }
        return resultConfig;
    }

    public static HttpResultEntity getSuccessSimpleResultBody() {
        return getSuccessSimpleResultBody(null);
    }

    public static HttpResultEntity getSuccessSimpleResultBody(Object result) {
        return getSuccessMoreResultBody(result, false, null);
    }

    public static HttpResultEntity getSuccessMoreResultBody(Object result, Object moreResult) {
        return getSuccessMoreResultBody(result, true, moreResult);
    }

    private static HttpResultEntity getSuccessMoreResultBody(Object result, boolean withMore, Object moreResult) {
        HttpResultEntity resultBody = new HttpResultEntity();
        resultBody.put(getResultConfig().getErrorCodeField(), getResultConfig().getDefaultSuccessCode());
        resultBody.put(getResultConfig().getMessageField(), getResultConfig().getDefaultSuccessMessage());
        resultBody.put(getResultConfig().getResultField(), result);
        if (withMore) {
            resultBody.put(getResultConfig().getMoreResultField(), moreResult);
        }
        return resultBody;
    }


    public static HttpResultEntity getErrorSimpleResultBody() {
        return getErrorSimpleResultBody(null);
    }

    public static HttpResultEntity getErrorSimpleResultBody(BaseException baseException) {
        HttpResultEntity resultBody = new HttpResultEntity();
        if (baseException != null) {
            Exception exception = (Exception) baseException;
            resultBody.put(getResultConfig().getErrorCodeField(), getResultConfig().convertErrorCode(baseException.getErrorCode()));
            resultBody.put(getResultConfig().getMessageField(), exception.getMessage());
            resultBody.put(getResultConfig().getResultField(), baseException.getErrorResult());
        } else {
            resultBody.put(getResultConfig().getErrorCodeField(), getResultConfig().getDefaultErrorCode());
            resultBody.put(getResultConfig().getMessageField(), getResultConfig().getDefaultErrorMessage());
        }
        return resultBody;
    }

}
