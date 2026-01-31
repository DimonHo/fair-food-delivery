package com.fairfood.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 商家服务启动类
 * 提供商家列表、详情、商品列表等功能
 */
@SpringBootApplication
public class MerchantServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MerchantServiceApplication.class, args);
    }
}
