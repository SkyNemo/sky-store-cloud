package cn.edu.kmust.store.order.service;

import cn.edu.kmust.store.order.param.OrderItemVo;
import cn.edu.kmust.store.order.param.OrderVo;

import java.util.List;

public interface OrderItemService {


    OrderItemVo getOrderItemVoByUserIdAndProductIdAndNumber(Integer userId,Integer productId,Integer number);

    List<OrderItemVo> getOrderItemVoListByOrderItemIds(String[] orderItemIds);

    List<OrderItemVo> getOrderItemVoListByUserIdAndOrderId(Integer userId, Integer orderId);

    void deleteOrderItemById(Integer orderItemId);

    boolean changeOrderItem(Integer userId, Integer productId,Integer number);

    Integer countProductSale(Integer productId);

}
