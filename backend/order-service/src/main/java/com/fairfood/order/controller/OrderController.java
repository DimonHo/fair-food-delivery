package com.fairfood.order.controller;

import com.fairfood.order.dto.*;
import com.fairfood.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单控制器
 * 提供订单相关API接口
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "订单管理", description = "订单创建、查询、状态更新接口")
public class OrderController {

    private final OrderService orderService;

    /**
     * 创建订单
     */
    @PostMapping
    @Operation(summary = "创建订单", description = "创建新订单，需要用户提供ID")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody CreateOrderRequest request) {
        log.info("收到创建订单请求: userId={}", userId);

        try {
            OrderResponse order = orderService.createOrder(userId, request);
            return ResponseEntity.ok(ApiResponse.success(order, "订单创建成功"));
        } catch (Exception e) {
            log.error("创建订单失败: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 获取订单列表
     */
    @GetMapping
    @Operation(summary = "获取订单列表", description = "获取当前用户的订单列表，可按状态筛选")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrderList(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam(required = false) String status) {
        log.info("收到获取订单列表请求: userId={}, status={}", userId, status);

        try {
            List<OrderResponse> orders = orderService.getUserOrders(userId, status);
            return ResponseEntity.ok(ApiResponse.success(orders));
        } catch (Exception e) {
            log.error("获取订单列表失败: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 获取订单详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取订单详情", description = "根据订单ID获取订单详细信息")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderDetail(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id) {
        log.info("收到获取订单详情请求: userId={}, orderId={}", userId, id);

        try {
            OrderResponse order = orderService.getOrderDetail(id, userId);
            return ResponseEntity.ok(ApiResponse.success(order));
        } catch (Exception e) {
            log.error("获取订单详情失败: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 更新订单状态
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "更新订单状态", description = "更新订单状态，状态值: PENDING, CONFIRMED, PREPARING, DELIVERING, COMPLETED, CANCELLED")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody UpdateStatusRequest request) {
        log.info("收到更新订单状态请求: orderId={}, newStatus={}", id, request.getStatus());

        try {
            OrderResponse order = orderService.updateOrderStatus(id, request);
            return ResponseEntity.ok(ApiResponse.success(order, "状态更新成功"));
        } catch (Exception e) {
            log.error("更新订单状态失败: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
