package com.fairfood.merchant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体类
 */
@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 所属商家ID
     */
    @Column(nullable = false)
    private Long merchantId;

    /**
     * 商品名称
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * 商品描述
     */
    @Column(length = 500)
    private String description;

    /**
     * 商品图片URL
     */
    @Column(length = 500)
    private String imageUrl;

    /**
     * 商品价格
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /**
     * 分类名称
     */
    @Column(length = 50)
    private String category;

    /**
     * 月销售量
     */
    @Builder.Default
    private Integer monthlySales = 0;

    /**
     * 商品状态: AVAILABLE-在售, SOLD_OUT-售罄, OFF_SHELF-下架
     */
    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "AVAILABLE";

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
