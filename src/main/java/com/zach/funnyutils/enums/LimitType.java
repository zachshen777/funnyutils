package com.zach.funnyutils.enums;

/**
 * 限流类型枚举
 */
public enum LimitType {
    IP,            // 根据IP限流
    USER,          // 根据用户ID限流
    CUSTOM_KEY     // 自定义限流Key
}

