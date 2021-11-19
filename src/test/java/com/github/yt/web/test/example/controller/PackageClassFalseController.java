package com.github.yt.web.test.example.controller;

import com.github.yt.web.controller.BaseController;
import com.github.yt.web.result.HttpResultEntity;
import com.github.yt.web.result.PackageResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PackageResponseBody(false)
@RequestMapping("packageClassFalse")
public class PackageClassFalseController extends BaseController {

    @GetMapping("methodDefault")
    public void methodDefault() {

    }

    @GetMapping("methodTrue")
    @PackageResponseBody
    public void methodTrue() {

    }

    @GetMapping("methodFalse")
    @PackageResponseBody(false)
    public void methodFalse() {

    }


    @GetMapping("entityMethodDefault")
    public HttpResultEntity entityMethodDefault() {
        return result(1);
    }

    @GetMapping("entityMethodTrue")
    @PackageResponseBody
    public HttpResultEntity entityMethodTrue() {
        return result(1);
    }

    @GetMapping("entityMethodFalse")
    @PackageResponseBody(false)
    public HttpResultEntity entityMethodFalse() {
        return result(1);
    }

    @GetMapping("entityThrowException")
    public HttpResultEntity entityThrowException() {
        throw new RuntimeException();
    }

    @GetMapping("responseEntityThrowException")
    public ResponseEntity<?> responseEntityThrowException() {
        throw new RuntimeException();
    }
}
