package com.github.yt.web.log;

import java.io.Serializable;
import java.util.Date;

/**
 * 请求日志实体类
 *
 * @author liujiasheng
 */
public class RequestLogEntity implements Serializable {

    private String ipAddress;
    private String userAgent;

    private String classMethodName;
    private String headerParams;
    // 问号之前
    private String requestUri;
    // 问好之后
    private String urlParams;
    private String requestBody;
    private String responseBody;

    private Boolean isError;
    private String errorMessage;
    private String errorStackTrace;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date requestTime;
    private Integer invokingTime;

    public String getRequestUri() {
        return requestUri;
    }

    public RequestLogEntity setRequestUri(String requestUri) {
        this.requestUri = requestUri;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public RequestLogEntity setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }


    public String getIpAddress() {
        return ipAddress;
    }

    public RequestLogEntity setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public String getClassMethodName() {
        return classMethodName;
    }

    public RequestLogEntity setClassMethodName(String classMethodName) {
        this.classMethodName = classMethodName;
        return this;
    }

    public String getHeaderParams() {
        return headerParams;
    }

    public RequestLogEntity setHeaderParams(String headerParams) {
        this.headerParams = headerParams;
        return this;
    }

    public String getUrlParams() {
        return urlParams;
    }

    public RequestLogEntity setUrlParams(String urlParams) {
        this.urlParams = urlParams;
        return this;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public RequestLogEntity setRequestBody(String requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public RequestLogEntity setResponseBody(String responseBody) {
        this.responseBody = responseBody;
        return this;
    }

    public Boolean getError() {
        return isError;
    }

    public RequestLogEntity setError(Boolean error) {
        isError = error;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public RequestLogEntity setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public String getErrorStackTrace() {
        return errorStackTrace;
    }

    public RequestLogEntity setErrorStackTrace(String errorStackTrace) {
        this.errorStackTrace = errorStackTrace;
        return this;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public RequestLogEntity setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
        return this;
    }

    public Integer getInvokingTime() {
        return invokingTime;
    }

    public RequestLogEntity setInvokingTime(Integer invokingTime) {
        this.invokingTime = invokingTime;
        return this;
    }
}
