package cn.edu.kmust.store.product.client;

import cn.edu.kmust.store.product.entity.Property;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "service-category",fallback = PropertyClientFallback.class)
public interface PropertyFeignClient {
    @RequestMapping(value = "/property/categoryId/{id}", method = RequestMethod.GET)
    List<Property> findByCategoryId(@PathVariable("id") Integer id);

}

/**
 * 回退类FeignClientFallback需实现Feign Client接口
 * FeignClientFallback也可以是public class，没有区别
 *
 * @author Nemo
 */
@Component
class PropertyClientFallback implements PropertyFeignClient {
    @Override
    public List<Property> findByCategoryId(Integer id) {

        return null;
    }
}