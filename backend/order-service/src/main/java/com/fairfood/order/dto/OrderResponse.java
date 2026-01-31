package com.fairfood.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单响应DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private String orderNo;
    private Long userId;
    private Long merchantId;
    private String merchantName;
    private Long addressId;
    private String addressDetail;
    private BigDecimal totalAmount;
    private BigDecimal deliveryFee;
    private BigDecimal discountAmount;
    private BigDecimal payAmount;
    private String status;
    private String statusText;
    private String payMethod;
    private String remark;
    private LocalDateTime estimatedDeliveryTime;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;

    /**
     * 订单项响应
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemResponse {
        private Long id;
        private Long productId;
        private String productName;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal subtotal;
    }
}
