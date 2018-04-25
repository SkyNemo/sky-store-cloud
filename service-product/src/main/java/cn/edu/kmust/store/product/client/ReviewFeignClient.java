package cn.edu.kmust.store.product.client;

import cn.edu.kmust.store.product.entity.Review;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Feign的fallback测试
 * 使用@FeignClient的fallback属性指定回退类
 *
 * @author Nemo
 */

@FeignClient(name = "service-review",fallback = ReviewFeignClientFallback.class)
public interface ReviewFeignClient {

    @RequestMapping(value = "/review/prodcutId/{id}",method = RequestMethod.GET)
    List<Review> findByProductId(@PathVariable("id") Integer id);

}

/**
 * 回退类FeignClientFallback需实现Feign Client接口
 * FeignClientFallback也可以是public class，没有区别
 *
 * @author Nemo
 */
@Component
class ReviewFeignClientFallback implements ReviewFeignClient {
    @Override
    public List<Review> findByProductId(Integer id) {

        List<Review> reviews = new ArrayList<Review>();

        Review review = new Review();
        review.setId(-1);
        review.setUserId(-1);
        review.setContent("获取评论失败");
        review.setCreateDate(LocalDateTime.now());

        reviews.add(review);

        return reviews;
    }
}