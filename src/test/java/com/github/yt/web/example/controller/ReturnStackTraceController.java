package com.github.yt.web.example.controller;

import com.github.yt.commons.exception.BaseAccidentException;
import com.github.yt.web.exception.MyBusinessExceptionEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("returnStackTrace")
public class ReturnStackTraceController {

    @GetMapping("success")
    public void success() {
    }

    @GetMapping("error")
    public void error() {
        throw new RuntimeException();
    }

    @GetMapping("knowException")
    public void knowException() {
        throw new BaseAccidentException(MyBusinessExceptionEnum.CODE_1003);
    }

}
