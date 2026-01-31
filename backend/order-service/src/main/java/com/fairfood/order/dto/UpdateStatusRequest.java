package com.fairfood.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 更新订单状态请求DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusRequest {

    /**
     * 新状态
     */
    private String status;

    /**
     * 状态变更备注
     */
    private String remark;
}
