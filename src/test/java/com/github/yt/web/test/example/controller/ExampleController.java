package com.github.yt.web.test.example.controller;

import com.github.yt.web.exception.WebBusinessException;
import com.github.yt.web.test.exception.MyBusinessExceptionEnum;
import com.github.yt.web.test.query.WebQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ExampleController {
    @Autowired
    HttpServletResponse response;

    @GetMapping("autoPackageVoid")
    public void autoPackageVoid() {

    }

    @GetMapping("autoPackageResponse")
    public void autoPackageResponse(HttpServletResponse response) {
        // 方法中传入 response 对象，同时返回值为 void 时，不会进行自动包装
        // 可以采用注入 response 对象方式
    }

    @GetMapping("autoPackageResponse2")
    public void autoPackageResponse2() {

        System.out.println(response);
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
        throw new WebBusinessException(MyBusinessExceptionEnum.CODE_1004);
    }

    @GetMapping("pageQuery")
    public Map pageQuery(WebQuery query) {
        Map<String, Object> result = new HashMap<>();
        result.put("pageNo", query.takePageNo());
        result.put("pageSize", query.takePageSize());
        return result;
    }

}
