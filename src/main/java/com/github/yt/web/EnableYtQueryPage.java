package com.github.yt.web;


import com.github.yt.web.query.QueryControllerAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启分页参数传入功能
 * 分页参数传入到Query对象中
 * 将传入参数的
 *
 * @author liujiasheng
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({
        YtWebConfig.class,
        QueryControllerAspect.class,
})
public @interface EnableYtQueryPage {
}
