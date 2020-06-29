package com.github.yt.web.log;

import com.github.yt.web.YtWebConfig;
import com.github.yt.web.util.SpringContextUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 记录请求日志的filter
 * 对于post请求的body中内容只能读取一次，将post的内容设置成可重复读取
 *
 * @author liujiasheng
 */
@WebFilter
@Component
public class RequestLogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        YtWebConfig ytWebConfig = SpringContextUtils.getBean(YtWebConfig.class);
        if (ytWebConfig.getRequest().isRequestLog() && ytWebConfig.getRequest().isRequestLogBody()) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            if ("POST".equalsIgnoreCase(httpServletRequest.getMethod())) {
                // 防止流读取一次后就没有了, 所以需要将流继续写出去
                ServletRequest requestWrapper = new RequestWrapper(httpServletRequest);
                chain.doFilter(requestWrapper, response);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}
