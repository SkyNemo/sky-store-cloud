package cn.edu.kmust.store.client;

import cn.edu.kmust.store.entity.ProductImage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "service-product", fallback = ProductImageClientFallback.class)
public interface ProductImageFeignClient {
    @RequestMapping(value = "/productImage/{id}", method = RequestMethod.GET)
    ProductImage findProductImageById(@PathVariable("id") Integer id);

}

/**
 * 回退类FeignClientFallback需实现Feign Client接口
 * FeignClientFallback也可以是public class，没有区别
 *
 * @author Nemo
 */
@Component
class ProductImageClientFallback implements ProductImageFeignClient {
    @Override
    public ProductImage findProductImageById(Integer id) {
        System.out.println("callback   for product");
        return null;
    }
}