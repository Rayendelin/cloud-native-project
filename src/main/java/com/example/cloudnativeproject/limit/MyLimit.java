package com.example.cloudnativeproject.limit;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface MyLimit {
    /**
     * key
     */
    String key() default "";

    /**
     * 每秒访问限制，默认为每秒100次
     */
    double permitPerSecond() default 100;

    /**
     * 获取令牌的最大等待的时间，默认为1秒种
     */
    long time() default 1000;

    /**
     * 设定时间单位为毫秒
     */
    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

}
