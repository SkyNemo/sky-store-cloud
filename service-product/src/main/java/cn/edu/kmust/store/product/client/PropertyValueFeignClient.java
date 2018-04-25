package cn.edu.kmust.store.product.client;

import cn.edu.kmust.store.product.entity.PropertyValue;
import cn.edu.kmust.store.product.entity.Review;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@FeignClient(name = "service-category",fallback = PropertyValueClientFallback.class)
public interface PropertyValueFeignClient {
    @RequestMapping(value = "/propertyValue/productId/{id}",method = RequestMethod.GET)
    List<PropertyValue> findByProductId(@PathVariable("id") Integer id);

}

/**
 * 回退类FeignClientFallback需实现Feign Client接口
 * FeignClientFallback也可以是public class，没有区别
 *
 * @author Nemo
 */
@Component
class PropertyValueClientFallback implements PropertyValueFeignClient {
    @Override
    public List<PropertyValue> findByProductId(Integer id) {

        return null;
    }
}