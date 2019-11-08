package com.github.yt.web;

import com.github.yt.web.log.RequestLogInterceptor;
import com.github.yt.web.result.CleanResponseThreadLocalInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class YtWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CleanResponseThreadLocalInterceptor()).order(100);
        registry.addInterceptor(new RequestLogInterceptor()).order(200).excludePathPatterns("/error");
    }

}
