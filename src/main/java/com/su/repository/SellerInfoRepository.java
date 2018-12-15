package com.su.repository;

import com.su.model.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

    public SellerInfo findByEmail(String email);
}
