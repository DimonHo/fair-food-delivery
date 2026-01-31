package com.fairfood.dispatch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 骑手实体类
 */
@Entity
@Table(name = "riders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 骑手姓名
     */
    @Column(nullable = false, length = 50)
    private String name;

    /**
     * 骑手电话
     */
    @Column(nullable = false, length = 20)
    private String phone;

    /**
     * 状态: AVAILABLE-空闲, BUSY-忙碌, OFFLINE-离线
     */
    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "OFFLINE";

    /**
     * 当前纬度
     */
    private Double latitude;

    /**
     * 当前经度
     */
    private Double longitude;

    /**
     * 今日配送单数
     */
    @Builder.Default
    private Integer todayDeliveries = 0;

    /**
     * 评分 (0-5)
     */
    @Builder.Default
    private Double rating = 5.0;

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
