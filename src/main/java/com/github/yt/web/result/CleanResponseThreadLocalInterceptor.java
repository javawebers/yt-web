package com.github.yt.web.result;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 清除threadLocal
 * @author 刘加胜
 */
@Configuration
public class CleanResponseThreadLocalInterceptor implements HandlerInterceptor, WebMvcConfigurer {

	@Resource
	private CleanResponseThreadLocalInterceptor cleanResponseThreadLocalInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(cleanResponseThreadLocalInterceptor).order(100);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		PackageResponseBodyAdvice.exceptionThreadLocal.remove();
		PackageResponseBodyAdvice.resultEntityThreadLocal.remove();
		PackageResponseBodyAdvice.beforeBodyWriteThreadLocal.remove();
	}
}
