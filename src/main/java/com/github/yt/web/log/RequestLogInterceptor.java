package com.github.yt.web.log;

import com.github.yt.web.YtWebConfig;
import com.github.yt.web.result.PackageResponseBodyAdvice;
import com.github.yt.web.util.JsonUtils;
import com.github.yt.web.util.SpringContextUtils;
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

    private Logger logger = LoggerFactory.getLogger(RequestLogInterceptor.class);

    /**
     * 读取post的body参数
     */
    private String getInputStr(HttpServletRequest httpServletRequest) {
        if ("POST".equalsIgnoreCase(httpServletRequest.getMethod())) {
            return HttpHelper.getBodyString(httpServletRequest);
        }
        return "";
    }


    /**
     * 获取IP地址
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

    private boolean isLog(HttpServletRequest request, Object handler) {
        // 排除 swagger
        String path = request.getServletPath();
        if (path.startsWith("/swagger")) {
            return false;
        }
        if (!(handler instanceof HandlerMethod)) {
            return false;
        }
        if (!logger.isDebugEnabled()) {
            return false;
        }
        /// 判断是否记录日志
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 判断方法配置(默认true)
        RequestLog methodRequestLog = handlerMethod.getMethodAnnotation(RequestLog.class);
        RequestLog classRequestLog = handlerMethod.getBeanType().getAnnotation(RequestLog.class);
        if (methodRequestLog != null) {
            // 判断方法配置(默认true)
            return methodRequestLog.value();
        } else if (classRequestLog != null) {
            // 判断类配置(默认true)
            return classRequestLog.value();
        } else {
            YtWebConfig ytWebConfig = SpringContextUtils.getBean(YtWebConfig.class);
            return ytWebConfig.getRequest().isRequestLog();
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("requestTime", new Date());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (!isLog(request, handler)) {
            return;
        }
        Date requestTime = (Date) request.getAttribute("requestTime");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequestLogEntity requestLogEntity = new RequestLogEntity();
        requestLogEntity.setRequestTime(requestTime);
        requestLogEntity.setIpAddress(getIpAddress(request));
        requestLogEntity.setRequestUri(request.getRequestURI());
        requestLogEntity.setUserAgent(request.getHeader("User-Agent"));
        requestLogEntity.setClassMethodName(handlerMethod.getMethod().toString());

        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headerMap = new HashMap<>(16);
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String header = request.getHeader(headerName);
            headerMap.put(headerName, header);
        }
        if (!headerMap.isEmpty()) {
            requestLogEntity.setHeaderParams(JsonUtils.toJsonString(headerMap));
        }
        if (request.getParameterMap() != null && !request.getParameterMap().isEmpty()) {
            requestLogEntity.setUrlParams(JsonUtils.toJsonString(request.getParameterMap()));
        }

        YtWebConfig ytWebConfig = SpringContextUtils.getBean(YtWebConfig.class);
        if (ytWebConfig.getRequest().isRequestLogBody()) {
            requestLogEntity.setRequestBody(getInputStr(request));
        }

        requestLogEntity.setInvokingTime((int) (System.currentTimeMillis() - requestLogEntity.getRequestTime().getTime()));
        requestLogEntity.setResponseBody(JsonUtils.toJsonString(request.getAttribute(PackageResponseBodyAdvice.REQUEST_RESULT_ENTITY)));
        Exception e = (Exception) request.getAttribute(PackageResponseBodyAdvice.REQUEST_EXCEPTION);
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
            logger.debug(JsonUtils.toJsonString(requestLogEntity));
        }
    }
}
