package en.edu.kmust.store.review.client;

import en.edu.kmust.store.review.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "service-user",fallback = UserClientFallback.class)
public interface UserFeignClient {
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    User findUserById(@PathVariable("id") Integer userId);

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