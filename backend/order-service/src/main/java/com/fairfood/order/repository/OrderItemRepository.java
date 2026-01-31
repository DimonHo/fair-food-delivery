package com.fairfood.order.repository;

import com.fairfood.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单项数据访问接口
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    /**
     * 根据订单ID查询所有订单项
     */
    List<OrderItem> findByOrderId(Long orderId);

    /**
     * 根据订单ID删除所有订单项
     */
    void deleteByOrderId(Long orderId);
}
