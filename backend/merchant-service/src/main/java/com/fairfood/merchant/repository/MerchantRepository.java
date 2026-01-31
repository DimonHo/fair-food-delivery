package com.fairfood.merchant.repository;

import com.fairfood.merchant.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商家数据访问接口
 */
@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    /**
     * 根据商家名称模糊查询
     */
    List<Merchant> findByNameContainingIgnoreCase(String name);

    /**
     * 根据状态查询商家
     */
    List<Merchant> findByStatus(String status);

    /**
     * 查询附近商家 (使用简单的距离计算)
     * 计算每个商家与给定坐标的距离，返回指定半径内的商家
     */
    @Query(value = """
            SELECT m.*, (
                6371 * acos(
                    cos(radians(:latitude)) * cos(radians(m.latitude)) *
                    cos(radians(m.longitude) - radians(:longitude)) +
                    sin(radians(:latitude)) * sin(radians(m.latitude))
                )
            ) AS distance
            FROM merchants m
            WHERE m.latitude IS NOT NULL
              AND m.longitude IS NOT NULL
              AND m.status = 'OPEN'
            HAVING distance <= :radius
            ORDER BY distance ASC
            """, nativeQuery = true)
    List<Merchant> findNearbyMerchants(
            @Param("latitude") BigDecimal latitude,
            @Param("longitude") BigDecimal longitude,
            @Param("radius") Double radius);
}
