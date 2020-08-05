package com.github.yt.web.result;

import com.github.yt.commons.exception.BaseException;
import com.github.yt.commons.exception.BaseExceptionConverter;
import com.github.yt.web.YtWebConfig;
import com.github.yt.web.util.JsonUtils;
import com.github.yt.web.util.SpringContextUtils;
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
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
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
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String REQUEST_EXCEPTION = "yt:request_exception";
    public static final String REQUEST_RESULT_ENTITY = "yt:request_result_entity";
    public static final String REQUEST_BEFORE_BODY_WRITE = "yt:request_before_body_write";

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    /**
     * 405 异常直接抛出
     *
     * @param e e
     * @throws Throwable e
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @PackageResponseBody(false)
    public void handleExceptions405(final Throwable e) throws Throwable {
        throw e;
    }

    /**
     * 全局异常处理类
     * <p>
     * 配合文件上传文件最大限制时需要同时配置 spring.servlet.multipart.resolve-lazily。如下：
     * spring.servlet.multipart.max-file-size=1KB
     * spring.servlet.multipart.resolve-lazily=true
     *
     * @param e             异常类
     * @param handlerMethod controller 方法
     * @param request       request
     * @param response      response
     * @throws Exception 不进行处理的异常重新抛出
     */
    @ExceptionHandler
    @PackageResponseBody(false)
    public void handleExceptions(final Throwable e, HandlerMethod handlerMethod,
                                 HttpServletRequest request, HttpServletResponse response) throws Throwable {
        Throwable se = convertToKnownException(e);
        request.setAttribute(REQUEST_EXCEPTION, se);
        if (!exceptionPackageResponseBody(request, handlerMethod.getMethod())) {
            throw e;
        }
        Object beforeBodyWrite = request.getAttribute(REQUEST_BEFORE_BODY_WRITE);
        if (beforeBodyWrite != null) {
            throw se;
        }

        // 当不向上抛异常时主动打印异常
        logger.error(se.getMessage(), se);

        HttpResultEntity resultBody = HttpResultHandler.getErrorSimpleResultBody(se);

        response.setStatus(200);
        response.addHeader("Content-type", "application/json;charset=UTF-8");
        request.setAttribute(REQUEST_RESULT_ENTITY, resultBody);
        String result = JsonUtils.toJsonString(resultBody);
        response.getWriter().write(result);
    }


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        HttpServletRequest request = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
        request.setAttribute(REQUEST_RESULT_ENTITY, body);

        if (!successPackageResponseBody(request, Objects.requireNonNull(returnType.getMethod()))) {
            return body;
        }

        HttpResultEntity resultBody = HttpResultHandler.getSuccessSimpleResultBody(body);
        request.setAttribute(REQUEST_RESULT_ENTITY, resultBody);
        request.setAttribute(REQUEST_BEFORE_BODY_WRITE, new Object());
        serverHttpResponse.setStatusCode(HttpStatus.OK);
        serverHttpResponse.getHeaders().add("Content-type", "application/json;charset=UTF-8");
        if (body instanceof String || returnType.getMethod().getReturnType().equals(String.class)) {
            return JsonUtils.toJsonString(resultBody);
        }
        return resultBody;
    }


    /**
     * 将异常转换为BaseException
     */
    private Throwable convertToKnownException(Throwable e) {
        if (e instanceof BaseException) {
            return e;
        }
        Map<String, BaseExceptionConverter> exceptionConverterMap = applicationContext.getBeansOfType(BaseExceptionConverter.class);
        for (BaseExceptionConverter baseExceptionConverter : exceptionConverterMap.values()) {
            Throwable knownException = baseExceptionConverter.convertToBaseException(e);
            if (knownException instanceof BaseException) {
                return knownException;
            }
        }
        return e;
    }

    private boolean exceptionPackageResponseBody(HttpServletRequest request, Method method) {
        String path = request.getServletPath();
        // 排除 actuator
        if (path.startsWith("/actuator")) {
            return false;
        }
        if (ResponseEntity.class.isAssignableFrom(method.getReturnType())) {
            return false;
        }
        // 返回的实体类是 HttpResultEntity 时，抛出异常也需要包装
        if (HttpResultEntity.class.isAssignableFrom(method.getReturnType())) {
            return true;
        }
        return packageResponseBody(method);
    }

    private boolean successPackageResponseBody(HttpServletRequest request, Method method) {
        String path = request.getServletPath();
        // 排除 actuator
        if (path.startsWith("/actuator")) {
            return false;
        }
        if (ResponseEntity.class.isAssignableFrom(method.getReturnType())) {
            return false;
        }
        // 返回的实体类是 HttpResultEntity 时，正常返回就不需要包装了
        if (HttpResultEntity.class.isAssignableFrom(method.getReturnType())) {
            return false;
        }
        return packageResponseBody(method);
    }

    /**
     * 通过全局配置或者注解判断是否包装返回体
     */
    private boolean packageResponseBody(Method method) {
        PackageResponseBody methodPackageResponseBody = method.getAnnotation(PackageResponseBody.class);
        PackageResponseBody classPackageResponseBody = method.getDeclaringClass().getAnnotation(PackageResponseBody.class);
        if (methodPackageResponseBody != null) {
            // 判断方法配置(默认true)
            return methodPackageResponseBody.value();
        } else // 判断全局配置(默认true)
            if (classPackageResponseBody != null) {
                // 判断类配置(默认true)
                return classPackageResponseBody.value();
            } else {
                YtWebConfig ytWebConfig = SpringContextUtils.getBean(YtWebConfig.class);
                return ytWebConfig.getResult().isPackageResponseBody();
            }
    }
}
