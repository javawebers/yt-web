package com.github.yt.web.controller;


import com.github.yt.web.result.HttpResultEntity;
import com.github.yt.web.result.HttpResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author sheng
 */
public class BaseController {

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Resource
    protected HttpServletRequest request;

    @Resource
    protected HttpServletResponse response;

    @Resource
    protected HttpSession session;


    protected HttpResultEntity result() {
        return HttpResultHandler.getSuccessSimpleResultBody();
    }

    protected HttpResultEntity result(Object result) {
        return HttpResultHandler.getSuccessSimpleResultBody(result);
    }

    protected HttpResultEntity result(Object result, Object moreResult) {
        return HttpResultHandler.getSuccessMoreResultBody(result, moreResult);
    }


}
