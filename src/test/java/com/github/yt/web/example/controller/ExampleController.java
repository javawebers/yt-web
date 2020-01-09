package com.github.yt.web.example.controller;

import com.github.yt.commons.exception.BaseAccidentException;
import com.github.yt.web.exception.MyBusinessExceptionEnum;
import com.github.yt.web.query.WebQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ExampleController {

    @GetMapping("autoPackageVoid")
    public void autoPackageVoid() {

    }

    @GetMapping("autoPackageResult")
    public Map autoPackageResult() {
        Map result = new HashMap();
        result.put("key1", "value1");
        return result;
    }

    @GetMapping("autoPackageException")
    public void autoPackageException() {
        throw new RuntimeException("未知异常");
    }

    @GetMapping("autoPackageException2")
    public void autoPackageException2() {
        throw new BaseAccidentException(MyBusinessExceptionEnum.CODE_1004);
    }

    @GetMapping("pageQuery")
    public Map pageQuery(WebQuery query) {
        Map<String, Object> result = new HashMap<>();
        result.put("pageNo", query.takePageNo());
        result.put("pageSize", query.takePageSize());
        return result;
    }

}
