package com.zach.funnyutils.aspect;

import com.zach.funnyutils.annotation.RateLimit;
import com.zach.funnyutils.enums.LimitType;
import com.zach.funnyutils.exception.RateLimitException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

@Aspect
@Component
public class RateLimitAspect {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisScript<Long> rateLimitScript;

    @Before("@annotation(com.zach.funnyutils.annotation.RateLimit)")
    public void before(JoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);

        if (rateLimit == null) {
            return;
        }

        LimitType limitType = rateLimit.limitType();

        // 生成限流Key
        String combineKey = getCombineKey(rateLimit, limitType);

        // 执行Lua脚本
        List<String> keys = Collections.singletonList(combineKey);
        long result = redisTemplate.execute(
                rateLimitScript,
                keys,
                rateLimit.count(),
                rateLimit.period()
        );

        if (result > 0) {
            // 超出限流次数
            throw new RateLimitException("访问过于频繁，请稍后再试", result);
        }
    }

    private String getCombineKey(RateLimit rateLimit, LimitType limitType) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        StringBuilder keyBuilder = new StringBuilder(rateLimit.key());

        switch (limitType) {
            case IP:
                keyBuilder.append(getIpAddress(request));
                break;
//            case USER:
//                // 这里需要根据实际情况获取用户ID
//                String userId = getUserIdFromRequest(request);
//                keyBuilder.append(userId);
//                break;
            case CUSTOM_KEY:
                // 自定义Key逻辑
                break;
            default:
                throw new IllegalArgumentException("Unsupported limit type");
        }

        keyBuilder.append(":").append(request.getRequestURI());
        return keyBuilder.toString();
    }

    private String getIpAddress(HttpServletRequest request) {
        // 简化的IP获取逻辑，实际项目中可能需要更复杂的实现
        String xffHeader = request.getHeader("X-Forwarded-For");
        if (xffHeader == null) {
            return request.getRemoteAddr();
        }
        return xffHeader.split(",")[0];
    }

}