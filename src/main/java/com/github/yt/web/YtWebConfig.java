package com.github.yt.web;

import com.github.yt.web.result.BaseResultConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author sheng
 */
@Configuration
public class YtWebConfig {

    public static boolean packageResponseBody;
    public static boolean returnStackTrace;
    public static Class<? extends BaseResultConfig> resultClass;
    public static String pageNoName;
    public static String pageSizeName;
    public static boolean requestLog;
    public static boolean requestLogBody;

    /**
     * 是否自动包装返回对象
     */
    @Value("${yt.result.packageResponseBody:true}")
    public void setPackageResponseBody(boolean packageResponseBody) {
        YtWebConfig.packageResponseBody = packageResponseBody;
    }

    /**
     * 是否将异常栈信息返回到前端
     */
    @Value("${yt.result.returnStackTrace:false}")
    public void setReturnStackTrace(boolean returnStackTrace) {
        YtWebConfig.returnStackTrace = returnStackTrace;
    }

    /**
     * 返回对象的属性配置
     * 实现 BaseResultConfig
     */
    @Value("${yt.result.class:com.github.yt.web.result.SimpleResultConfig}")
    public void setResultClass(Class<? extends BaseResultConfig> resultClass) {
        YtWebConfig.resultClass = resultClass;
    }

    /**
     * 请求分页参数配置
     */
    @Value("${yt.page.pageNoName:pageNo}")
    public void setPageNoName(String pageNoName) {
        YtWebConfig.pageNoName = pageNoName;
    }

    @Value("${yt.page.pageSizeName:pageSize}")
    public void setPageSizeName(String pageSizeName) {
        YtWebConfig.pageSizeName = pageSizeName;
    }

    /**
     * 请求日志
     * 是否记录请求日志
     */
    @Value("${yt.request.requestLog:true}")
    public void setRequestLog(boolean requestLog) {
        YtWebConfig.requestLog = requestLog;
    }

    /**
     * 如果记录日志是否记录post等的body内容，记录body内容在单元测试时会有问题，取不到body的值
     */
    @Value("${yt.request.requestLogBody:false}")
    public void setRequestLogBody(boolean requestLogBody) {
        YtWebConfig.requestLogBody = requestLogBody;
    }
}

