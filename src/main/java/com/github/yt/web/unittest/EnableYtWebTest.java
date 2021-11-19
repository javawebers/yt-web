package com.github.yt.web.unittest;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 引入单元测试
 * <p>
 * 单元测试同时需要添加注解 @AutoConfigureMockMvc
 *
 * @author liujiasheng
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({
        InitApplication.class,
})
public @interface EnableYtWebTest {
}
