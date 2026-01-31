package com.fairfood.dispatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 派单服务启动类
 * 提供订单派单功能
 */
@SpringBootApplication
public class DispatchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DispatchServiceApplication.class, args);
    }
}
