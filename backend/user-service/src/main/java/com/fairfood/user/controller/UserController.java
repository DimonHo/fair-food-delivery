package com.fairfood.user.controller;

import com.fairfood.user.dto.*;
import com.fairfood.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 * 提供用户相关的API接口
 */
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "用户管理", description = "用户注册、登录、信息管理接口")
public class UserController {

    private final UserService userService;

    /**
     * 用户注册接口
     * @param request 注册请求
     * @return 认证响应(包含JWT令牌)
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "创建新用户账号并返回JWT令牌")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest request) {
        log.info("收到注册请求: {}", request.getUsername());

        try {
            AuthResponse response = userService.register(request);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            log.error("注册失败: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 用户登录接口
     * @param request 登录请求
     * @return 认证响应(包含JWT令牌)
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "验证用户凭据并返回JWT令牌")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        log.info("收到登录请求: {}", request.getUsername());

        try {
            AuthResponse response = userService.login(request);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            log.error("登录失败: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 获取用户信息接口
     * @param userId 用户ID
     * @return 用户信息
     */
    @GetMapping("/profile")
    @Operation(summary = "获取用户信息", description = "根据用户ID获取用户详细信息")
    public ResponseEntity<ApiResponse<UserResponse>> getProfile(
            @RequestHeader("X-User-Id") Long userId) {
        log.info("收到获取用户信息请求: userId={}", userId);

        try {
            UserResponse response = userService.getUserProfile(userId);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            log.error("获取用户信息失败: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
