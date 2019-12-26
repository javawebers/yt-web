package com.github.yt.web.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.yt.commons.exception.BaseException;
import com.github.yt.commons.exception.BaseExceptionConverter;
import com.github.yt.web.YtWebConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Objects;


/**
 * 1.返回体拦截器 实现ResponseBodyAdvice接口的supports和beforeBodyWrite方法
 * 2.异常拦截器 @ExceptionHandler作用的handleExceptions方法
 * <p>
 * ApplicationContextAware 的作用是可以获取spring管理的bean
 * <p>
 * 正常返回和异常返回分别被该类处理
 * 拦截返回结果或者异常包装成HttpResultEntity
 *
 * @author liujiasheng
 */
@Order(200)
@ControllerAdvice
public class PackageResponseBodyAdvice implements ResponseBodyAdvice<Object>, ApplicationContextAware {
    private static Logger logger = LoggerFactory.getLogger(PackageResponseBodyAdvice.class);

    // 记录异常的threadLocal
    public static ThreadLocal<Exception> exceptionThreadLocal = new ThreadLocal<>();
    // 记录返回结果的threadLocal
    public static ThreadLocal<Object> resultEntityThreadLocal = new ThreadLocal<>();
    // 异常包装的时候判断是否执行了beforeBodyWrite方法
    public static ThreadLocal<Boolean> beforeBodyWriteThreadLocal = new ThreadLocal<>();

    private ApplicationContext applicationContext;


    public static void setExceptionThreadLocal(Exception e) {
        exceptionThreadLocal.set(e);
    }

    public static void removeExceptionThreadLocal() {
        exceptionThreadLocal.remove();
    }

    public static Exception getExceptionThreadLocal() {
        return exceptionThreadLocal.get();
    }

    public static void setResultEntityThreadLocal(Object entity) {
        resultEntityThreadLocal.set(entity);
    }

    public static void removeResultEntityThreadLocal() {
        resultEntityThreadLocal.remove();
    }

    public static Object getResultEntityThreadLocal() {
        return resultEntityThreadLocal.get();
    }


    public static void setBeforeBodyWriteThreadLocal(Boolean e) {
        beforeBodyWriteThreadLocal.set(e);
    }

    public static void removeBeforeBodyWriteThreadLocal() {
        beforeBodyWriteThreadLocal.remove();
    }

    public static Boolean getBeforeBodyWriteThreadLocal() {
        return beforeBodyWriteThreadLocal.get();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @ExceptionHandler
    @PackageResponseBody(false)
    public void handleExceptions(final Exception e, HandlerMethod handlerMethod, HttpServletResponse response) throws Exception {
        Exception se = convertToKnownException(e);
        exceptionThreadLocal.set(se);
        response.addHeader("Content-type", "text/html;charset=UTF-8");
        Boolean beforeBodyWrite = beforeBodyWriteThreadLocal.get();
        if (beforeBodyWrite != null) {
            throw se;
        }
        PackageResponseBody methodPackageResponseBody = handlerMethod.getMethod().getAnnotation(PackageResponseBody.class);
        PackageResponseBody classPackageResponseBody = handlerMethod.getBeanType().getAnnotation(PackageResponseBody.class);
        // 判断方法配置(默认true)
        if (methodPackageResponseBody != null) {
            if (!methodPackageResponseBody.value()) {
                throw e;
            }
        } else if (classPackageResponseBody != null) {
            // 判断类配置(默认true)
            if (!classPackageResponseBody.value()) {
                throw e;
            }
        } else if (!YtWebConfig.packageResponseBody) {
            // 判断全局配置(默认true)
            throw e;
        }

        // 当不向上抛异常时主动打印异常
        logger.error(se.getMessage(), se);

        HttpResultEntity resultBody;
        if (se instanceof BaseException) {
            resultBody = HttpResultHandler.getErrorSimpleResultBody((BaseException) se);
        } else {
            resultBody = HttpResultHandler.getErrorSimpleResultBody();
        }
        // 返回异常堆栈到前端
        if (YtWebConfig.returnStackTrace) {
            StringWriter sw = new StringWriter();
            se.printStackTrace(new PrintWriter(sw, true));
            resultBody.put("stackTrace", sw);
        }
        response.setStatus(200);
        response.addHeader("Content-type", "application/json;charset=UTF-8");
        resultEntityThreadLocal.set(resultBody);
        String result = JSON.toJSONString(resultBody, SerializerFeature.WriteMapNullValue);
        response.getWriter().write(result);
    }

    /**
     * 将异常转换为BaseException
     *
     * @param e
     * @return
     */
    private Exception convertToKnownException(Exception e) {
        if (e instanceof BaseException) {
            return e;
        }
        Map<String, BaseExceptionConverter> exceptionConverterMap = applicationContext.getBeansOfType(BaseExceptionConverter.class);
        for (BaseExceptionConverter baseExceptionConverter : exceptionConverterMap.values()) {
            Exception knownException = baseExceptionConverter.convertToBaseException(e);
            if (knownException instanceof BaseException) {
                return knownException;
            }
        }
        return e;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        resultEntityThreadLocal.set(body);
        String path = ((ServletServerHttpRequest) request).getServletRequest().getServletPath();
        // 排除 actuator
        if (path.startsWith("/actuator")) {
            return body;
        }
//
        // 判断方法配置(默认true)
        PackageResponseBody methodPackageResponseBody = Objects.requireNonNull(returnType.getMethod()).getAnnotation(PackageResponseBody.class);
        PackageResponseBody classPackageResponseBody = returnType.getDeclaringClass().getAnnotation(PackageResponseBody.class);

        if (methodPackageResponseBody != null) {
            // 判断方法配置(默认true)
            if (!methodPackageResponseBody.value()) {
                return body;
            }
        } else if (classPackageResponseBody != null) {
            // 判断类配置(默认true)
            if (!classPackageResponseBody.value()) {
                return body;
            }
        } else if (!YtWebConfig.packageResponseBody) {
            // 判断类配置(默认true)
            return body;
        }

        if (returnType.getMethod().getReturnType().equals(ResponseEntity.class)) {
            return body;
        }
        if (returnType.getMethod().getReturnType().equals(HttpResultEntity.class)) {
            return body;
        }

        HttpResultEntity resultBody = HttpResultHandler.getSuccessSimpleResultBody(body);
        resultEntityThreadLocal.set(resultBody);
        response.setStatusCode(HttpStatus.OK);
        beforeBodyWriteThreadLocal.set(true);
        response.getHeaders().add("Content-type", "application/json;charset=UTF-8");
        if (body instanceof String || returnType.getMethod().getReturnType().equals(String.class)) {
            return JSON.toJSONString(resultBody, SerializerFeature.WriteMapNullValue);
        }
        return resultBody;
    }
}
