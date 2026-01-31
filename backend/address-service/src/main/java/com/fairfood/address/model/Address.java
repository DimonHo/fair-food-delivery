package com.fairfood.address.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 地址实体类
 */
@Entity
@Table(name = "addresses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(nullable = false)
    private Long userId;

    /**
     * 收货人姓名
     */
    @Column(nullable = false, length = 50)
    private String receiverName;

    /**
     * 收货人电话
     */
    @Column(nullable = false, length = 20)
    private String receiverPhone;

    /**
     * 省份
     */
    @Column(length = 50)
    private String province;

    /**
     * 城市
     */
    @Column(length = 50)
    private String city;

    /**
     * 区县
     */
    @Column(length = 50)
    private String district;

    /**
     * 详细地址
     */
    @Column(nullable = false, length = 200)
    private String detailAddress;

    /**
     * 标签: HOME-家, COMPANY-公司, SCHOOL-学校, OTHER-其他
     */
    @Column(length = 20)
    @Builder.Default
    private String label = "OTHER";

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
     * 是否默认地址
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean isDefault = false;

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
