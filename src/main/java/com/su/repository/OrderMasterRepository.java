package com.su.repository;

import com.su.model.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
    /** 根据买家的用户id查询出所有的该买家订单：使用分页的方式 */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
