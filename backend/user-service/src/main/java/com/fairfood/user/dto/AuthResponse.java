package com.fairfood.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 认证响应DTO - 包含JWT令牌
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    /**
     * JWT访问令牌
     */
    private String token;

    /**
     * 令牌类型
     */
    @Builder.Default
    private String tokenType = "Bearer";

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 过期时间(秒)
     */
    private Long expiresIn;
}
