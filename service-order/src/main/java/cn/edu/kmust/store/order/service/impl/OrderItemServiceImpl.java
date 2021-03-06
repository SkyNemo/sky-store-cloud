package cn.edu.kmust.store.order.service.impl;

import cn.edu.kmust.store.order.client.ProductFeignClient;
import cn.edu.kmust.store.order.entity.OrderItem;
import cn.edu.kmust.store.order.entity.Product;
import cn.edu.kmust.store.order.param.OrderItemVo;
import cn.edu.kmust.store.order.param.OrderVo;
import cn.edu.kmust.store.order.repository.OrderItemRepository;
import cn.edu.kmust.store.order.service.OrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {


    private static final Logger LOGGER = LoggerFactory.getLogger(OrderItemServiceImpl.class);

    @Resource
    private OrderItemRepository orderItemRepository;


    @Resource
    private ProductFeignClient productFeignClient;


    /**
     * 创建订单项
     */
    @Override
    public OrderItemVo getOrderItemVoByUserIdAndProductIdAndNumber(Integer userId, Integer productId, Integer number) {


        Product product = productFeignClient.findProductById(productId);

        if (product == null) {
            return null;
        }

        // 查看该订单项是否存在
        OrderItem orderItem = orderItemRepository.findByUserIdAndProductIdAndOrderIdNull(userId, productId);

        if (orderItem != null) {
            // 订单项已存在，更新商品数量
            orderItem.setNumber(orderItem.getNumber() + number);
            orderItemRepository.save(orderItem);
        } else {
            // 订单项不存在，创建新的订单项
            orderItem = new OrderItem();
            orderItem.setUserId(userId);
            orderItem.setProductId(product.getId());
            orderItem.setNumber(number);
            orderItemRepository.save(orderItem);
        }

        // 构建订单项Vo，返回Vo
        OrderItemVo orderItemVo = new OrderItemVo();
        BeanUtils.copyProperties(orderItem, orderItemVo);

        return orderItemVo;
    }

    @Override
    public List<OrderItemVo> getOrderItemVoListByOrderItemIds(String[] orderItemIds) {

        List<OrderItemVo> orderItemVos = new ArrayList<OrderItemVo>();


        for (String orderItemId : orderItemIds) {

            float total = 0;

            OrderItem orderItem = orderItemRepository.findOne(Integer.parseInt(orderItemId));
            OrderItemVo orderItemVo = new OrderItemVo();
            if (orderItem != null) {
                Product product = productFeignClient.findProductById(orderItem.getProductId());
                if (product != null) {

                    total += total + orderItem.getNumber() * product.getPromotePrice();

                    BeanUtils.copyProperties(orderItem, orderItemVo);

                    orderItemVo.setProduct(product);

                    orderItemVos.add(orderItemVo);

                    orderItemVo.setTotal(total);
                }
            }
        }


        return orderItemVos;
    }

    @Override
    public List<OrderItemVo> getOrderItemVoListByUserIdAndOrderId(Integer userId, Integer orderId) {

        List<OrderItem> orderItemList = orderItemRepository.getByUserIdAndOrderId(userId, orderId);

        List<OrderItemVo> orderItemVoList = new ArrayList<>();

        if (orderItemList != null) {


            for (OrderItem orderItem : orderItemList) {

                float total = 0;

                OrderItemVo orderItemVo = new OrderItemVo();

                Product product = productFeignClient.findProductById(orderItem.getProductId());

                if (product != null) {

                    orderItemVo.setProduct(product);

                    Integer number = orderItem.getNumber();

                    float price = product.getPromotePrice();

                    total = number * price;

                    orderItemVo.setTotal(total);


                    BeanUtils.copyProperties(orderItem, orderItemVo);


                    orderItemVoList.add(orderItemVo);
                }

                LOGGER.info("创建订单项成功，订单项Id");

            }

        }

        return orderItemVoList;
    }

    /**
     * 删除订单项
     * */
    @Override
    public void deleteOrderItemById(Integer orderItemId) {

        OrderItem one = orderItemRepository.findOne(orderItemId);
        if (one != null) {
            orderItemRepository.delete(orderItemId);
        }

    }


    @Override
    public boolean changeOrderItem(Integer userId, Integer productId, Integer number) {

        Product product = productFeignClient.findProductById(productId);

        if (product == null) {
            return false;
        }

        //查看该订单项是否存在
        OrderItem orderItem = orderItemRepository.findByUserIdAndProductIdAndOrderIdNull(userId, productId);

        if (orderItem != null) {

            orderItem.setNumber(number);
            orderItemRepository.save(orderItem);

        } else {
            orderItem = new OrderItem();

            orderItem.setProductId(product.getId());

            orderItem.setUserId(userId);

            orderItem.setNumber(number);

            orderItemRepository.save(orderItem);

        }

        return true;
    }



    /**
     * 返回销量信息
     * */
    @Override
    public Integer countProductSale(Integer productId) {


        List<OrderItem> orderItemList = orderItemRepository.findByProductIdAndOrderIdIsNotNull(productId);

        int count = 0;

        if (orderItemList != null && !orderItemList.isEmpty()){

            for (OrderItem orderItem : orderItemList){
                count += orderItem.getNumber();
            }

        }

        return count;

    }


}
