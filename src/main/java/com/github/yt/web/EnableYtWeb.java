package com.github.yt.web;


import com.github.yt.web.log.RequestLogFilter;
import com.github.yt.web.log.RequestLogInterceptor;
import com.github.yt.web.query.QueryControllerAspect;
import com.github.yt.web.result.*;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


/**
 * 1.开启分页参数传入功能
 * 2.返回体自动包装，包括无异常返回和异常返回两种情况的处理
 * 3.记录请求信息
 * @author liujiasheng
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({
        YtWebConfig.class,
        PackageResponseBodyAdvice.class,
        CleanResponseThreadLocalInterceptor.class,
        JsonResultConfig.class,
        RequestLogFilter.class,
        RequestLogInterceptor.class,
        QueryControllerAspect.class,
        ValidatorExceptionConverter.class,
})
public @interface EnableYtWeb {
}
