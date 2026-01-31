package com.fairfood.merchant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商家实体类
 */
@Entity
@Table(name = "merchants")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商家名称
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * 商家描述
     */
    @Column(length = 500)
    private String description;

    /**
     * 商家图片URL
     */
    @Column(length = 500)
    private String imageUrl;

    /**
     * 商家地址
     */
    @Column(length = 200)
    private String address;

    /**
     * 商家电话
     */
    @Column(length = 20)
    private String phone;

    /**
     * 纬度
     */
    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    /**
     * 经度
     */
    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    /**
     * 评分 (0-5)
     */
    @Column(precision = 2, scale = 1)
    @Builder.Default
    private BigDecimal rating = BigDecimal.ZERO;

    /**
     * 月销售量
     */
    @Builder.Default
    private Integer monthlySales = 0;

    /**
     * 起送价
     */
    @Column(precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal minOrderAmount = BigDecimal.ZERO;

    /**
     * 配送费
     */
    @Column(precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal deliveryFee = BigDecimal.ZERO;

    /**
     * 营业开始时间 (格式: HH:mm)
     */
    @Column(length = 5)
    private String openTime;

    /**
     * 营业结束时间 (格式: HH:mm)
     */
    @Column(length = 5)
    private String closeTime;

    /**
     * 商家状态: OPEN-营业中, CLOSED-已关闭, PAUSED-暂停营业
     */
    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "OPEN";

    /**
     * 创建时间
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
