package com.fairfood.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 */
@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单号 (唯一)
     */
    @Column(nullable = false, unique = true, length = 50)
    private String orderNo;

    /**
     * 用户ID
     */
    @Column(nullable = false)
    private Long userId;

    /**
     * 商家ID
     */
    @Column(nullable = false)
    private Long merchantId;

    /**
     * 收货地址ID
     */
    @Column(nullable = false)
    private Long addressId;

    /**
     * 订单总金额
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    /**
     * 配送费
     */
    @Column(precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal deliveryFee = BigDecimal.ZERO;

    /**
     * 优惠金额
     */
    @Column(precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal discountAmount = BigDecimal.ZERO;

    /**
     * 实际支付金额
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal payAmount;

    /**
     * 订单状态: PENDING-待付款, CONFIRMED-已确认, PREPARING-准备中, DELIVERING-配送中, COMPLETED-已完成, CANCELLED-已取消
     */
    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "PENDING";

    /**
     * 支付方式: ONLINE-在线支付, COD-货到付款
     */
    @Column(length = 20)
    @Builder.Default
    private String payMethod = "ONLINE";

    /**
     * 备注
     */
    @Column(length = 500)
    private String remark;

    /**
     * 预计送达时间
     */
    private LocalDateTime estimatedDeliveryTime;

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
