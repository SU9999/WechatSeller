package com.su.service;

import com.su.model.ProductCategory;

import java.util.List;

/**
 *  类目的Service层接口
 */
public interface CategoryService {

    ProductCategory findOne(int id);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypes);

    /**
     * 执行新增类目和更新类目的操作
     * @param productCategory
     * @return
     */
    ProductCategory save(ProductCategory productCategory);
}
