package cn.edu.kmust.store.product.controller;


import cn.edu.kmust.store.product.param.ProductImageDto;
import cn.edu.kmust.store.product.service.ProductImageService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ProductImageController {


    @Resource
    private ProductImageService productImageService;


    /*
    * 根据商品图片Id获取商品图片信息
    * */
    @RequestMapping("/productImage/{id}")
    public ProductImageDto getProductImageById(@PathVariable Integer id) {


        ProductImageDto productImageDto = productImageService.getProductImageById(id);

        return productImageDto;
    }

}
