package com.zach.funnyutils.exception;

import lombok.Getter;

@Getter
public class RateLimitException extends RuntimeException {
    private final long retryAfter; // 重试时间(毫秒)

    public RateLimitException(String message, long retryAfter) {
        super(message);
        this.retryAfter = retryAfter;
    }

}
