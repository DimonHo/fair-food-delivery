package com.fairfood.dispatch.repository;

import com.fairfood.dispatch.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 骑手数据访问接口
 */
@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {

    /**
     * 查询所有空闲骑手
     */
    List<Rider> findByStatus(String status);

    /**
     * 查询附近空闲骑手 (基于位置)
     */
    @Query(value = """
            SELECT r.*, (
                6371 * acos(
                    cos(radians(:latitude)) * cos(radians(r.latitude)) *
                    cos(radians(r.longitude) - radians(:longitude)) +
                    sin(radians(:latitude)) * sin(radians(r.latitude))
                )
            ) AS distance
            FROM riders r
            WHERE r.status = 'AVAILABLE'
              AND r.latitude IS NOT NULL
              AND r.longitude IS NOT NULL
            ORDER BY distance ASC
            """, nativeQuery = true)
    List<Rider> findNearbyAvailableRiders(@Param("latitude") Double latitude,
                                           @Param("longitude") Double longitude);

    /**
     * 查询工作负载最低的骑手
     */
    @Query("SELECT r FROM Rider r WHERE r.status = 'AVAILABLE' ORDER BY r.todayDeliveries ASC")
    List<Rider> findAvailableRidersByWorkload();
}
