package com.su.service;

import com.su.dto.CartDTO;
import com.su.model.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品服务层
 */
public interface ProductService {

    public ProductInfo findOne(String productId);

    /** 查询所有在架的商品: 即商品状态status为0 */
    public List<ProductInfo> findUpAll();

    /**
     * 查询所有的商品：主要针对卖家
     * 同时实现分页功能: 使用Pageable接口, 此时返回一个page对象而不是list对象
     * */
    public Page<ProductInfo> findAll(Pageable pageable);

    public ProductInfo save(ProductInfo productInfo);

    /** 加库存 */
    public void addStock(List<CartDTO> cartDTOList);

    /** 减库存 */
    public void subStock(List<CartDTO> cartDTOList);
}
