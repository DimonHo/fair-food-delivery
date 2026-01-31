package com.fairfood.address.controller;

import com.fairfood.address.dto.AddressResponse;
import com.fairfood.address.dto.CreateAddressRequest;
import com.fairfood.address.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 地址控制器
 * 提供地址管理API接口
 */
@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "地址管理", description = "地址列表、添加、删除接口")
public class AddressController {

    private final AddressService addressService;

    /**
     * 获取用户地址列表
     */
    @GetMapping
    @Operation(summary = "获取地址列表", description = "获取当前用户的所有地址")
    public ResponseEntity<ApiResponse<List<AddressResponse>>> getAddressList(
            @RequestHeader("X-User-Id") Long userId) {
        log.info("收到获取地址列表请求: userId={}", userId);

        try {
            List<AddressResponse> addresses = addressService.getUserAddresses(userId);
            return ResponseEntity.ok(ApiResponse.success(addresses));
        } catch (Exception e) {
            log.error("获取地址列表失败: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 添加新地址
     */
    @PostMapping
    @Operation(summary = "添加地址", description = "添加新的收货地址")
    public ResponseEntity<ApiResponse<AddressResponse>> addAddress(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody CreateAddressRequest request) {
        log.info("收到添加地址请求: userId={}, receiver={}", userId, request.getReceiverName());

        try {
            AddressResponse address = addressService.addAddress(userId, request);
            return ResponseEntity.ok(ApiResponse.success(address, "地址添加成功"));
        } catch (Exception e) {
            log.error("添加地址失败: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 删除地址
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除地址", description = "根据地址ID删除地址")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id) {
        log.info("收到删除地址请求: userId={}, addressId={}", userId, id);

        try {
            addressService.deleteAddress(userId, id);
            return ResponseEntity.ok(ApiResponse.success(null, "地址删除成功"));
        } catch (Exception e) {
            log.error("删除地址失败: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
