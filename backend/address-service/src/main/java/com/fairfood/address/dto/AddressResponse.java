package com.fairfood.address.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 地址响应DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {

    private Long id;
    private Long userId;
    private String receiverName;
    private String receiverPhone;
    private String province;
    private String city;
    private String district;
    private String detailAddress;
    private String fullAddress;
    private String label;
    private String labelText;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Boolean isDefault;
    private LocalDateTime createdAt;
}
