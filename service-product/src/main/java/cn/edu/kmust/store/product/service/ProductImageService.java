package cn.edu.kmust.store.product.service;

import cn.edu.kmust.store.product.entity.ProductImage;
import cn.edu.kmust.store.product.param.ProductDetailVo;

import java.util.List;

public interface ProductImageService {

    String TYPE_DETAIL = "type_detail";

    String TYPE_SINGLE = "type_single";

    void setProductDetailVoImage(ProductDetailVo productDetailVo);

    List<ProductImage> getDetailProductImageByProductId(Integer productId);


    List<ProductImage> getSingleProductImageByProductId(Integer productId);

}
