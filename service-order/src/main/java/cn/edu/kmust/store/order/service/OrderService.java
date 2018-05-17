package cn.edu.kmust.store.order.service;

import cn.edu.kmust.store.order.entity.Order;
import cn.edu.kmust.store.order.param.OrderDto;
import cn.edu.kmust.store.order.param.OrderItemVo;
import cn.edu.kmust.store.order.param.OrderParam;
import cn.edu.kmust.store.order.param.OrderVo;

import java.util.List;

public interface OrderService {

    String WAIT_PAY = "waitPay";
    String WAIT_DELIVERY = "waitDelivery";
    String WAIT_CONFIRM = "waitConfirm";
    String WAIT_REVIEW = "waitReview";
    String FINISH = "finish";
    String DELETE = "delete";


    OrderVo createOrder(OrderParam orderParam , List<OrderItemVo> orderItemVoList);

    OrderVo confirmPay(OrderParam orderParam);

    List<OrderVo> getByUserId(Integer userId);

    void deleteOrder(Integer id);

    OrderDto getOrderDtoByOrderId(Integer orderId);

    boolean finishOrder(Integer id);

    boolean confirmReceipt(Integer orderId);

    OrderVo findByOrderId(Integer orderId);
}
