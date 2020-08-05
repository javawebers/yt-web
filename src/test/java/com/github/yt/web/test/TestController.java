package com.github.yt.web.test;

import com.github.yt.web.controller.BaseController;
import com.github.yt.web.result.HttpResultEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController extends BaseController {

    @GetMapping(value = "/testResultEntity1")
    public HttpResultEntity testResultEntity1() {
        return new HttpResultEntity();
    }

    @GetMapping(value = "/testResultEntity2")
    public HttpResultEntity testResultEntity2() {
        return result(null);
    }

    @GetMapping(value = "/testResultEntity3")
    public HttpResultEntity testResultEntity3() {
        throw new RuntimeException();
    }

}
