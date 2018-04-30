package cn.edu.kmust.store.product.service.impl;

import cn.edu.kmust.store.product.entity.ProductImage;
import cn.edu.kmust.store.product.param.ProductDetailVo;
import cn.edu.kmust.store.product.repository.ProductImageRepository;
import cn.edu.kmust.store.product.service.ProductImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ProductImageServiceImpl implements ProductImageService {


    @Resource
    private ProductImageRepository productImageRepository;


    @Override
    public void setProductDetailVoImage(ProductDetailVo productDetailVo) {
        productDetailVo.setDetailImages(this.getDetailProductImageByProductId(productDetailVo.getId()));
        List<ProductImage> productImages = this.getSingleProductImageByProductId(productDetailVo.getId());
        productDetailVo.setSingleImages(productImages);
        productDetailVo.setFirstProductImage(productImages.get(0));

    }

    @Override
    public List<ProductImage> getDetailProductImageByProductId(Integer productId) {

        List<ProductImage> productImageList = productImageRepository
                .findByProductIdAndTypeOrderByIdDesc(productId, ProductImageService.TYPE_DETAIL);

        return productImageList;
    }

    @Override
    public List<ProductImage> getSingleProductImageByProductId(Integer productId) {
        List<ProductImage> productImageList = productImageRepository
                .findByProductIdAndTypeOrderByIdDesc(productId, ProductImageService.TYPE_SINGLE);

        return productImageList;
    }


}
