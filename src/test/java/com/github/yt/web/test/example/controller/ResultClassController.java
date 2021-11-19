package com.github.yt.web.test.example.controller;

import com.github.yt.web.exception.WebBusinessException;
import com.github.yt.web.test.exception.MyBusinessExceptionEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("resultClass")
public class ResultClassController {

    @GetMapping("success")
    public void success() {

    }

    @GetMapping("error")
    public void error() {
        throw new RuntimeException();
    }

    @GetMapping("knowException")
    public void knowException() {
        throw new WebBusinessException(MyBusinessExceptionEnum.CODE_1003);
    }

    @GetMapping("stringResult")
    public String stringResult() {
        return "str1";
    }
    @GetMapping("stringResultError")
    public String stringResultError() {
        throw new RuntimeException();
    }
}
