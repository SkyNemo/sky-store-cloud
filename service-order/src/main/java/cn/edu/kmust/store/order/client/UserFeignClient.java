package cn.edu.kmust.store.order.client;

import cn.edu.kmust.store.order.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "service-category",fallback = UserClientFallback.class)
public interface UserFeignClient {
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    User findUserById(@PathVariable("userId") Integer userId);

}

/**
 * 回退类FeignClientFallback需实现Feign Client接口
 * FeignClientFallback也可以是public class，没有区别
 *
 * @author Nemo
 */
@Component
class UserClientFallback implements UserFeignClient {
    @Override
    public User findUserById(Integer userId) {
        return null;
    }
}