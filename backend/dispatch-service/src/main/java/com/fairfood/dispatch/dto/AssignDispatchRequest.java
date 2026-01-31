package com.fairfood.dispatch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 派单请求DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignDispatchRequest {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 派单算法: NEAREST-最近骑手, LOAD_BALANCE-负载均衡
     */
    @Builder.Default
    private String algorithm = "NEAREST";

    /**
     * 强制指定骑手ID (可选)
     */
    private Long forceRiderId;
}
