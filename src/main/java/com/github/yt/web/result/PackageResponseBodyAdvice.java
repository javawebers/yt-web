package com.github.yt.web.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.yt.web.YtWebConfig;
import com.github.yt.commons.exception.BaseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


/**
 * 返回体拦截器，异常拦截器
 * 正常返回和异常返回分别被该类处理
 * 拦截返回结果或者异常包装成HttpResultEntity
 * @author liujiasheng
 */
@Order(200)
@ControllerAdvice
public class PackageResponseBodyAdvice implements ResponseBodyAdvice<Object> {
	private static Logger logger = LoggerFactory.getLogger(PackageResponseBodyAdvice.class);

	// 记录异常的threadLocal
	public static ThreadLocal<Exception> exceptionThreadLocal = new ThreadLocal<>();
	// 记录返回结果的threadLocal
	public static ThreadLocal<Object> resultEntityThreadLocal = new ThreadLocal<>();
	// 异常包装的时候判断是否执行了beforeBodyWrite方法
	public static ThreadLocal<Boolean> beforeBodyWriteThreadLocal = new ThreadLocal<>();

	@ExceptionHandler
	@PackageResponseBody(false)
	public void handleExceptions(final Exception e, HandlerMethod handlerMethod, HttpServletResponse response) throws Exception {
		Exception se = e;
		exceptionThreadLocal.set(se);
		logger.error(se.getMessage(), se);
		response.addHeader("Content-type", "text/html;charset=UTF-8");
		Boolean beforeBodyWrite = beforeBodyWriteThreadLocal.get();
		if (beforeBodyWrite != null) {
			throw se;
		}
		// 判断方法配置(默认true)
		PackageResponseBody methodPackageResponseBody = handlerMethod.getMethod().getAnnotation(PackageResponseBody.class);
		if (methodPackageResponseBody != null && !methodPackageResponseBody.value()) {
			throw e;
		}
		// 判断类配置(默认true)
		PackageResponseBody classPackageResponseBody = handlerMethod.getBeanType().getAnnotation(PackageResponseBody.class);
		if (classPackageResponseBody != null && !classPackageResponseBody.value()) {
			throw e;
		}
		// 判断全局配置(默认true)
		if (!YtWebConfig.packageResponseBody) {
			throw e;
		}

		HttpResultEntity resultBody;
		if (se instanceof BaseException){
			resultBody = HttpResultHandler.getErrorSimpleResultBody((BaseException)se);
		} else {
			resultBody = HttpResultHandler.getErrorSimpleResultBody();
		}
		response.setStatus(200);
		response.addHeader("Content-type", "application/json;charset=UTF-8");
		resultEntityThreadLocal.set(resultBody);
		String result = JSON.toJSONString(resultBody, SerializerFeature.WriteMapNullValue);
		response.getWriter().write(result);
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
		PackageResponseBody methodPackageResponseBody = returnType.getMethod().getAnnotation(PackageResponseBody.class);
		if (methodPackageResponseBody != null && !methodPackageResponseBody.value()) {
			return body;
		}
		// 判断类配置(默认true)
		PackageResponseBody classPackageResponseBody = returnType.getDeclaringClass().getAnnotation(PackageResponseBody.class);
		if (classPackageResponseBody != null && !classPackageResponseBody.value()) {
			return body;
		}
		// 判断全局配置(默认true)
		if (!YtWebConfig.packageResponseBody) {
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
		response.getHeaders().add("Content-type", "application/json;charset=UTF-8");
		if (body instanceof String || returnType.getMethod().getReturnType().equals(String.class)){
			return JSON.toJSONString(resultBody, SerializerFeature.WriteMapNullValue );
		}
		response.setStatusCode(HttpStatus.OK);
		beforeBodyWriteThreadLocal.set(true);
		return resultBody;
	}
}
