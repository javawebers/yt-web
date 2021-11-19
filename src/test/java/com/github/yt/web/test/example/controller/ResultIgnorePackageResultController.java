package com.github.yt.web.test.example.controller;

import com.github.yt.web.test.example.entity.IgnorePackageResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("resultIgnorePackageResult")
public class ResultIgnorePackageResultController {

    @GetMapping("success")
    public IgnorePackageResult success() {
        return new IgnorePackageResult();
    }

    @GetMapping("error")
    public IgnorePackageResult error() {
        throw new RuntimeException();
    }

}
