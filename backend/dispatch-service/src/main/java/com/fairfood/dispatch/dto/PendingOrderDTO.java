package com.fairfood.dispatch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 待派单订单DTO (从订单服务获取的数据)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PendingOrderDTO {

    private Long id;
    private String orderNo;
    private Long userId;
    private Long merchantId;
    private String merchantName;
    private Long addressId;
    private String addressDetail;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;

    // 商家位置 (用于计算距离)
    private BigDecimal merchantLatitude;
    private BigDecimal merchantLongitude;

    // 用户位置 (用于计算距离)
    private BigDecimal userLatitude;
    private BigDecimal userLongitude;
}
