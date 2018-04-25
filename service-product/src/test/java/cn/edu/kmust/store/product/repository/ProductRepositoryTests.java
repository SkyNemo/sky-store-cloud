package cn.edu.kmust.store.product.repository;


import cn.edu.kmust.store.product.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductRepositoryTests {


    private final static Logger LOGGER = LoggerFactory.getLogger(ProductRepositoryTests.class);



    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testProductGet(){
        Product findOne = productRepository.findOne(962);
        System.out.println(findOne);
    }

}
