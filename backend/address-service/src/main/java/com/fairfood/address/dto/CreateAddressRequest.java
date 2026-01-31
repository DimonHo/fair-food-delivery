package com.fairfood.address.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 添加地址请求DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAddressRequest {

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人电话
     */
    private String receiverPhone;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区县
     */
    private String district;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 标签
     */
    private String label;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 是否设为默认地址
     */
    private Boolean isDefault;
}
