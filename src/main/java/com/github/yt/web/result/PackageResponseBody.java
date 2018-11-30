package com.github.yt.web.result;

import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否自动包装返回体
 * 返回体实现BaseResultConfig，默认SimpleResultConfig
 * 作用于controller的类或者方法上
 * 优先级，方法-类-全局配置(yt.result.packageResponseBody)
 * @author liujiasheng
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ResponseBody
public @interface PackageResponseBody  {
    boolean value() default true;
}
