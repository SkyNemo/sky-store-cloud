package cn.edu.kmust.store.order.repository;

import cn.edu.kmust.store.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {


    List<Order> getByUserIdAndStatusIsNot(Integer userId, String excludedStatus);

}
