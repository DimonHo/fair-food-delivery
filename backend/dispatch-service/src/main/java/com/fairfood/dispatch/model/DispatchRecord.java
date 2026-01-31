package com.fairfood.dispatch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 派单记录实体类
 */
@Entity
@Table(name = "dispatch_records")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DispatchRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单ID
     */
    @Column(nullable = false)
    private Long orderId;

    /**
     * 订单号
     */
    @Column(nullable = false, length = 50)
    private String orderNo;

    /**
     * 骑手ID
     */
    @Column(nullable = false)
    private Long riderId;

    /**
     * 骑手姓名
     */
    @Column(length = 50)
    private String riderName;

    /**
     * 派单状态: ASSIGNED-已派单, ACCEPTED-已接单, DELIVERING-配送中, COMPLETED-已完成, FAILED-失败
     */
    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "ASSIGNED";

    /**
     * 派单算法 (如: NEAREST, LOAD_BALANCE)
     */
    @Column(length = 50)
    private String algorithm;

    /**
     * 预计送达时间
     */
    private LocalDateTime estimatedDeliveryTime;

    /**
     * 实际送达时间
     */
    private LocalDateTime actualDeliveryTime;

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
