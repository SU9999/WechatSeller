package com.su.service.impl;

import com.su.converter.OrderMaster2OrderDTOConverter;
import com.su.dto.CartDTO;
import com.su.dto.OrderDTO;
import com.su.enums.OrderStatusEnum;
import com.su.enums.PayStatusEnum;
import com.su.enums.ResultStatusEnum;
import com.su.exception.SellException;
import com.su.model.OrderDetail;
import com.su.model.OrderMaster;
import com.su.model.ProductInfo;
import com.su.repository.OrderDetailRepository;
import com.su.repository.OrderMasterRepository;
import com.su.service.OrderService;
import com.su.service.PayService;
import com.su.service.ProductService;
import com.su.utils.KeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository detailRepository;

    @Autowired
    private OrderMasterRepository masterRepository;

    @Autowired
    private PayService payService;

    /** 添加事务管理 */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        // 订单id
        String orderId = KeyUtil.getUniqueKey();
        // 1.查询商品信息（数量，价格等）
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            // 查询出该订单详情对象的商品的详情信息
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            // 查询出该商品不存在时，抛出商品不存在的异常信息
            if (productInfo == null){
                throw new SellException(ResultStatusEnum.PRODUCT_NOT_EXIST);
            }
            // 计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            // 组合订单详情的内容
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);

            //将orderDetail写入数据库
            detailRepository.save(orderDetail);
        }
        // 将订单主表的信息生成，并写入数据库
        OrderMaster orderMaster = new OrderMaster();
        // 注意：BeanUtils也会把null值拷贝到目标属性中，因此，如果使用BeanUtils做拷贝，应该放在设置其他属性之前
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
//        System.out.println(orderMaster.getOrderId());
        OrderMaster createOrderMaster = masterRepository.save(orderMaster);

        // 4.扣库存，同时判断库存是否合理，如超过则抛出异常
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().
                map(item -> new CartDTO(item.getProductId(), item.getProductQuantity())).
                collect(Collectors.toList());
        productService.subStock(cartDTOList);

        return OrderMaster2OrderDTOConverter.convert(createOrderMaster);
    }

    @Override
    public OrderDTO findOne(String orderId) {
        // 查询出订单主表
        OrderMaster orderMaster = masterRepository.findOne(orderId);
        if (orderMaster == null){
            throw new SellException(ResultStatusEnum.ORDER_NOT_EXIST);
        }
        // 查询单订下的订单详细信息
        List<OrderDetail> orderDetailList = detailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultStatusEnum.DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> orderMasterPage = masterRepository.findByBuyerOpenid(buyerOpenid, pageable);

        //将orderMasterPage中的内容转换成OrderDTO集合，用于向前端输送数据
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        // 返回一个OrderDTO的page对象
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        // 查询并判断订单状态, 同时修改状态为CANCEL
        OrderMaster updateOrderMaster = updateOrderStatus(orderDTO, OrderStatusEnum.CANCEL);

        // 返回库存, 首先对订单中的详情信息进行判断
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【订单中无订单详情信息】，OrderDTO={}", orderDTO);
            throw new SellException(ResultStatusEnum.ORDER_DETAIL_EMPTY);
        }
        // 将orderDTO中的OrderDetail集合转换成CartDTO集合，用于添加库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().
                map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).
                collect(Collectors.toList());
        productService.addStock(cartDTOList);

        // 如果买家已付款，则需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO 此处需要调用PayService的退款方法，但该方法还未通关微信验证,因此先注释掉
            // payService.reFund(orderDTO);
        }

        // 返回修改状态后的orderDTO对象
        return OrderMaster2OrderDTOConverter.convert(updateOrderMaster);
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        // 查询并判断订单状态, 同时修改状态为FINISHED
        OrderMaster updateOrderMaster = updateOrderStatus(orderDTO, OrderStatusEnum.FINISHED);
        return OrderMaster2OrderDTOConverter.convert(updateOrderMaster);
    }

    /**
     *  按照指定的状态码信息，将OrderDTO中的状态信息更改为指定状态码
     *  失败：抛出异常
     *  成功：返回更新后的OrderMaster对象
      * @param orderDTO
     * @param statusEnum
     * @return
     */
    private OrderMaster updateOrderStatus(OrderDTO orderDTO, OrderStatusEnum statusEnum){
        // 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【订单状态不正确：orderId={}, 状态码={}】",orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultStatusEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderStatus(statusEnum.getCode());
        OrderMaster updateOrderMaster = masterRepository.save(orderMaster);
        if (updateOrderMaster == null){
            log.error("【修改订单失败】：orderId={}, orderStatusCode={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultStatusEnum.UPDATE_ORDER_ERROR);
        }

        return updateOrderMaster;
    }
    @Override
    @Transactional
    public OrderDTO pay(OrderDTO orderDTO) {
        // 判断支付状态和订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【订单状态不正确】：orderId={}, 状态码={}",orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultStatusEnum.ORDER_STATUS_ERROR);
        }
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【订单支付状态不正确】payStatus={}, orderDTO={},", orderDTO.getPayStatus(), orderDTO);
            throw new SellException(ResultStatusEnum.PAY_STATUS_ERROR);
        }

        // 修改订单支付状态
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster updateOrderMaster = masterRepository.save(orderMaster);
        if (updateOrderMaster == null){
            log.error("【修改支付状态失败】:payStatus={}, orderDTO={}", orderDTO.getPayStatus(), orderDTO);
        }
        return OrderMaster2OrderDTOConverter.convert(updateOrderMaster);
    }
}
