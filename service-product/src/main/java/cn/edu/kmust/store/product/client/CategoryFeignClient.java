package cn.edu.kmust.store.product.client;

import cn.edu.kmust.store.product.entity.Category;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "service-category",fallback = CategoryClientFallback.class)
public interface CategoryFeignClient {
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    List<Category> findAllCategory();


    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
    Category findByCategoryId(@PathVariable(name = "id") Integer id);
}

/**
 * 回退类FeignClientFallback需实现Feign Client接口
 * FeignClientFallback也可以是public class，没有区别
 *
 * @author Nemo
 */
@Component
class CategoryClientFallback implements CategoryFeignClient {
    @Override
    public List<Category> findAllCategory() {

        return null;
    }

    @Override
    public Category findByCategoryId(Integer id) {
        return null;
    }

}