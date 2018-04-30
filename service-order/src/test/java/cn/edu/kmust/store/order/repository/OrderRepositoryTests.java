package cn.edu.kmust.store.order.repository;


import cn.edu.kmust.store.order.entity.Order;
import cn.edu.kmust.store.order.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderRepositoryTests {


    @Resource
    private OrderRepository orderRepository;




    @Test
    public void getByUserIdAndStatusIsNotTest(){

        List<Order> orderList = orderRepository.getByUserIdAndStatusIsNot(8, OrderService.DELETE);

        for (Order order: orderList){
            System.out.println(order);
        }


    }


}
