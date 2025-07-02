package com.zach.funnyutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class FunnyUtilsApplication {

    private static final Logger log = LoggerFactory.getLogger(FunnyUtilsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FunnyUtilsApplication.class, args);
        // 项目启动成功后打印日志
        log.info("=====================================");
        log.info("✅ 趣多多工具箱后端项目启动成功 ✅");
        log.info("服务地址: http://localhost:8080");
        log.info("=====================================");
    }
}
    