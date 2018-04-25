package cn.edu.kmust.store.product.service.impl;

import cn.edu.kmust.store.product.entity.ProductImage;
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
    @Transactional(readOnly = true)
    public ProductImage getProductImageById(Integer id) {

        ProductImage findOne = productImageRepository.findOne(id);

        return findOne;
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
