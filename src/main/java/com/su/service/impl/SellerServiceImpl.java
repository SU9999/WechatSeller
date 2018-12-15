package com.su.service.impl;

import com.su.model.SellerInfo;
import com.su.repository.SellerInfoRepository;
import com.su.service.SellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {
    private Logger log = LoggerFactory.getLogger(SellerServiceImpl.class);

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findSellerInfoByEmail(String email) {
        return sellerInfoRepository.findByEmail(email);
    }

    @Override
    public SellerInfo register(SellerInfo sellerInfo) {
        return sellerInfoRepository.save(sellerInfo);
    }
}
