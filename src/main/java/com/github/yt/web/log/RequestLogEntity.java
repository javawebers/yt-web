package com.github.yt.web.log;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 请求日志实体类
 *
 * @author liujiasheng
 */
@Data
public class RequestLogEntity implements Serializable {

    private String ipAddress;
    private String userAgent;

    private String classMethodName;
    private String headerParams;
    /**
     * 问号之前
     */
    private String requestUri;
    /**
     * 问好之后
     */
    private String urlParams;
    private String responseBody;

    private Boolean isError;
    private String errorMessage;
    private String errorStackTrace;

    private Date requestTime;
    private Integer invokingTime;

}
