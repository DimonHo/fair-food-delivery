package com.fairfood.merchant.service;

import com.fairfood.merchant.dto.MerchantResponse;
import com.fairfood.merchant.dto.ProductResponse;
import com.fairfood.merchant.model.Merchant;
import com.fairfood.merchant.model.Product;
import com.fairfood.merchant.repository.MerchantRepository;
import com.fairfood.merchant.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商家服务实现类
 * 处理商家查询、商品查询等业务逻辑
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MerchantService {

    private final MerchantRepository merchantRepository;
    private final ProductRepository productRepository;

    /**
     * 获取商家列表
     * @param latitude  纬度 (可选，用于计算距离)
     * @param longitude 经度 (可选)
     * @param radius    搜索半径(公里)
     * @return 商家列表
     */
    public List<MerchantResponse> getMerchantList(
            BigDecimal latitude, BigDecimal longitude, Double radius) {

        // 如果提供了坐标，查询附近商家
        if (latitude != null && longitude != null && radius != null) {
            log.info("查询附近商家: lat={}, lng={}, radius={}km", latitude, longitude, radius);
            return findNearbyMerchants(latitude, longitude, radius);
        }

        // 否则返回所有商家
        log.info("获取所有商家列表");
        return merchantRepository.findAll().stream()
                .map(this::toMerchantResponse)
                .collect(Collectors.toList());
    }

    /**
     * 查询附近商家
     */
    private List<MerchantResponse> findNearbyMerchants(
            BigDecimal latitude, BigDecimal longitude, Double radius) {

        List<Merchant> merchants = merchantRepository.findNearbyMerchants(latitude, longitude, radius);

        return merchants.stream()
                .map(merchant -> {
                    MerchantResponse response = toMerchantResponse(merchant);
                    // 计算距离
                    response.setDistance(calculateDistance(
                            latitude.doubleValue(),
                            longitude.doubleValue(),
                            merchant.getLatitude().doubleValue(),
                            merchant.getLongitude().doubleValue()));
                    return response;
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取商家详情
     * @param merchantId 商家ID
     * @return 商家详情
     */
    public MerchantResponse getMerchantDetail(Long merchantId) {
        log.info("获取商家详情: merchantId={}", merchantId);

        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new RuntimeException("商家不存在: " + merchantId));

        return toMerchantResponse(merchant);
    }

    /**
     * 获取商家的商品列表
     * @param merchantId 商家ID
     * @return 商品列表
     */
    public List<ProductResponse> getMerchantProducts(Long merchantId) {
        log.info("获取商家商品列表: merchantId={}", merchantId);

        // 检查商家是否存在
        if (!merchantRepository.existsById(merchantId)) {
            throw new RuntimeException("商家不存在: " + merchantId);
        }

        return productRepository.findByMerchantIdAndStatus(merchantId, "AVAILABLE")
                .stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());
    }

    /**
     * 将Merchant实体转换为响应DTO
     */
    private MerchantResponse toMerchantResponse(Merchant merchant) {
        return MerchantResponse.builder()
                .id(merchant.getId())
                .name(merchant.getName())
                .description(merchant.getDescription())
                .imageUrl(merchant.getImageUrl())
                .address(merchant.getAddress())
                .phone(merchant.getPhone())
                .latitude(merchant.getLatitude())
                .longitude(merchant.getLongitude())
                .rating(merchant.getRating())
                .monthlySales(merchant.getMonthlySales())
                .minOrderAmount(merchant.getMinOrderAmount())
                .deliveryFee(merchant.getDeliveryFee())
                .openTime(merchant.getOpenTime())
                .closeTime(merchant.getCloseTime())
                .status(merchant.getStatus())
                .build();
    }

    /**
     * 将Product实体转换为响应DTO
     */
    private ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .merchantId(product.getMerchantId())
                .name(product.getName())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .price(product.getPrice())
                .category(product.getCategory())
                .monthlySales(product.getMonthlySales())
                .status(product.getStatus())
                .build();
    }

    /**
     * 计算两点之间的距离 (Haversine公式)
     */
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371; // 地球半径(公里)

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return BigDecimal.valueOf(R * c)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
