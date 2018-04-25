package cn.edu.kmust.store.user.repository;


import cn.edu.kmust.store.user.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserRepositoryTests.class);


    @Autowired
    private UserRepository userRepository;


    @Test
    public void testInsert(){

        User user = new User();
        user.setName("test");
        user.setPassword("123456");
        user.setPhone("18487357220");
        user.setEmail("sky.nemo@outlook.com");

        userRepository.save(user);

        System.out.println(userRepository.findOne(1L));

    }


    @Test
    public void testGet(){

        User user = userRepository.getOne(8L);

    }



}
