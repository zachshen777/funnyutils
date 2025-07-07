package com.zach.funnyutils.annotation;

import com.zach.funnyutils.enums.LimitType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    String key() default "rate_limit";
    int period() default 60;  // 限流周期（秒）
    int count() default 20;  // 允许的最大请求次数
    String message() default "请求过于频繁，请稍后再试";
    /**
     * 限流类型
     */
    LimitType limitType() default LimitType.IP;
}