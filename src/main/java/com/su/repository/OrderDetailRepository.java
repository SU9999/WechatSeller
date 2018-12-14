package com.su.repository;

import com.su.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    /** 根据订单的id，查询出所有的详情信息 */
    public List<OrderDetail> findByOrderId(String orderId);
}
