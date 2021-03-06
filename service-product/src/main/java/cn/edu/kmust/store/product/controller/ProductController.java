package cn.edu.kmust.store.product.controller;


import cn.edu.kmust.store.product.param.CategoryHomeVo;
import cn.edu.kmust.store.product.param.ProductDetailVo;
import cn.edu.kmust.store.product.param.ProductDto;
import cn.edu.kmust.store.product.service.ProductImageService;
import cn.edu.kmust.store.product.service.ProductService;
import cn.edu.kmust.store.product.util.comparator.ProductComparator;
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


    /*
     * 根据商品id获取商品信息 VO
     * */
    @RequestMapping("/{id}")
    public String ProductDetial(Model model, @PathVariable Integer id) {
        // 获取Vo
        ProductDetailVo productDetailVo = productService.getProductDetailVoById(id);
        // 设置照片
        productImageService.setProductDetailVoImage(productDetailVo);
        // 设置评论
        productService.setProductDetailVoReviews(productDetailVo);
        // 设置属性、属性值
        productService.setProductDetailVoPropertyAndValue(productDetailVo);

        model.addAttribute("product", productDetailVo);
        // 跳转到商品详情页面
        return "productDetail";
    }

    /*
     * 显示首页
     * */
    @RequestMapping("/")
    public String productList(Model model) {
        // 获取首页按分类（CategoryHomeVo）显示商品
        // List<CategoryHomeVo> categoryHomeVos = productService.getAllCategoryHomeVoList();
        List<CategoryHomeVo> categoryHomeVos = productService.getCategoryHomeVoImprove(null);

        // 设置model
        model.addAttribute("categories", categoryHomeVos);
        // 返回首页
        return "home";
    }


    /*
     * 显示分类下的商品
     * */
    @RequestMapping("/categoryHome/{id}/sort/{method}")
    public String CategoryProductList(@PathVariable Integer id,@PathVariable String method, Model model) {

        CategoryHomeVo categoryHomeVo = productService.getCategoryHomeVoByCategoryId(id);

        if (categoryHomeVo == null) {
            return this.productList(model);
        } else {

            // 排序
            ProductComparator.comparator(categoryHomeVo.getProductList(),method);
            // 设置model
            model.addAttribute("category", categoryHomeVo);
            model.addAttribute("sort", method);
            // 返回首页
            return "category";
        }

    }


    /*
     * 根据商品id获取商品信息 DTO
     * */
    @RequestMapping("/product/{id}")
    @ResponseBody
    public ProductDto findProductById(@PathVariable Integer id) {
        // 获取Dto
        ProductDto productDto = productService.getProductDtoById(id);
        // 返回Dto
        return productDto;
    }


    /*
     * 校验库存
     * */
    @RequestMapping("/checkStock/product/{id}/{number}")
    @ResponseBody
    public Boolean checkProductStock(@PathVariable Integer id,@PathVariable Integer number){

        Boolean checkResult = productService.checkStock(id,number);

        return checkResult;
    }


    /*
     * 校验库存
     * */
    @RequestMapping("/updateStock/product/{id}/{number}")
    @ResponseBody
    public Boolean updateProductStock(@PathVariable Integer id,@PathVariable Integer number){

        Boolean updateResult = productService.updateStock(id,number);

        return updateResult;
    }

}
