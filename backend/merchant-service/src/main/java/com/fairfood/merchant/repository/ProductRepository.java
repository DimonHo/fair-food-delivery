package com.fairfood.merchant.repository;

import com.fairfood.merchant.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品数据访问接口
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * 根据商家ID查询所有在售商品
     */
    List<Product> findByMerchantIdAndStatus(Long merchantId, String status);

    /**
     * 根据商家ID查询所有商品
     */
    List<Product> findByMerchantId(Long merchantId);

    /**
     * 根据商家ID和分类查询商品
     */
    List<Product> findByMerchantIdAndCategory(Long merchantId, String category);

    /**
     * 根据商家ID和状态查询商品
     */
    List<Product> findByMerchantIdAndStatusOrderByMonthlySalesDesc(Long merchantId, String status);
}
