package com.github.yt.web.result;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 清除threadLocal
 * @author 刘加胜
 */
public class CleanResponseThreadLocalInterceptor implements HandlerInterceptor {


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
