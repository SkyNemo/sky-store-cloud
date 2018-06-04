package cn.edu.kmust.store.order.service.impl;

import cn.edu.kmust.store.order.client.ProductFeignClient;
import cn.edu.kmust.store.order.entity.Order;
import cn.edu.kmust.store.order.entity.OrderItem;
import cn.edu.kmust.store.order.entity.Product;
import cn.edu.kmust.store.order.param.OrderDto;
import cn.edu.kmust.store.order.param.OrderItemVo;
import cn.edu.kmust.store.order.param.OrderParam;
import cn.edu.kmust.store.order.param.OrderVo;
import cn.edu.kmust.store.order.repository.OrderItemRepository;
import cn.edu.kmust.store.order.repository.OrderRepository;
import cn.edu.kmust.store.order.service.OrderItemService;
import cn.edu.kmust.store.order.service.OrderService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private OrderItemRepository orderItemRepository;


    @Resource
    private ProductFeignClient productFeignClient;


    /**
     * 创建订单
     */
    @Override
    public OrderVo createOrder(OrderParam orderParam, List<OrderItemVo> orderItemVoList) {


        Order order = new Order();

        BeanUtils.copyProperties(orderParam, order);

        // 设置订单号
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(0, 10000);
        order.setOrderCode(orderCode);
        // 设置订单创建日期
        order.setCreateDate(LocalDateTime.now());
        // 设置订单状态
        order.setStatus(OrderService.WAIT_PAY);

        // 保存订单
        Order saveOrder = orderRepository.save(order);

        float total = 0;

        for (OrderItemVo orderItemVo : orderItemVoList) {

            OrderItem orderItem = orderItemRepository.findOne(orderItemVo.getId());

            Integer productId = orderItem.getProductId();
            Integer productNumber = orderItem.getProductId();


            // 校验库存
            Boolean checkResult = productFeignClient.checkProductStock(productId, productNumber);

            if (!checkResult){
                continue;
            }

            // 减库存
            Boolean updateResult = productFeignClient.updateProductStock(productId, productNumber);

            if (!updateResult){
                continue;
            }



            // 判断前面的订单是否已有该订单项
            // 没有订单号的订单项即为购物车中的物品
            if (orderItem.getOrderId() != null) {

                // 创建新的订单项
                OrderItem newOrderItem = new OrderItem();

                BeanUtils.copyProperties(orderItem, newOrderItem);

                newOrderItem.setOrderId(saveOrder.getId());

                orderItemRepository.save(newOrderItem);

            } else {

                // 使用之前的订单项
                orderItem.setOrderId(saveOrder.getId());

                orderItemRepository.save(orderItem);

            }

            //total += orderItemVo.getProduct().getPromotePrice() * orderItemVo.getNumber();

            // 计算订单项价格
            total += orderItemVo.getTotal();
        }


        OrderVo orderVo = new OrderVo();

        BeanUtils.copyProperties(order, orderVo);

        // 设置订单价格
        orderVo.setTotal(total);

        return orderVo;
    }

    /**
     * 确认付款
     */
    @Override
    public OrderVo confirmPay(OrderParam orderParam) {

        Order order = orderRepository.findOne(orderParam.getId());

        // 设置付款日期
        order.setPayDate(LocalDateTime.now());

        // 设置订单状态
        order.setStatus(OrderService.WAIT_DELIVERY);

        orderRepository.save(order);

        OrderVo orderVo = new OrderVo();

        BeanUtils.copyProperties(order, orderVo);

        orderVo.setTotal(orderParam.getTotal());

        return orderVo;
    }

    /**
     * 根据用户id查询该用户的订单
     */
    @Override
    public List<OrderVo> getByUserId(Integer userId) {

        List<OrderVo> orderVoList = new ArrayList<>();

        List<Order> orderList = orderRepository.getByUserIdAndStatusIsNot(userId, OrderService.DELETE);

        if (orderList != null && orderList.size() > 0) {

            // 填充每个order的orderItem以及计算total
            for (Order order : orderList) {

                OrderVo orderVo = new OrderVo();
                BeanUtils.copyProperties(order, orderVo);

                List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderVo.getId());

                List<OrderItemVo> orderItemVos = new ArrayList<>();

                float orderTotal = 0;

                if (orderItemList != null && orderItemList.size() > 0) {

                    for (OrderItem orderItem : orderItemList) {

                        OrderItemVo orderItemVo = new OrderItemVo();
                        BeanUtils.copyProperties(orderItem, orderItemVo);

                        //计算每个orderItem的total
                        float orderItemTotal = 0;
                        Product product = productFeignClient.findProductById(orderItemVo.getProductId());
                        if (product != null) {

                            orderItemVo.setProduct(product);

                            orderItemTotal += product.getPromotePrice() * orderItemVo.getNumber();

                            orderItemVo.setTotal(orderItemTotal);

                            orderTotal += orderItemTotal;

                        }

                        orderItemVos.add(orderItemVo);

                    }
                }else {
                    // 订单下没有订单项
                    // 删除该订单
                    orderRepository.delete(order);
                    continue;
                }

                orderVo.setTotal(orderTotal);
                orderVo.setOrderItemVos(orderItemVos);

                orderVoList.add(orderVo);

            }

        }

        return orderVoList;
    }


    /**
     * 删除订单
     */
    @Override
    public void deleteOrder(Integer id) {

        Order order = orderRepository.getOne(id);

        order.setStatus(OrderService.DELETE);

        orderRepository.save(order);

    }

    /**
     * 根据订单id返回Dto
     */
    @Override
    public OrderDto getOrderDtoByOrderId(Integer orderId) {

        Order order = orderRepository.findOne(orderId);

        OrderDto orderDto = null;

        // 订单不为空 封装Dto
        if (order != null) {

            orderDto = new OrderDto();

            List<OrderItemVo> orderItemVoList = null;

            BeanUtils.copyProperties(order, orderDto);

            // 封装每个订单项
            List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderId);

            if (orderItemList != null && !orderItemList.isEmpty()) {

                orderItemVoList = new ArrayList<>();

                for (OrderItem orderItem : orderItemList) {

                    OrderItemVo orderItemVo = new OrderItemVo();

                    BeanUtils.copyProperties(orderItem, orderItemVo);

                    Product product = productFeignClient.findProductById(orderItem.getProductId());

                    orderItemVo.setProduct(product);

                    orderItemVoList.add(orderItemVo);
                }

            }

            orderDto.setOrderItems(orderItemVoList);

        }


        return orderDto;
    }

    /**
     * 结束订单
     */
    @Override
    public boolean finishOrder(Integer id) {

        Order order = orderRepository.findOne(id);

        if (order != null) {

            order.setStatus(OrderService.FINISH);

            orderRepository.save(order);

            return true;

        }
        return false;
    }

    /**
     * 确认收货
     */
    @Override
    public boolean confirmReceipt(Integer orderId) {

        Order order = orderRepository.findOne(orderId);

        if (order != null) {
            order.setStatus(OrderService.WAIT_REVIEW);

            orderRepository.save(order);

            return true;
        }

        return false;
    }

    /**
     * 根据订单id返回订单
     */
    @Override
    public OrderVo findByOrderId(Integer orderId) {


        OrderVo orderVo = new OrderVo();

        Order order = orderRepository.findOne(orderId);

        float total = 0;

        if (order != null) {

            BeanUtils.copyProperties(order, orderVo);

            List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderId);

            List<OrderItemVo> orderItemVoList = new ArrayList<>();

            if (orderItemList != null && !orderItemList.isEmpty()) {

                for (OrderItem orderItem : orderItemList) {

                    OrderItemVo orderItemVo = new OrderItemVo();

                    BeanUtils.copyProperties(orderItem, orderItemVo);


                    Integer number = orderItem.getNumber();

                    Product product = productFeignClient.findProductById(orderItem.getProductId());

                    orderItemVo.setProduct(product);


                    orderItemVo.setTotal(product.getPromotePrice() * number);

                    total += product.getPromotePrice() * number;

                    orderItemVoList.add(orderItemVo);

                }


            }

            orderVo.setOrderItemVos(orderItemVoList);


        }

        orderVo.setTotal(total);

        return orderVo;
    }


}
