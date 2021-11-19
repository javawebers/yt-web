package com.github.yt.web.result;

import com.github.yt.web.exception.WebBaseException;
import com.github.yt.web.util.YtStringUtils;
import com.github.yt.web.conf.YtWebProperties;
import com.github.yt.web.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

/**
 * http请求返回实体处理类
 *
 * @author liujiasheng
 */
@Slf4j
public class HttpResultHandler {

    private static volatile BaseResultConfig resultConfig;

    public static BaseResultConfig getResultConfig() {
        if (resultConfig == null) {
            synchronized (HttpResultHandler.class) {
                try {
                    YtWebProperties ytWebProperties = SpringContextUtils.getBean(YtWebProperties.class);
                    resultConfig = ytWebProperties.getResult().getResultConfigClass().newInstance();
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

    /**
     * 请求成功封装返回对象
     *
     * @param result     结果
     * @param withMore   是否有更多结果
     * @param moreResult 更多结果
     * @return HttpResultEntity
     */
    private static HttpResultEntity getSuccessMoreResultBody(Object result, boolean withMore,
            Object moreResult) {
        HttpResultEntity resultBody = new HttpResultEntity();
        resultBody.put(getResultConfig().getErrorCodeField(),
                getResultConfig().getDefaultSuccessCode());
        resultBody.put(getResultConfig().getMessageField(),
                getResultConfig().getDefaultSuccessMessage());
        resultBody.put(getResultConfig().getResultField(), result);
        if (withMore) {
            resultBody.put(getResultConfig().getMoreResultField(), moreResult);
        }
        return resultBody;
    }

    /**
     * 请求失败封装返回对象
     *
     * @param exception 异常
     * @return HttpResultEntity
     */
    public static HttpResultEntity getErrorSimpleResultBody(Throwable exception) {
        HttpResultEntity resultBody = new HttpResultEntity();
        if (exception instanceof WebBaseException) {
            WebBaseException webBaseException = (WebBaseException) exception;
            resultBody.put(getResultConfig().getErrorCodeField(),
                    getResultConfig().convertErrorCode(webBaseException.getErrorCode()));
            resultBody.put(getResultConfig().getMessageField(), exception.getMessage());
            resultBody.put(getResultConfig().getResultField(), webBaseException.getErrorResult());
        } else {
            resultBody.put(getResultConfig().getErrorCodeField(),
                    getResultConfig().getDefaultErrorCode());
            resultBody.put(getResultConfig().getMessageField(),
                    getResultConfig().getDefaultErrorMessage());
            resultBody.put(getResultConfig().getResultField(), null);
        }

        // 返回异常堆栈到前端
        YtWebProperties ytWebProperties = SpringContextUtils.getBean(YtWebProperties.class);
        String stackTraceField = getResultConfig().getStackTraceField();
        if (ytWebProperties.getResult().isReturnStackTrace() && YtStringUtils
                .isNotBlank(stackTraceField)) {
            resultBody.put(stackTraceField, getAndSetExceptionStrToRequest(exception));
        }
        return resultBody;
    }

    /**
     * 从 request 中获取 异常栈 信息，如果没有则设置到 request 中，如果存在则直接返回
     *
     * @param exception 异常
     * @return 异常栈
     */
    public static String getAndSetExceptionStrToRequest(Throwable exception) {
        final String REQUEST_EXCEPTION_STR = "__REQUEST_EXCEPTION_STR__";
        HttpServletRequest request = getRequest();
        String exceptionStr = (String) request.getAttribute(REQUEST_EXCEPTION_STR);
        if (exceptionStr == null) {
            StringWriter stringWriter = new StringWriter();
            exception.printStackTrace(new PrintWriter(stringWriter, true));
            exceptionStr = stringWriter.getBuffer().toString();
            request.setAttribute(REQUEST_EXCEPTION_STR, exceptionStr);
        }
        return exceptionStr;
    }

    private static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return ((ServletRequestAttributes) Objects.requireNonNull(requestAttributes)).getRequest();
    }

}
