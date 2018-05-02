package en.edu.kmust.store.review.client;

import en.edu.kmust.store.review.entity.Order;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "service-order",fallback = OrderItemClientFallback.class)
public interface OrderItemFeignClient {
    @RequestMapping(value = "/productId/{id}", method = RequestMethod.GET)
    Integer countProductSale(@PathVariable("id") Integer productId);

}

/**
 * 回退类FeignClientFallback需实现Feign Client接口
 * FeignClientFallback也可以是public class，没有区别
 *
 * @author Nemo
 */
@Component
class OrderItemClientFallback implements OrderItemFeignClient {
    @Override
    public Integer countProductSale(Integer productId) {

        System.out.println("order service fallback");

        return 0;
    }
}