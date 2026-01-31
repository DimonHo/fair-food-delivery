package com.fairfood.address.repository;

import com.fairfood.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 地址数据访问接口
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    /**
     * 根据用户ID查询所有地址
     */
    List<Address> findByUserIdOrderByIsDefaultDescCreatedAtDesc(Long userId);

    /**
     * 根据用户ID和ID查询
     */
    Optional<Address> findByIdAndUserId(Long id, Long userId);

    /**
     * 根据用户ID查询默认地址
     */
    Optional<Address> findByUserIdAndIsDefaultTrue(Long userId);

    /**
     * 删除地址
     */
    void deleteByIdAndUserId(Long id, Long userId);
}
