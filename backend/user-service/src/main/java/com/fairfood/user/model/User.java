package com.fairfood.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户实体类
 * 对应数据库中的用户表
 */
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /**
     * 密码 (加密存储)
     */
    @Column(nullable = false, length = 255)
    private String password;

    /**
     * 邮箱
     */
    @Column(length = 100)
    private String email;

    /**
     * 手机号
     */
    @Column(length = 20)
    private String phone;

    /**
     * 用户类型: USER-普通用户, MERCHANT-商家, ADMIN-管理员
     */
    @Column(nullable = false, length = 20)
    @Builder.Default
    private String userType = "USER";

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
