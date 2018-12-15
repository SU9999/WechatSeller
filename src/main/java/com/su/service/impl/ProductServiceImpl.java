package com.su.service.impl;

import com.su.controller.SellerProductController;
import com.su.dto.CartDTO;
import com.su.enums.ProductStatusEnum;
import com.su.enums.ResultStatusEnum;
import com.su.exception.SellException;
import com.su.model.ProductInfo;
import com.su.repository.ProductInfoRepository;
import com.su.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sun.tools.asm.CatchData;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoRepository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    /** 加库存 */
    @Override
    @Transactional
    public void addStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList){
            if (cartDTO.getProductQuantity() <= 0){
                throw new SellException(ResultStatusEnum.PRODUCT_QUANTITY_ERROR);
            }

            ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());
            productInfo.setProductStock(productInfo.getProductStock()+ cartDTO.getProductQuantity());

            productInfoRepository.save(productInfo);
        }
    }

    /** 减库存操作：添加事务处理 */
    @Override
    @Transactional
    public void subStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            if (cartDTO.getProductQuantity() <= 0){
                throw new SellException(ResultStatusEnum.PRODUCT_QUANTITY_ERROR);
            }

            ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());
            if (productInfo == null){
                throw new SellException(ResultStatusEnum.PRODUCT_NOT_EXIST);
            }
            int result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0){
                throw new SellException(ResultStatusEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = productInfoRepository.findOne(productId);
        if (productInfo == null){
            throw new SellException(ResultStatusEnum.PRODUCT_NOT_EXIST);
        }

        if (productInfo.getProductStatus() == ProductStatusEnum.DOWN.getCode()){
            throw new SellException(ResultStatusEnum.PRODUCT_STATUS_ERROR);
        }

        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return productInfoRepository.save(productInfo);
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productInfoRepository.findOne(productId);
        if (productInfo == null){
            throw new SellException(ResultStatusEnum.PRODUCT_NOT_EXIST);
        }

        if (productInfo.getProductStatus() == ProductStatusEnum.UP.getCode()){
            throw new SellException(ResultStatusEnum.PRODUCT_STATUS_ERROR);
        }

        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return productInfoRepository.save(productInfo);
    }
}
