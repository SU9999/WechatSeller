package com.su.service;

import com.su.model.SellerInfo;

/**
 *  卖家端service层
 */
public interface SellerService {
    public SellerInfo findSellerInfoByEmail(String email);

    public SellerInfo register(SellerInfo sellerInfo);
}
