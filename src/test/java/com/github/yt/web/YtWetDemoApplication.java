package com.github.yt.web;

import com.github.yt.commons.exception.BaseAccidentException;
import com.github.yt.web.exception.MyBusinessExceptionEnum;
import com.github.yt.web.log.RequestLog;
import com.github.yt.web.result.PackageResponseBody;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@RestController
@EnableYtWeb
@EnableSwagger2
@RequestLog
public class YtWetDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(YtWetDemoApplication.class, args);
    }

    // 自动包装返回体1
    @GetMapping("test1")
    @RequestLog
    public void testAutoPackage1() {
    }

    // 自动包装返回体2
    @GetMapping("test2")
    @RequestLog(false)
    public Map testAutoPackage2() {
        Map result = new HashMap<>();
        result.put("key222", "value222");
        return result;
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
                int a = 1 / 0;
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
}
