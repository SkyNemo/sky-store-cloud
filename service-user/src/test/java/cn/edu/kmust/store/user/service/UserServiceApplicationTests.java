package cn.edu.kmust.store.user.service;


import cn.edu.kmust.store.user.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceApplicationTests {


    @Resource
    private UserService userService;



    @Test
    public void testSelectUserByName(){

        boolean result = userService.selectUserByName("test");

        System.out.println(result);

    }


    @Test
    public void testSelectUserByNameAndPassword(){

        User findOne1 = userService.selectUserByNameAndPassword("test","123456");

        System.out.println(findOne1);


        User findOne2 = userService.selectUserByNameAndPassword("test","12345");

        System.out.println(findOne2);


    }


    @Test
    public void testSaveUser(){

        User user = new User();
        user.setName("test1");
        user.setPassword("123456");
        user.setPhone("18487357221");
        user.setEmail("sky2nemo@outlook.com");


        boolean result = userService.saveUser(user);

        System.out.println(result);
    }


}
