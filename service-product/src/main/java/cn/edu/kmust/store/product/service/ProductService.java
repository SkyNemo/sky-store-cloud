package cn.edu.kmust.store.product.service;

import cn.edu.kmust.store.product.param.CategoryHomeVo;
import cn.edu.kmust.store.product.param.ProductDetailVo;
import cn.edu.kmust.store.product.param.ProductDto;

import java.util.List;

public interface ProductService {

    int BLOCK_SIZE = 7;

    ProductDetailVo getProductDetailVoById(Integer productId);

    void setProductDetailVoReviews(ProductDetailVo productDetailVo);

    void setProductDetailVoPropertyAndValue(ProductDetailVo productDetailVo);


    ProductDto getProductDtoById(Integer productId);

    List<CategoryHomeVo> getAllCategoryHomeVoImprove();

}
