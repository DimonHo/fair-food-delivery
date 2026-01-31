package com.fairfood.order.service;

import com.fairfood.order.dto.*;
import com.fairfood.order.model.Order;
import com.fairfood.order.model.OrderItem;
import com.fairfood.order.repository.OrderItemRepository;
import com.fairfood.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 订单服务实现类
 * 处理订单创建、查询、状态更新等业务逻辑
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    // 模拟的商家数据 (实际应调用商家服务)
    private static final java.util.Map<Long, String> MERCHANT_NAMES = java.util.Map.of(
            1L, "麦当劳",
            2L, "星巴克",
            3L, "海底捞火锅"
    );

    // 模拟的商品数据 (实际应调用商家服务)
    private static final java.util.Map<Long, java.util.Map<String, Object>> PRODUCT_DATA = java.util.Map.of(
            1L, java.util.Map.of("name", "巨无霸套餐", "price", new BigDecimal("35.00")),
            2L, java.util.Map.of("name", "麦辣鸡腿堡", "price", new BigDecimal("28.00")),
            3L, java.util.Map.of("name", "麦乐鸡(5块)", "price", new BigDecimal("15.00")),
            4L, java.util.Map.of("name", "星巴克拿铁", "price", new BigDecimal("31.00")),
            5L, java.util.Map.of("name", "星冰乐", "price", new BigDecimal("35.00")),
            6L, java.util.Map.of("name", "美式咖啡", "price", new BigDecimal("24.00"))
    );

    /**
     * 创建订单
     */
    @Transactional
    public OrderResponse createOrder(Long userId, CreateOrderRequest request) {
        log.info("创建订单: userId={}, merchantId={}", userId, request.getMerchantId());

        // 生成订单号
        String orderNo = generateOrderNo();

        // 计算订单金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        java.util.List<OrderItem> orderItems = new java.util.ArrayList<>();

        for (CreateOrderRequest.OrderItemRequest itemReq : request.getItems()) {
            // 获取商品信息
            var productInfo = PRODUCT_DATA.get(itemReq.getProductId());
            if (productInfo == null) {
                throw new RuntimeException("商品不存在: " + itemReq.getProductId());
            }

            BigDecimal price = (BigDecimal) productInfo.get("price");
            BigDecimal subtotal = price.multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            totalAmount = totalAmount.add(subtotal);

            OrderItem orderItem = OrderItem.builder()
                    .orderId(null) // 先不设置，保存订单后设置
                    .productId(itemReq.getProductId())
                    .productName((String) productInfo.get("name"))
                    .price(price)
                    .quantity(itemReq.getQuantity())
                    .subtotal(subtotal)
                    .build();
            orderItems.add(orderItem);
        }

        // 配送费 (满100免配送费)
        BigDecimal deliveryFee = totalAmount.compareTo(new BigDecimal("100")) >= 0
                ? BigDecimal.ZERO : new BigDecimal("8.00");

        // 创建订单
        Order order = Order.builder()
                .orderNo(orderNo)
                .userId(userId)
                .merchantId(request.getMerchantId())
                .addressId(request.getAddressId())
                .totalAmount(totalAmount)
                .deliveryFee(deliveryFee)
                .discountAmount(BigDecimal.ZERO)
                .payAmount(totalAmount.add(deliveryFee))
                .status("CONFIRMED") // 直接确认，后续可改为待付款
                .payMethod(request.getPayMethod() != null ? request.getPayMethod() : "ONLINE")
                .remark(request.getRemark())
                .estimatedDeliveryTime(LocalDateTime.now().plusMinutes(45))
                .build();

        order = orderRepository.save(order);

        // 保存订单项
        for (OrderItem item : orderItems) {
            item.setOrderId(order.getId());
        }
        orderItemRepository.saveAll(orderItems);

        log.info("订单创建成功: orderNo={}", orderNo);

        return toOrderResponse(order);
    }

    /**
     * 获取用户订单列表
     */
    public List<OrderResponse> getUserOrders(Long userId, String status) {
        log.info("获取用户订单列表: userId={}, status={}", userId, status);

        List<Order> orders;
        if (status != null && !status.isEmpty()) {
            orders = orderRepository.findByUserIdAndStatusOrderByCreatedAtDesc(userId, status);
        } else {
            orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
        }

        return orders.stream()
                .map(this::toOrderResponse)
                .collect(Collectors.toList());
    }

    /**
     * 获取订单详情
     */
    public OrderResponse getOrderDetail(Long orderId, Long userId) {
        log.info("获取订单详情: orderId={}, userId={}", orderId, userId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("订单不存在: " + orderId));

        // 验证订单归属
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("无权访问此订单");
        }

        return toOrderResponse(order);
    }

    /**
     * 更新订单状态
     */
    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, UpdateStatusRequest request) {
        log.info("更新订单状态: orderId={}, newStatus={}", orderId, request.getStatus());

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("订单不存在: " + orderId));

        // 验证状态流转是否合法
        validateStatusTransition(order.getStatus(), request.getStatus());

        order.setStatus(request.getStatus());

        // 如果是完成订单，设置预计送达时间
        if ("DELIVERING".equals(request.getStatus())) {
            order.setEstimatedDeliveryTime(LocalDateTime.now().plusMinutes(30));
        }

        order = orderRepository.save(order);

        return toOrderResponse(order);
    }

    /**
     * 获取待派单订单列表 (供派单服务使用)
     */
    public List<OrderResponse> getPendingDispatchOrders() {
        log.info("获取待派单订单列表");
        return orderRepository.findByStatusOrderByCreatedAtAsc("CONFIRMED")
                .stream()
                .map(this::toOrderResponse)
                .collect(Collectors.toList());
    }

    /**
     * 生成订单号
     */
    private String generateOrderNo() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "FF" + timestamp + random;
    }

    /**
     * 验证状态流转是否合法
     */
    private void validateStatusTransition(String currentStatus, String newStatus) {
        // 定义合法状态流转
        java.util.Map<String, java.util.List<String>> allowedTransitions = java.util.Map.of(
                "PENDING", java.util.List.of("CONFIRMED", "CANCELLED"),
                "CONFIRMED", java.util.List.of("PREPARING", "CANCELLED"),
                "PREPARING", java.util.List.of("DELIVERING"),
                "DELIVERING", java.util.List.of("COMPLETED"),
                "COMPLETED", java.util.List.of(), // 已完成不能变更
                "CANCELLED", java.util.List.of()  // 已取消不能变更
        );

        var allowed = allowedTransitions.getOrDefault(currentStatus, java.util.List.of());
        if (!allowed.contains(newStatus)) {
            throw new RuntimeException("不支持的状态流转: " + currentStatus + " -> " + newStatus);
        }
    }

    /**
     * 将Order实体转换为响应DTO
     */
    private OrderResponse toOrderResponse(Order order) {
        // 获取订单项
        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());

        // 获取商家名称
        String merchantName = MERCHANT_NAMES.getOrDefault(order.getMerchantId(), "未知商家");

        return OrderResponse.builder()
                .id(order.getId())
                .orderNo(order.getOrderNo())
                .userId(order.getUserId())
                .merchantId(order.getMerchantId())
                .merchantName(merchantName)
                .addressId(order.getAddressId())
                .totalAmount(order.getTotalAmount())
                .deliveryFee(order.getDeliveryFee())
                .discountAmount(order.getDiscountAmount())
                .payAmount(order.getPayAmount())
                .status(order.getStatus())
                .statusText(getStatusText(order.getStatus()))
                .payMethod(order.getPayMethod())
                .remark(order.getRemark())
                .estimatedDeliveryTime(order.getEstimatedDeliveryTime())
                .createdAt(order.getCreatedAt())
                .items(items.stream()
                        .map(this::toOrderItemResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * 将OrderItem实体转换为响应DTO
     */
    private OrderResponse.OrderItemResponse toOrderItemResponse(OrderItem item) {
        return OrderResponse.OrderItemResponse.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .productName(item.getProductName())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .subtotal(item.getSubtotal())
                .build();
    }

    /**
     * 获取状态描述文本
     */
    private String getStatusText(String status) {
        return switch (status) {
            case "PENDING" -> "待付款";
            case "CONFIRMED" -> "已确认";
            case "PREPARING" -> "准备中";
            case "DELIVERING" -> "配送中";
            case "COMPLETED" -> "已完成";
            case "CANCELLED" -> "已取消";
            default -> status;
        };
    }
}
