package cn.edu.kmust.store.product.controller;


import cn.edu.kmust.store.product.client.CategoryFeignClient;
import cn.edu.kmust.store.product.client.PropertyFeignClient;
import cn.edu.kmust.store.product.client.PropertyValueFeignClient;
import cn.edu.kmust.store.product.client.ReviewFeignClient;
import cn.edu.kmust.store.product.entity.*;
import cn.edu.kmust.store.product.param.CategoryHomeVo;
import cn.edu.kmust.store.product.param.ProductDetailVo;
import cn.edu.kmust.store.product.service.ProductImageService;
import cn.edu.kmust.store.product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {


    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;


    @Resource
    private ReviewFeignClient reviewFeignClient;

    @Resource
    private PropertyValueFeignClient propertyValueFeignClient;

    @Resource
    private PropertyFeignClient propertyFeignClient;

    @Resource
    private CategoryFeignClient categoryFeignClient;


    @RequestMapping("/{id}")
    public String ProductDetial(Model model, @PathVariable Integer id) {


        // 设置产品
        Product product = productService.getProductById(id);

        ProductDetailVo productDetailVo = new ProductDetailVo();

        BeanUtils.copyProperties(product, productDetailVo);


        // 设置照片
        List<ProductImage> detailImages = productImageService.getDetailProductImageByProductId(id);

        List<ProductImage> singleImages = productImageService.getSingleProductImageByProductId(id);

        productDetailVo.setDetailImages(detailImages);

        productDetailVo.setSingleImages(singleImages);

        productDetailVo.setFirstProductImage(singleImages.get(0));


        // 设置评论
        List<Review> reviews = this.reviewFeignClient.findByProductId(id);

        productDetailVo.setReviews(reviews);

        // 设置属性以及属性值
        Map<String, String> propertyAndValueMap = new HashMap<String, String>();

        List<PropertyValue> propertyValues = propertyValueFeignClient.findByProductId(id);

        List<Property> properties = propertyFeignClient.findByCategoryId(product.getCategoryId());


        if ((propertyValues != null) && (properties != null)) {

            for (int i = 0; i < properties.size() - 1; i++) {
                propertyAndValueMap.put(properties.get(i).getName(), propertyValues.get(i).getValue());
            }
        }

        productDetailVo.setPropertyAndValueMap(propertyAndValueMap);

        model.addAttribute("product", productDetailVo);

        return "productDetail";
    }



    @RequestMapping("/")
    public String productList(Model model){

        List<Category> categories = categoryFeignClient.findAllCategory();

        List<CategoryHomeVo> categoryHomeVos = null;

        if (categories != null){

            categoryHomeVos = new ArrayList<>(categories.size());
            productService.fillCategoryHomeVoList(categories,categoryHomeVos);
        }

        model.addAttribute("categories",categoryHomeVos);

        return "home";
    }


}
