package cn.edu.kmust.store.product.controller;


import cn.edu.kmust.store.product.param.CategoryHomeVo;
import cn.edu.kmust.store.product.param.ProductDetailVo;
import cn.edu.kmust.store.product.param.ProductDto;
import cn.edu.kmust.store.product.service.ProductImageService;
import cn.edu.kmust.store.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProductController {


    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;



    @RequestMapping("/{id}")
    public String ProductDetial(Model model, @PathVariable Integer id) {

        ProductDetailVo productDetailVo = productService.getProductDetailVoById(id);

        // 设置照片
        productImageService.setProductDetailVoImage(productDetailVo);

        // 设置评论
        productService.setProductDetailVoReviews(productDetailVo);

        // 设置属性以及属性值
        productService.setProductDetailVoPropertyAndValue(productDetailVo);

        model.addAttribute("product", productDetailVo);

        return "productDetail";
    }


    @RequestMapping("/")
    public String productList(Model model) {


        // 获取首页按分类（CategoryHomeVo）显示商品
        List<CategoryHomeVo> categoryHomeVos = productService.getAllCategoryHomeVoList();

        model.addAttribute("categories", categoryHomeVos);

        return "home";
    }



    /*
    * 根据商品id获取商品信息
    * */
    @RequestMapping("/product/{id}")
    @ResponseBody
    public ProductDto findProductById(@PathVariable Integer id) {

        ProductDto productDto = productService.getProductDtoById(id);

        return productDto;
    }



}
