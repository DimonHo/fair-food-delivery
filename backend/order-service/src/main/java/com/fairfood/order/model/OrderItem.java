package com.fairfood.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 订单项实体类
 */
@Entity
@Table(name = "order_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 所属订单ID
     */
    @Column(nullable = false)
    private Long orderId;

    /**
     * 商品ID
     */
    @Column(nullable = false)
    private Long productId;

    /**
     * 商品名称
     */
    @Column(nullable = false, length = 100)
    private String productName;

    /**
     * 商品单价
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /**
     * 购买数量
     */
    @Column(nullable = false)
    private Integer quantity;

    /**
     * 小计金额
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;
}
