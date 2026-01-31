package com.fairfood.address.service;

import com.fairfood.address.dto.AddressResponse;
import com.fairfood.address.dto.CreateAddressRequest;
import com.fairfood.address.model.Address;
import com.fairfood.address.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 地址服务实现类
 * 处理地址管理业务逻辑
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;

    /**
     * 获取用户地址列表
     */
    public List<AddressResponse> getUserAddresses(Long userId) {
        log.info("获取用户地址列表: userId={}", userId);

        return addressRepository.findByUserIdOrderByIsDefaultDescCreatedAtDesc(userId)
                .stream()
                .map(this::toAddressResponse)
                .collect(Collectors.toList());
    }

    /**
     * 添加新地址
     */
    @Transactional
    public AddressResponse addAddress(Long userId, CreateAddressRequest request) {
        log.info("添加新地址: userId={}, receiver={}", userId, request.getReceiverName());

        // 如果设为默认地址，先取消其他默认地址
        if (Boolean.TRUE.equals(request.getIsDefault())) {
            addressRepository.findByUserIdAndIsDefaultTrue(userId)
                    .ifPresent(defaultAddr -> {
                        defaultAddr.setIsDefault(false);
                        addressRepository.save(defaultAddr);
                    });
        }

        // 如果是第一个地址，设为默认
        List<Address> existingAddresses = addressRepository.findByUserIdOrderByIsDefaultDescCreatedAtDesc(userId);
        boolean shouldBeDefault = request.getIsDefault() != null && request.getIsDefault()
                || existingAddresses.isEmpty();

        // 创建地址
        Address address = Address.builder()
                .userId(userId)
                .receiverName(request.getReceiverName())
                .receiverPhone(request.getReceiverPhone())
                .province(request.getProvince())
                .city(request.getCity())
                .district(request.getDistrict())
                .detailAddress(request.getDetailAddress())
                .label(request.getLabel() != null ? request.getLabel() : "OTHER")
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .isDefault(shouldBeDefault)
                .build();

        address = addressRepository.save(address);

        log.info("地址添加成功: addressId={}", address.getId());

        return toAddressResponse(address);
    }

    /**
     * 删除地址
     */
    @Transactional
    public void deleteAddress(Long userId, Long addressId) {
        log.info("删除地址: userId={}, addressId={}", userId, addressId);

        // 验证地址归属
        Address address = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new RuntimeException("地址不存在或无权访问"));

        addressRepository.delete(address);

        log.info("地址删除成功: addressId={}", addressId);
    }

    /**
     * 将Address实体转换为响应DTO
     */
    private AddressResponse toAddressResponse(Address address) {
        // 拼接完整地址
        String fullAddress = String.format("%s%s%s%s",
                address.getProvince() != null ? address.getProvince() : "",
                address.getCity() != null ? address.getCity() : "",
                address.getDistrict() != null ? address.getDistrict() : "",
                address.getDetailAddress());

        return AddressResponse.builder()
                .id(address.getId())
                .userId(address.getUserId())
                .receiverName(address.getReceiverName())
                .receiverPhone(address.getReceiverPhone())
                .province(address.getProvince())
                .city(address.getCity())
                .district(address.getDistrict())
                .detailAddress(address.getDetailAddress())
                .fullAddress(fullAddress.trim())
                .label(address.getLabel())
                .labelText(getLabelText(address.getLabel()))
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .isDefault(address.getIsDefault())
                .createdAt(address.getCreatedAt())
                .build();
    }

    /**
     * 获取标签描述文本
     */
    private String getLabelText(String label) {
        if (label == null) return "其他";
        return switch (label) {
            case "HOME" -> "家";
            case "COMPANY" -> "公司";
            case "SCHOOL" -> "学校";
            default -> "其他";
        };
    }
}
