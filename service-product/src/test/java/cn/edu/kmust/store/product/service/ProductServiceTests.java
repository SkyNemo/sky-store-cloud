package cn.edu.kmust.store.product.service;


import cn.edu.kmust.store.product.entity.Category;
import cn.edu.kmust.store.product.entity.Product;
import cn.edu.kmust.store.product.param.CategoryHomeVo;
import cn.edu.kmust.store.product.param.ProductHomeVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTests {


    @Autowired
    private ProductService productService;



    @Test
    public void testProductGet(){

        Product findOne = productService.getProductById(962);

        System.out.println(findOne);

    }


    @Test
    public void testFillCategoryHomeVo(){

        Category category = new Category();
        category.setId(60);
        category.setName("安全座椅");

        CategoryHomeVo categoryHomeVo = new CategoryHomeVo();
        productService.fillCategoryHomeVo(category,categoryHomeVo);

        for (ProductHomeVo productHomeVo: categoryHomeVo.getProductList()){

            System.out.println(productHomeVo.getFirstProductImage().getId());

        }

        System.out.println(categoryHomeVo);


    }


    @Test
    public void testFillCategoryHomeVoList(){

        Category category1 = new Category();
        category1.setId(60);
        category1.setName("安全座椅");

        Category category2 = new Category();
        category2.setId(64);
        category2.setName("太阳镜");

        Category category3 = new Category();
        category3.setId(68);
        category3.setName("品牌女装");

        List<Category> categories = new ArrayList<Category>();

        categories.add(category1);
        categories.add(category2);
        categories.add(category3);



        List<CategoryHomeVo> categoryHomeVos = new ArrayList<>(categories.size());


        System.out.println("test :  " + categories.size() + " : " + categoryHomeVos.size());


        productService.fillCategoryHomeVoList(categories,categoryHomeVos);

        for (CategoryHomeVo categoryHomeVo : categoryHomeVos){
            List<ProductHomeVo> productList = categoryHomeVo.getProductList();
            for (ProductHomeVo productHomeVo : productList){
                System.out.println(productHomeVo.getFirstProductImage().getId());
            }
        }

    }


}
