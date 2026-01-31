package com.fairfood.user.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一API响应格式
 * 所有API返回均使用此格式: {"code": 0, "message": "success", "data": {...}}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    /**
     * 响应码: 0-成功, 非0-失败
     */
    @Builder.Default
    private Integer code = 0;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 成功响应
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(0)
                .message("success")
                .data(data)
                .build();
    }

    /**
     * 成功响应(带消息)
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .code(0)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * 错误响应
     */
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .code(-1)
                .message(message)
                .build();
    }

    /**
     * 错误响应(带错误码)
     */
    public static <T> ApiResponse<T> error(Integer code, String message) {
        return ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .build();
    }
}
