package com.fairfood.dispatch.service;

import com.fairfood.dispatch.dto.AssignDispatchRequest;
import com.fairfood.dispatch.dto.DispatchResponse;
import com.fairfood.dispatch.dto.PendingOrderDTO;
import com.fairfood.dispatch.model.DispatchRecord;
import com.fairfood.dispatch.model.Rider;
import com.fairfood.dispatch.repository.DispatchRecordRepository;
import com.fairfood.dispatch.repository.RiderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 派单服务实现类
 * 实现简单派单算法
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DispatchService {

    private final RiderRepository riderRepository;
    private final DispatchRecordRepository dispatchRecordRepository;

    // 模拟商家位置数据
    private static final java.util.Map<Long, java.util.Map<String, Double>> MERCHANT_LOCATIONS = java.util.Map.of(
            1L, java.util.Map.of("lat", 39.9042, "lng", 116.4074),
            2L, java.util.Map.of("lat", 39.9123, "lng", 116.4512),
            3L, java.util.Map.of("lat", 39.9561, "lng", 116.3198)
    );

    /**
     * 派单接口
     * 根据算法将订单分配给合适的骑手
     */
    @Transactional
    public DispatchResponse assignOrder(AssignDispatchRequest request) {
        log.info("开始派单: orderId={}, algorithm={}", request.getOrderId(), request.getAlgorithm());

        // 检查是否已有派单记录
        Optional<DispatchRecord> existingRecord = dispatchRecordRepository.findByOrderId(request.getOrderId());
        if (existingRecord.isPresent()) {
            throw new RuntimeException("该订单已有派单记录");
        }

        // 选择骑手
        Rider selectedRider;
        if (request.getForceRiderId() != null) {
            // 强制指定骑手
            selectedRider = riderRepository.findById(request.getForceRiderId())
                    .orElseThrow(() -> new RuntimeException("骑手不存在"));
            if (!"AVAILABLE".equals(selectedRider.getStatus())) {
                throw new RuntimeException("指定骑手当前不可用");
            }
        } else {
            // 根据算法自动选择
            selectedRider = selectRider(request.getAlgorithm(), request.getOrderId());
        }

        // 更新骑手状态
        selectedRider.setStatus("BUSY");
        selectedRider.setTodayDeliveries(selectedRider.getTodayDeliveries() + 1);
        riderRepository.save(selectedRider);

        // 计算预计送达时间 (默认30-45分钟)
        LocalDateTime estimatedDeliveryTime = LocalDateTime.now().plusMinutes(30 + (int)(Math.random() * 15));

        // 创建派单记录
        DispatchRecord record = DispatchRecord.builder()
                .orderId(request.getOrderId())
                .orderNo("FF" + request.getOrderId() + System.currentTimeMillis())
                .riderId(selectedRider.getId())
                .riderName(selectedRider.getName())
                .status("ASSIGNED")
                .algorithm(request.getAlgorithm())
                .estimatedDeliveryTime(estimatedDeliveryTime)
                .build();

        record = dispatchRecordRepository.save(record);

        log.info("派单成功: orderId={}, riderId={}, riderName={}",
                request.getOrderId(), selectedRider.getId(), selectedRider.getName());

        return toDispatchResponse(record);
    }

    /**
     * 获取待派单订单列表
     */
    public List<DispatchResponse> getPendingDispatchOrders() {
        log.info("获取待派单订单列表");

        return dispatchRecordRepository.findByStatusOrderByCreatedAtAsc("ASSIGNED")
                .stream()
                .map(this::toDispatchResponse)
                .collect(Collectors.toList());
    }

    /**
     * 选择骑手
     */
    private Rider selectRider(String algorithm, Long orderId) {
        // 获取商家位置
        var merchantLoc = MERCHANT_LOCATIONS.getOrDefault(orderId, java.util.Map.of("lat", 39.9, "lng", 116.4));
        Double merchantLat = merchantLoc.get("lat");
        Double merchantLng = merchantLoc.get("lng");

        return switch (algorithm != null ? algorithm : "NEAREST") {
            case "LOAD_BALANCE" -> selectByLoadBalance();
            case "NEAREST" -> selectByNearest(merchantLat, merchantLng);
            default -> selectByNearest(merchantLat, merchantLng);
        };
    }

    /**
     * 选择距离最近的骑手
     */
    private Rider selectByNearest(Double merchantLat, Double merchantLng) {
        List<Rider> nearbyRiders = riderRepository.findNearbyAvailableRiders(merchantLat, merchantLng);

        if (nearbyRiders.isEmpty()) {
            // 如果附近没有空闲骑手，按负载均衡选择
            return selectByLoadBalance();
        }

        return nearbyRiders.get(0);
    }

    /**
     * 选择负载最低的骑手
     */
    private Rider selectByLoadBalance() {
        List<Rider> riders = riderRepository.findAvailableRidersByWorkload();

        if (riders.isEmpty()) {
            throw new RuntimeException("当前没有可用的骑手");
        }

        // 返回负载最低的骑手
        return riders.get(0);
    }

    /**
     * 将DispatchRecord转换为响应DTO
     */
    private DispatchResponse toDispatchResponse(DispatchRecord record) {
        return DispatchResponse.builder()
                .dispatchId(record.getId())
                .orderId(record.getOrderId())
                .orderNo(record.getOrderNo())
                .riderId(record.getRiderId())
                .riderName(record.getRiderName())
                .status(record.getStatus())
                .statusText(getStatusText(record.getStatus()))
                .algorithm(record.getAlgorithm())
                .estimatedDeliveryTime(record.getEstimatedDeliveryTime())
                .createdAt(record.getCreatedAt())
                .build();
    }

    /**
     * 获取状态描述文本
     */
    private String getStatusText(String status) {
        return switch (status) {
            case "ASSIGNED" -> "已派单";
            case "ACCEPTED" -> "已接单";
            case "DELIVERING" -> "配送中";
            case "COMPLETED" -> "已完成";
            case "FAILED" -> "失败";
            default -> status;
        };
    }
}
