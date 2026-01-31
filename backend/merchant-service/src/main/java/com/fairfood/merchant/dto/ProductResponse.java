package com.fairfood.merchant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 商品响应DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private Long merchantId;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private String category;
    private Integer monthlySales;
    private String status;
}
