package com.fairfood.order.repository;

import com.fairfood.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 订单数据访问接口
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * 根据订单号查找
     */
    Optional<Order> findByOrderNo(String orderNo);

    /**
     * 根据用户ID查询订单列表
     */
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);

    /**
     * 根据用户ID和状态查询订单
     */
    List<Order> findByUserIdAndStatusOrderByCreatedAtDesc(Long userId, String status);

    /**
     * 根据商家ID查询订单
     */
    List<Order> findByMerchantIdOrderByCreatedAtDesc(Long merchantId);

    /**
     * 查询所有待派单订单 (状态为CONFIRMED)
     */
    List<Order> findByStatusOrderByCreatedAtAsc(String status);

    /**
     * 检查订单号是否存在
     */
    boolean existsByOrderNo(String orderNo);
}
