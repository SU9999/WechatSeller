package com.su.converter;

import com.su.form.ProductForm;
import com.su.model.ProductInfo;
import com.su.utils.KeyUtil;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 转化器
 */
public class ProductForm2ProductInfoConverter {

    public static ProductInfo convert(ProductForm productForm){
        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(productForm, productInfo);

        if (productInfo.getProductId().trim().equalsIgnoreCase("")){
            productInfo.setProductId(KeyUtil.getUniqueKey());    // 生成唯一主键值
        }

        return productInfo;
    }

    public static List<ProductInfo> convert(List<ProductForm> productFormList){
        List<ProductInfo> productInfoList = new ArrayList<>();

        for (ProductForm productForm : productFormList){
            ProductInfo productInfo = new ProductInfo();
            BeanUtils.copyProperties(productForm, productInfo);
            if (productInfo.getProductId().trim().equalsIgnoreCase("")){
                productInfo.setProductId(KeyUtil.getUniqueKey());    // 生成唯一主键值
            }
            productInfoList.add(productInfo);
        }

        return productInfoList;
    }
}
