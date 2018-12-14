package com.su.repository;

import com.su.model.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    public List<ProductInfo> findByProductStatus(Integer status);
}
