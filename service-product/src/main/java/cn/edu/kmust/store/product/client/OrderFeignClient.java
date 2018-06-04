package cn.edu.kmust.store.product.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "service-order",fallback = OrderClientFallback.class)
public interface OrderFeignClient {

    @RequestMapping(value = "/sale/{id}", method = RequestMethod.GET)
    Integer findSaleByProductId(@PathVariable(name = "id") Integer id);
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
    public Integer findSaleByProductId(Integer id) {

        return 0;
    }
}