package com.fairfood.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 创建订单请求DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    /**
     * 商家ID
     */
    private Long merchantId;

    /**
     * 收货地址ID
     */
    private Long addressId;

    /**
     * 订单项列表
     */
    private List<OrderItemRequest> items;

    /**
     * 备注
     */
    private String remark;

    /**
     * 支付方式
     */
    private String payMethod;

    /**
     * 订单项请求
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemRequest {
        private Long productId;
        private Integer quantity;
    }
}
