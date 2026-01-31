package com.fairfood.dispatch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 派单响应DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DispatchResponse {

    private Long dispatchId;
    private Long orderId;
    private String orderNo;
    private Long riderId;
    private String riderName;
    private String riderPhone;
    private String status;
    private String statusText;
    private String algorithm;
    private LocalDateTime estimatedDeliveryTime;
    private LocalDateTime createdAt;
}
