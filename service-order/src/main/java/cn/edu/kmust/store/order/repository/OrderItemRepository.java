package cn.edu.kmust.store.order.repository;

import cn.edu.kmust.store.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    OrderItem findByUserIdAndProductIdAndOrderIdNull (Integer userId,Integer productId);

    List<OrderItem> findByOrderId(Integer id);

    List<OrderItem> getByUserIdAndOrderId(Integer userId, Integer orderId);

    Integer countByProductId(Integer productId);
}
