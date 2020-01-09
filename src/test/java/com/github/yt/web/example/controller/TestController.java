package com.github.yt.web.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yt.commons.exception.BaseAccidentException;
import com.github.yt.web.controller.BaseController;
import com.github.yt.web.example.entity.CircularReferenceA;
import com.github.yt.web.example.entity.CircularReferenceB;
import com.github.yt.web.exception.MyBusinessExceptionEnum;
import com.github.yt.web.log.RequestLog;
import com.github.yt.web.query.WebQuery;
import com.github.yt.web.result.HttpResultEntity;
import com.github.yt.web.result.PackageResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestLog
public class TestController extends BaseController {

    // 自动包装返回体1
    @GetMapping("test1")
    @RequestLog
    public void testAutoPackage1() {
    }

    // 自动包装返回体2
    @GetMapping("test2")
    @RequestLog(false)
    public Map test2() {
        Map<String, String> result = new HashMap<>();
        result.put("key222", "value222");
        return result;
    }

    /**
     * 返回map指定泛型这种情况不支持
     *
     * @return
     */
    @GetMapping("test22")
    @RequestLog(false)
    public Map<String, String> test22() {
        Map<String, String> result = new HashMap<>();
        result.put("key222", "value222");
        return result;
    }

    @GetMapping("test23")
    public List<String> test23() {
        return Arrays.asList("222", "333");
    }

    // 统一异常处理，未知异常
    @GetMapping("test3")
    public void testUnknownException() {
        throw new RuntimeException("未知异常");
    }

    // 统一异常处理，未知异常
    @GetMapping("test31")
    public void testUnknownException2() {
        try {
            try {
                System.out.println(1 / 0);
            } catch (Exception e1) {
                throw new RuntimeException("未知异常", e1);
            }
        } catch (Exception e) {
            throw new BaseAccidentException(MyBusinessExceptionEnum.CODE_1001, e, "包装异常");
        }
    }

    // 统一异常处理，已知异常（自定义异常）
    @GetMapping("test4")
    public void testBusinessException() {
        throw new BaseAccidentException(MyBusinessExceptionEnum.CODE_1001, "没有权限");
    }

    // 统一异常处理，已知异常（自定义异常）
    @GetMapping("test5")
    @PackageResponseBody(false)
    public void test5() {
        throw new BaseAccidentException(MyBusinessExceptionEnum.CODE_1001, "没有权限");
    }

    @GetMapping("test51")
    public Map test51(WebQuery query) {
        Map<String, Object> result = new HashMap<>();
        result.put("pageNo", query.takePageNo());
        result.put("pageSize", query.takePageSize());
        return result;
    }

    @GetMapping("test6")
    public String test6() {
        return "222";
    }

    @GetMapping("test7")
    public Object test7() {
        return "222";
    }

    @GetMapping("test8")
    public int test8() {
        return 233;
    }

    @GetMapping("test9")
    public Object test9() {
        return null;
    }

    @GetMapping("test10")
    public String test10() {
        return null;
    }

    @GetMapping("test11")
    @PackageResponseBody(false)
    public String test11() throws JsonProcessingException {
        List<String> re = Arrays.asList("aaa", "bbb");
        return new ObjectMapper().writeValueAsString(re);
    }

    @GetMapping("test112")
    @PackageResponseBody(false)
    public String test112() throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        map.put("222", "222");
        return new ObjectMapper().writeValueAsString(map);
    }

    @GetMapping("test12")
    @PackageResponseBody(false)
    public String test12() {
        return "222";
    }

    @GetMapping("test13")
    @PackageResponseBody(false)
    public Date test13() {
        return new Date();
    }

    @GetMapping("test14")
    public Date test14() {
        return new Date();
    }

    @GetMapping("test15")
    public CircularReferenceB test15() {
        CircularReferenceB circularReferenceB = new CircularReferenceB();
        CircularReferenceA circularReferenceA = new CircularReferenceA();
        circularReferenceB.setCircularReferenceA(circularReferenceA);
        circularReferenceA.setCircularReferenceB(circularReferenceB);
        circularReferenceB.setB("ddd");
        circularReferenceA.setA("sss");
        return circularReferenceB;
    }

    @GetMapping("test16")
    public HttpResultEntity test16() {
        return result("222");
    }

}
