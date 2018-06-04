package cn.edu.kmust.store.order.client;

import cn.edu.kmust.store.order.entity.Product;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "service-product", fallback = ProductClientFallback.class)
public interface ProductFeignClient {
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    Product findProductById(@PathVariable("id") Integer id);

    @RequestMapping(value = "/checkStock/product/{id}/{number}", method = RequestMethod.GET)
    Boolean checkProductStock(@PathVariable("id") Integer id,@PathVariable("number") Integer number);

    @RequestMapping(value = "/updateStock/product/{id}/{number}", method = RequestMethod.GET)
    Boolean updateProductStock(@PathVariable("id") Integer id,@PathVariable("number") Integer number);

}

/**
 * 回退类FeignClientFallback需实现Feign Client接口
 * FeignClientFallback也可以是public class，没有区别
 *
 * @author Nemo
 */
@Component
class ProductClientFallback implements ProductFeignClient {
    @Override
    public Product findProductById(Integer id) {
        System.out.println("callback   for product");

        Product product = new Product();

        product.setId(-1);

        product.setName("获取商品失败");

        return product;
    }


    @Override
    public Boolean checkProductStock(Integer id, Integer number) {
        return false;
    }

    @Override
    public Boolean updateProductStock(Integer id, Integer number) {
        return null;
    }

}