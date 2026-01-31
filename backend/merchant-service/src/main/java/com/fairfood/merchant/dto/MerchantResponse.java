package com.fairfood.merchant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 商家响应DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantResponse {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private String address;
    private String phone;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal rating;
    private Integer monthlySales;
    private BigDecimal minOrderAmount;
    private BigDecimal deliveryFee;
    private String openTime;
    private String closeTime;
    private String status;

    /**
     * 距离(公里) - 用于附近商家列表
     */
    private Double distance;
}
