package en.edu.kmust.store.review.client;

import en.edu.kmust.store.review.entity.Order;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "service-order",fallback = OrderClientFallback.class)
public interface OrderFeignClient {
    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    Order findOrderById(@PathVariable("id") Integer orderId);

    @RequestMapping(value = "/finishOrder/{id}",method = RequestMethod.GET)
    boolean finishOrderById(@PathVariable("id") Integer id);

}

/**
 * 回退类FeignClientFallback需实现Feign Client接口
 * FeignClientFallback也可以是public class，没有区别
 *
 * @author Nemo
 */
@Component
class OrderClientFallback implements OrderFeignClient {
    @Override
    public Order findOrderById(Integer orderId) {
        System.out.println("order service fallback");
        return null;
    }

    @Override
    public boolean finishOrderById(Integer id) {

        return false;
    }
}