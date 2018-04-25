package cn.edu.kmust.store.product.repository;


import cn.edu.kmust.store.product.entity.ProductImage;
import cn.edu.kmust.store.product.service.ProductImageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductImageRepositoryTests {


    private final static Logger LOGGER = LoggerFactory.getLogger(ProductImageRepositoryTests.class);



    @Autowired
    private ProductImageRepository repository;

    @Test
    public void testProductImageGet(){

        ProductImage findOne = repository.findOne(10198);

        System.out.println(findOne);
    }


    @Test
    public void testGetProductImageByPidAndType(){

        List<ProductImage> list = repository.findByProductIdAndTypeOrderByIdDesc(962, ProductImageService.TYPE_DETAIL);

        for (ProductImage productImage: list){
            System.out.println(productImage);
        }

    }
}
