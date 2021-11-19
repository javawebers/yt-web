package com.github.yt.web.controller;

import com.github.yt.web.result.HttpResultEntity;
import com.github.yt.web.result.HttpResultHandler;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author sheng
 */
public class BaseController {

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
