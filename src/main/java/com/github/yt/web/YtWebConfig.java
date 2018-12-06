package com.github.yt.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YtWebConfig {
    public static String baseEntityValueClass;
    public static boolean packageResponseBody;
    public static String resultClass;

    // 分页
    public static String pageNoName;
    public static String pageSizeName;

    // 请求日志
    // 是否记录请求日志
    public static boolean requestLog;
    // 如果记录日志是否记录post等的body内容，记录body内容在单元测试时会有问题，取不到body的值
    public static boolean requestLogBody;

    @Value("${yt.entity.baseEntityValue:com.github.yt.mybatis.domain.DefaultBaseEntityValue}")
    public void setBaseEntityValueClass(String baseEntityValueClass) {
        YtWebConfig.baseEntityValueClass = baseEntityValueClass;
    }

    @Value("${yt.result.packageResponseBody:true}")
    public void setPackageResponseBody(boolean packageResponseBody) {
        YtWebConfig.packageResponseBody = packageResponseBody;
    }

    @Value("${yt.result.class:com.github.yt.web.result.SimpleResultConfig}")
    public void setResultClass(String resultClass) {
        YtWebConfig.resultClass = resultClass;
    }

    @Value("${yt.page.pageNoName:pageNo}")
    public void setPageNoName(String pageNoName) {
        YtWebConfig.pageNoName = pageNoName;
    }

    @Value("${yt.page.pageSizeName:pageSize}")
    public void setPageSizeName(String pageSizeName) {
        YtWebConfig.pageSizeName = pageSizeName;
    }

    @Value("${yt.request.requestLog:true}")
    public void setRequestLog(boolean requestLog) {
        YtWebConfig.requestLog = requestLog;
    }
    @Value("${yt.request.requestLogBody:false}")
    public void setRequestLogBody(boolean requestLogBody) {
        YtWebConfig.requestLogBody = requestLogBody;
    }
}

