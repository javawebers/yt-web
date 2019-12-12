package com.github.yt.web.log;

import com.alibaba.fastjson.JSONObject;
import com.github.yt.web.YtWebConfig;
import com.github.yt.web.result.PackageResponseBodyAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 记录请求日志
 *
 * @author 刘加胜
 */
public class RequestLogInterceptor implements HandlerInterceptor {

    public static ThreadLocal<RequestLogEntity> requestLogThreadLocal = new ThreadLocal<>();

    private Logger logger = LoggerFactory.getLogger(RequestLogInterceptor.class);

    /**
     * 读取post的body参数
     *
     * @param httpServletRequest
     * @return
     */
    private String getInputStr(HttpServletRequest httpServletRequest) {
        if ("POST".equalsIgnoreCase(httpServletRequest.getMethod())) {
            return HttpHelper.getBodyString(httpServletRequest);
        }
        return "";
    }


    /**
     * 获取IP地址
     *
     * @param request
     * @return
     */
    private static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 排除 swagger
        String path = request.getServletPath();
        if (path.startsWith("/swagger")) {
            return true;
        }
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        if (!YtWebConfig.requestLog) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequestLog requestLog = handlerMethod.getMethodAnnotation(RequestLog.class);
        if (requestLog != null && !requestLog.value()) {
            return true;
        }
        requestLogThreadLocal.set(new RequestLogEntity());
        RequestLogEntity requestLogEntity = requestLogThreadLocal.get();
        requestLogEntity.setRequestTime(new Date());
        requestLogEntity.setIpAddress(getIpAddress(request));
        requestLogEntity.setRequestURI(request.getRequestURI());
        requestLogEntity.setUserAgent(request.getHeader("User-Agent"));
        requestLogEntity.setClassMethodName(handlerMethod.getMethod().toString());

        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headerMap = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String header = request.getHeader(headerName);
            headerMap.put(headerName, header);
        }
        if (!headerMap.isEmpty()) {
            requestLogEntity.setHeaderParams(JSONObject.toJSONString(headerMap));
        }
        if (request.getParameterMap() != null && !request.getParameterMap().isEmpty()) {
            requestLogEntity.setUrlParams(JSONObject.toJSONString(request.getParameterMap()));
        }
        if (YtWebConfig.requestLogBody) {
            requestLogEntity.setRequestBody(getInputStr(request));
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        RequestLogEntity requestLogEntity = requestLogThreadLocal.get();
        requestLogThreadLocal.remove();
        if (requestLogEntity == null) {
            return;
        }
        requestLogEntity.setInvokingTime((int) (System.currentTimeMillis() - requestLogEntity.getRequestTime().getTime()));
        requestLogEntity.setResponseBody(JSONObject.toJSONString(PackageResponseBodyAdvice.resultEntityThreadLocal.get()));
        Exception e = PackageResponseBodyAdvice.exceptionThreadLocal.get();
        if (e != null) {
            requestLogEntity.setError(true);
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            requestLogEntity.setErrorStackTrace(sw.getBuffer().toString());
            requestLogEntity.setErrorMessage(e.toString());
        } else {
            requestLogEntity.setError(false);
        }

        if (logger.isDebugEnabled()) {
            logger.debug(JSONObject.toJSONString(requestLogEntity));
        }
    }
}
