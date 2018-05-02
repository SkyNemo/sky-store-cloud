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

    @Override
    public OrderVo createOrder(OrderParam orderParam, List<OrderItemVo> orderItemVoList) {


        Order order = new Order();

        BeanUtils.copyProperties(orderParam, order);

        System.out.println(order);

        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(0, 10000);

        order.setOrderCode(orderCode);
        order.setCreateDate(LocalDateTime.now());
        order.setStatus(OrderService.WAIT_PAY);

        Order saveOrder = orderRepository.save(order);

        float total = 0;

        for (OrderItemVo orderItemVo : orderItemVoList) {

            OrderItem orderItem = orderItemRepository.findOne(orderItemVo.getId());


            //判断前面的订单是否已有该订单项
            if (orderItem.getOrderId() != null) {


                OrderItem newOrderItem = new OrderItem();

                BeanUtils.copyProperties(orderItem, newOrderItem);

                newOrderItem.setOrderId(saveOrder.getId());

                orderItemRepository.save(newOrderItem);

            } else {


                orderItem.setOrderId(saveOrder.getId());

                orderItemRepository.save(orderItem);

            }

            //total += orderItemVo.getProduct().getPromotePrice() * orderItemVo.getNumber();

            total += orderItemVo.getTotal();
        }


        OrderVo orderVo = new OrderVo();

        BeanUtils.copyProperties(order, orderVo);

        orderVo.setTotal(total);

        return orderVo;
    }

    @Override
    public OrderVo confirmPay(OrderParam orderParam) {

        Order order = orderRepository.findOne(orderParam.getId());

        order.setPayDate(LocalDateTime.now());

        order.setStatus(OrderService.WAIT_DELIVERY);

        orderRepository.save(order);

        OrderVo orderVo = new OrderVo();

        BeanUtils.copyProperties(order, orderVo);

        orderVo.setTotal(orderParam.getTotal());

        return orderVo;
    }


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
                }

                orderVo.setTotal(orderTotal);
                orderVo.setOrderItemVos(orderItemVos);

                orderVoList.add(orderVo);

            }

        }

        return orderVoList;
    }

    @Override
    public void deleteOrder(Integer id) {

        Order order = orderRepository.getOne(id);

        order.setStatus(OrderService.DELETE);

        orderRepository.save(order);

    }

    @Override
    public OrderDto getOrderDtoByOrderId(Integer orderId) {

        Order order = orderRepository.findOne(orderId);

        OrderDto orderDto = null;

        System.out.println("206----------------");

        if (order != null) {

            System.out.println("210----------------");

            orderDto = new OrderDto();

            List<OrderItemVo> orderItemVoList = null;

            BeanUtils.copyProperties(order, orderDto);

            List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderId);

            if (orderItemList != null && !orderItemList.isEmpty()) {

                System.out.println("222----------------");

                orderItemVoList = new ArrayList<>();

                for (OrderItem orderItem : orderItemList) {

                    System.out.println("228----------------");

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

    @Override
    public boolean finishOrder(Integer id) {

        Order order = orderRepository.findOne(id);

        if (order != null){

            order.setStatus(OrderService.FINISH);

            orderRepository.save(order);

            return true;

        }


        return false;
    }


}
