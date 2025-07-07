package com.zach.funnyutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FunnyUtilsApplication {

    private static final Logger log = LoggerFactory.getLogger(FunnyUtilsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FunnyUtilsApplication.class, args);
        // 项目启动成功后打印日志
        log.info("=====================================");
        log.info("✅ funnyutils start successfully ✅");
        log.info("server address: http://localhost:8080");
        log.info("=====================================");
    }
}
    