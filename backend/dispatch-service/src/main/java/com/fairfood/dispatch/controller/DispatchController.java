package com.fairfood.dispatch.controller;

import com.fairfood.dispatch.dto.AssignDispatchRequest;
import com.fairfood.dispatch.dto.DispatchResponse;
import com.fairfood.dispatch.service.DispatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 派单控制器
 * 提供订单派单API接口
 */
@RestController
@RequestMapping("/api/v1/dispatch")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "派单管理", description = "订单派单、待派单列表接口")
public class DispatchController {

    private final DispatchService dispatchService;

    /**
     * 派单接口
     * 将订单分配给合适的骑手
     */
    @PostMapping("/assign")
    @Operation(summary = "订单派单", description = "根据算法将订单分配给合适的骑手")
    public ResponseEntity<ApiResponse<DispatchResponse>> assignOrder(
            @RequestBody AssignDispatchRequest request) {
        log.info("收到派单请求: orderId={}, algorithm={}", request.getOrderId(), request.getAlgorithm());

        try {
            DispatchResponse response = dispatchService.assignOrder(request);
            return ResponseEntity.ok(ApiResponse.success(response, "派单成功"));
        } catch (Exception e) {
            log.error("派单失败: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 获取待派单订单列表
     */
    @GetMapping("/orders")
    @Operation(summary = "获取待派单订单", description = "获取所有待派单的订单列表")
    public ResponseEntity<ApiResponse<List<DispatchResponse>>> getPendingDispatchOrders() {
        log.info("收到获取待派单订单列表请求");

        try {
            List<DispatchResponse> orders = dispatchService.getPendingDispatchOrders();
            return ResponseEntity.ok(ApiResponse.success(orders));
        } catch (Exception e) {
            log.error("获取待派单订单列表失败: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
