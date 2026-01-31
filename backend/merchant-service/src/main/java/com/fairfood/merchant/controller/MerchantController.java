package com.fairfood.merchant.controller;

import com.fairfood.merchant.dto.MerchantResponse;
import com.fairfood.merchant.dto.ProductResponse;
import com.fairfood.merchant.service.MerchantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商家控制器
 * 提供商家相关API接口
 */
@RestController
@RequestMapping("/api/v1/merchants")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "商家管理", description = "商家列表、详情、商品查询接口")
public class MerchantController {

    private final MerchantService merchantService;

    /**
     * 获取商家列表
     * 支持经纬度附近商家查询
     */
    @GetMapping
    @Operation(summary = "获取商家列表", description = "获取所有商家或根据经纬度查询附近商家")
    public ResponseEntity<ApiResponse<List<MerchantResponse>>> getMerchantList(
            @RequestParam(required = false) BigDecimal latitude,
            @RequestParam(required = false) BigDecimal longitude,
            @RequestParam(required = false, defaultValue = "5.0") Double radius) {

        log.info("收到获取商家列表请求: lat={}, lng={}, radius={}", latitude, longitude, radius);

        try {
            List<MerchantResponse> merchants = merchantService.getMerchantList(latitude, longitude, radius);
            return ResponseEntity.ok(ApiResponse.success(merchants));
        } catch (Exception e) {
            log.error("获取商家列表失败: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 获取商家详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取商家详情", description = "根据商家ID获取商家详细信息")
    public ResponseEntity<ApiResponse<MerchantResponse>> getMerchantDetail(@PathVariable Long id) {
        log.info("收到获取商家详情请求: id={}", id);

        try {
            MerchantResponse merchant = merchantService.getMerchantDetail(id);
            return ResponseEntity.ok(ApiResponse.success(merchant));
        } catch (Exception e) {
            log.error("获取商家详情失败: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 获取商家的商品列表
     */
    @GetMapping("/{id}/products")
    @Operation(summary = "获取商品列表", description = "根据商家ID获取该商家的商品列表")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getMerchantProducts(@PathVariable Long id) {
        log.info("收到获取商家商品列表请求: merchantId={}", id);

        try {
            List<ProductResponse> products = merchantService.getMerchantProducts(id);
            return ResponseEntity.ok(ApiResponse.success(products));
        } catch (Exception e) {
            log.error("获取商家商品列表失败: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
