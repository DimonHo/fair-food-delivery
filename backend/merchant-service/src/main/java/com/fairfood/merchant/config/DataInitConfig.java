package com.fairfood.merchant.config;

import com.fairfood.merchant.model.Merchant;
import com.fairfood.merchant.model.Product;
import com.fairfood.merchant.repository.MerchantRepository;
import com.fairfood.merchant.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * 数据初始化配置
 * 预置测试商家和商品数据
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitConfig {

    @Bean
    public CommandLineRunner initData(MerchantRepository merchantRepository, ProductRepository productRepository) {
        return args -> {
            // 避免重复初始化
            if (merchantRepository.count() > 0) {
                log.info("数据已存在，跳过初始化");
                return;
            }

            log.info("开始初始化商家和商品数据...");

            // 创建商家1: 麦当劳
            Merchant mc = Merchant.builder()
                    .name("麦当劳")
                    .description("全球知名快餐连锁品牌，汉堡、薯条、炸鸡等")
                    .imageUrl("https://example.com/mc.jpg")
                    .address("北京市朝阳区建国路100号")
                    .phone("4008-517-517")
                    .latitude(new BigDecimal("39.9042"))
                    .longitude(new BigDecimal("116.4074"))
                    .rating(new BigDecimal("4.5"))
                    .monthlySales(5000)
                    .minOrderAmount(new BigDecimal("20.00"))
                    .deliveryFee(new BigDecimal("5.00"))
                    .openTime("00:00")
                    .closeTime("23:59")
                    .status("OPEN")
                    .build();
            mc = merchantRepository.save(mc);

            // 商家1的商品
            List<Product> mcProducts = Arrays.asList(
                    Product.builder()
                            .merchantId(mc.getId())
                            .name("巨无霸套餐")
                            .description("经典巨无霸汉堡+中薯条+中可乐")
                            .imageUrl("https://example.com/mc1.jpg")
                            .price(new BigDecimal("35.00"))
                            .category("套餐")
                            .monthlySales(1000)
                            .status("AVAILABLE")
                            .build(),
                    Product.builder()
                            .merchantId(mc.getId())
                            .name("麦辣鸡腿堡")
                            .description("香辣鸡腿堡+薯条+可乐")
                            .imageUrl("https://example.com/mc2.jpg")
                            .price(new BigDecimal("28.00"))
                            .category("套餐")
                            .monthlySales(800)
                            .status("AVAILABLE")
                            .build(),
                    Product.builder()
                            .merchantId(mc.getId())
                            .name("麦乐鸡(5块)")
                            .description("香脆麦乐鸡5块")
                            .imageUrl("https://example.com/mc3.jpg")
                            .price(new BigDecimal("15.00"))
                            .category("小食")
                            .monthlySales(500)
                            .status("AVAILABLE")
                            .build()
            );
            productRepository.saveAll(mcProducts);

            // 创建商家2: 星巴克
            Merchant sb = Merchant.builder()
                    .name("星巴克")
                    .description("全球最大的咖啡连锁店，提供各类咖啡饮品")
                    .imageUrl("https://example.com/sb.jpg")
                    .address("北京市朝阳区东三环中路1号")
                    .phone("4008-236-236")
                    .latitude(new BigDecimal("39.9123"))
                    .longitude(new BigDecimal("116.4512"))
                    .rating(new BigDecimal("4.7"))
                    .monthlySales(3000)
                    .minOrderAmount(new BigDecimal("30.00"))
                    .deliveryFee(new BigDecimal("8.00"))
                    .openTime("07:00")
                    .closeTime("22:00")
                    .status("OPEN")
                    .build();
            sb = merchantRepository.save(sb);

            // 商家2的商品
            List<Product> sbProducts = Arrays.asList(
                    Product.builder()
                            .merchantId(sb.getId())
                            .name("星巴克拿铁")
                            .description("经典拿铁咖啡，大杯")
                            .imageUrl("https://example.com/sb1.jpg")
                            .price(new BigDecimal("31.00"))
                            .category("咖啡")
                            .monthlySales(1200)
                            .status("AVAILABLE")
                            .build(),
                    Product.builder()
                            .merchantId(sb.getId())
                            .name("星冰乐")
                            .description("抹茶星冰乐，大杯")
                            .imageUrl("https://example.com/sb2.jpg")
                            .price(new BigDecimal("35.00"))
                            .category("饮品")
                            .monthlySales(600)
                            .status("AVAILABLE")
                            .build(),
                    Product.builder()
                            .merchantId(sb.getId())
                            .name("美式咖啡")
                            .description("经典美式咖啡，大杯")
                            .imageUrl("https://example.com/sb3.jpg")
                            .price(new BigDecimal("24.00"))
                            .category("咖啡")
                            .monthlySales(400)
                            .status("AVAILABLE")
                            .build()
            );
            productRepository.saveAll(sbProducts);

            // 创建商家3: 海底捞
            Merchant hdl = Merchant.builder()
                    .name("海底捞火锅")
                    .description("知名火锅连锁，提供优质火锅外卖服务")
                    .imageUrl("https://example.com/hdl.jpg")
                    .address("北京市海淀区中关村大街1号")
                    .phone("4008-109-109")
                    .latitude(new BigDecimal("39.9561"))
                    .longitude(new BigDecimal("116.3198"))
                    .rating(new BigDecimal("4.8"))
                    .monthlySales(2000)
                    .minOrderAmount(new BigDecimal("100.00"))
                    .deliveryFee(new BigDecimal("15.00"))
                    .openTime("10:00")
                    .closeTime("23:00")
                    .status("OPEN")
                    .build();
            merchantRepository.save(hdl);

            log.info("商家和商品数据初始化完成，共创建3个商家，6个商品");
        };
    }
}
