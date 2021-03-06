package cn.edu.kmust.store.product.service.impl;

import cn.edu.kmust.store.product.entity.ProductImage;
import cn.edu.kmust.store.product.param.ProductDetailVo;
import cn.edu.kmust.store.product.param.ProductImageDto;
import cn.edu.kmust.store.product.param.ProductImageVo;
import cn.edu.kmust.store.product.repository.ProductImageRepository;
import cn.edu.kmust.store.product.service.ProductImageService;
import cn.edu.kmust.store.product.util.VoUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProductImageServiceImpl implements ProductImageService {


    @Resource
    private ProductImageRepository productImageRepository;


    @Override
    public void setProductDetailVoImage(ProductDetailVo productDetailVo) {
        //商品可能没有图片，设置默认图片

        // 1.设置detail图片
        List<ProductImage> detailImageList = this.getDetailProductImageByProductId(productDetailVo.getId());

        List<ProductImageVo> detailImageVoList = VoUtils.copyList(detailImageList, ProductImageVo.class);


        if (detailImageVoList == null || detailImageVoList.isEmpty()) {

            ProductImageVo productImageVo = new ProductImageVo();
            productImageVo.setId(-1);
            detailImageVoList.add(productImageVo);

        }


//        List<ProductImageVo> detailImageVoList = new ArrayList<>();


//        if (detailImageList != null && !detailImageList.isEmpty()) {
//
//            for (ProductImage productImage : detailImageList) {
//
//                ProductImageVo productImageVo = new ProductImageVo();
//                BeanUtils.copyProperties(productImage, productImageVo);
//                detailImageVoList.add(productImageVo);
//            }
//        } else {
//            ProductImageVo productImageVo = new ProductImageVo();
//            productImageVo.setId(-1);
//            detailImageVoList.add(productImageVo);
//        }

        productDetailVo.setDetailImages(detailImageVoList);


        // 2.设置single图片
        List<ProductImage> singleImageList = this.getSingleProductImageByProductId(productDetailVo.getId());

        List<ProductImageVo> singleImageVoList = new ArrayList<>();

        if (singleImageList != null && !singleImageList.isEmpty()) {

            for (ProductImage productImage : singleImageList) {

                ProductImageVo productImageVo = new ProductImageVo();
                BeanUtils.copyProperties(productImage, productImageVo);
                singleImageVoList.add(productImageVo);
            }

        } else {
            ProductImageVo productImageVo = new ProductImageVo();
            productImageVo.setId(-1);
            singleImageVoList.add(productImageVo);
        }

        productDetailVo.setSingleImages(singleImageVoList);
        //
        productDetailVo.setFirstProductImage(singleImageVoList.get(0));

    }

    public List<ProductImage> getDetailProductImageByProductId(Integer productId) {

        List<ProductImage> productImageList = productImageRepository
                .findByProductIdAndTypeOrderByIdDesc(productId, ProductImageService.TYPE_DETAIL);

        return productImageList;
    }

    public List<ProductImage> getSingleProductImageByProductId(Integer productId) {
        List<ProductImage> productImageList = productImageRepository
                .findByProductIdAndTypeOrderByIdDesc(productId, ProductImageService.TYPE_SINGLE);

        return productImageList;
    }

    @Override
    public ProductImageDto getProductImageById(Integer id) {

        ProductImageDto productImageDto = new ProductImageDto();

        ProductImage productImage = productImageRepository.findOne(id);

        BeanUtils.copyProperties(productImage, productImageDto);

        return productImageDto;
    }


}
