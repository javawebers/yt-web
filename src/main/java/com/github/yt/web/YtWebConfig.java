package com.github.yt.web;

import com.github.yt.web.result.BaseResultConfig;
import com.github.yt.web.result.SimpleResultConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * @author sheng
 */
@Component
@ConfigurationProperties("yt")
public class YtWebConfig {

    private Page page = new Page();
    private Request request = new Request();
    private Result result = new Result();

    public Page getPage() {
        return page;
    }

    public YtWebConfig setPage(Page page) {
        this.page = page;
        return this;
    }

    public Request getRequest() {
        return request;
    }

    public YtWebConfig setRequest(Request request) {
        this.request = request;
        return this;
    }

    public Result getResult() {
        return result;
    }

    public YtWebConfig setResult(Result result) {
        this.result = result;
        return this;
    }

    public static class Request {
        private boolean requestLog = true;
        private boolean requestLogBody = false;

        public boolean isRequestLog() {
            return requestLog;
        }

        public Request setRequestLog(boolean requestLog) {
            this.requestLog = requestLog;
            return this;
        }

        public boolean isRequestLogBody() {
            return requestLogBody;
        }

        public Request setRequestLogBody(boolean requestLogBody) {
            this.requestLogBody = requestLogBody;
            return this;
        }
    }

    public static class Result {
        private boolean packageResponseBody = true;
        private boolean returnStackTrace = false;

        private Class<? extends BaseResultConfig> resultConfigClass = SimpleResultConfig.class;

        public boolean isPackageResponseBody() {
            return packageResponseBody;
        }

        public Result setPackageResponseBody(boolean packageResponseBody) {
            this.packageResponseBody = packageResponseBody;
            return this;
        }

        public boolean isReturnStackTrace() {
            return returnStackTrace;
        }

        public Result setReturnStackTrace(boolean returnStackTrace) {
            this.returnStackTrace = returnStackTrace;
            return this;
        }

        public Class<? extends BaseResultConfig> getResultConfigClass() {
            return resultConfigClass;
        }

        public Result setResultConfigClass(Class<? extends BaseResultConfig> resultConfigClass) {
            this.resultConfigClass = resultConfigClass;
            return this;
        }
    }

    public static class Page {
        private String pageNoName = "pageNo";
        private String pageSizeName = "pageSize";

        public String getPageNoName() {
            return pageNoName;
        }

        public Page setPageNoName(String pageNoName) {
            this.pageNoName = pageNoName;
            return this;
        }

        public String getPageSizeName() {
            return pageSizeName;
        }

        public Page setPageSizeName(String pageSizeName) {
            this.pageSizeName = pageSizeName;
            return this;
        }
    }

}

