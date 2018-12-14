package com.su.repository;

import com.su.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    /** 根据类目的类型categoryType查询数据 */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypes);
}
