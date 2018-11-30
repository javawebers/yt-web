package com.github.yt;


import com.github.yt.web.log.RequestLogFilter;
import com.github.yt.web.log.RequestLogInterceptor;
import com.github.yt.web.result.CleanResponseThreadLocalInterceptor;
import com.github.yt.web.result.PackageResponseBodyAdvice;
import com.github.yt.web.result.JsonResultConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

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
})
public @interface EnableYtWeb {
}
