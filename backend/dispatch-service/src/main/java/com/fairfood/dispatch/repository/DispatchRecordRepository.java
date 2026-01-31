package com.fairfood.dispatch.repository;

import com.fairfood.dispatch.model.DispatchRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 派单记录数据访问接口
 */
@Repository
public interface DispatchRecordRepository extends JpaRepository<DispatchRecord, Long> {

    /**
     * 根据订单ID查询派单记录
     */
    Optional<DispatchRecord> findByOrderId(Long orderId);

    /**
     * 根据骑手ID查询派单记录
     */
    List<DispatchRecord> findByRiderIdOrderByCreatedAtDesc(Long riderId);

    /**
     * 查询所有待派单订单的派单记录
     */
    List<DispatchRecord> findByStatusOrderByCreatedAtAsc(String status);
}
