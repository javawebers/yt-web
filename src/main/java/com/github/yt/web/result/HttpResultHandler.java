package com.github.yt.web.result;

import com.github.yt.commons.exception.BaseException;
import com.github.yt.web.YtWebConfig;
import com.github.yt.web.util.SpringContextUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * http请求返回实体处理类
 *
 * @author liujiasheng
 */
public class HttpResultHandler {
    private static BaseResultConfig resultConfig;

    public static BaseResultConfig getResultConfig() {
        if (resultConfig == null) {
            synchronized (HttpResultHandler.class) {
                try {
                    YtWebConfig ytWebConfig = SpringContextUtils.getBean(YtWebConfig.class);
                    resultConfig = ytWebConfig.getResult().getResultConfigClass().newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("实例化 BaseResultConfig 类异常", e);
                }
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

    public static HttpResultEntity getErrorSimpleResultBody(Throwable exception) {
        HttpResultEntity resultBody = new HttpResultEntity();
        if (exception instanceof BaseException) {
            BaseException baseException = (BaseException) exception;
            resultBody.put(getResultConfig().getErrorCodeField(), getResultConfig().convertErrorCode(baseException.getErrorCode()));
            resultBody.put(getResultConfig().getMessageField(), exception.getMessage());
            resultBody.put(getResultConfig().getResultField(), baseException.getErrorResult());
        } else {
            resultBody.put(getResultConfig().getErrorCodeField(), getResultConfig().getDefaultErrorCode());
            resultBody.put(getResultConfig().getMessageField(), getResultConfig().getDefaultErrorMessage());
            resultBody.put(getResultConfig().getResultField(), null);
        }
        // 返回异常堆栈到前端
        YtWebConfig ytWebConfig = SpringContextUtils.getBean(YtWebConfig.class);
        if (ytWebConfig.getResult().isReturnStackTrace()) {
            StringWriter stringWriter = new StringWriter();
            exception.printStackTrace(new PrintWriter(stringWriter, true));
            resultBody.put(getResultConfig().getStackTraceField(), stringWriter.getBuffer());
        }
        return resultBody;
    }

}
