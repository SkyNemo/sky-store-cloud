package cn.edu.kmust.store.product.service.impl;

import cn.edu.kmust.store.product.client.*;
import cn.edu.kmust.store.product.entity.*;
import cn.edu.kmust.store.product.param.*;
import cn.edu.kmust.store.product.repository.ProductImageRepository;
import cn.edu.kmust.store.product.repository.ProductRepository;
import cn.edu.kmust.store.product.service.ProductImageService;
import cn.edu.kmust.store.product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {


    @Resource
    private ReviewFeignClient reviewFeignClient;

    @Resource
    private PropertyValueFeignClient propertyValueFeignClient;

    @Resource
    private PropertyFeignClient propertyFeignClient;

    @Resource
    private CategoryFeignClient categoryFeignClient;

    @Resource
    private UserFeignClient userFeignClient;


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;


    public void fillProductDtoVo(Product product, ProductDto productDto) {
        BeanUtils.copyProperties(product, productDto);


        // 商品可能没有图片，需要做判断
        List<ProductImage> productImageList = productImageRepository.findByProductIdAndTypeOrderByIdDesc(product.getId(), ProductImageService.TYPE_SINGLE);

        if (productImageList != null && !productImageList.isEmpty()) {
            productDto.setProductImageId(productImageList.get(0).getId());
        }else {
            productDto.setProductImageId(-1);
        }

    }

    public void fillCategoryHomeVo(Category category, CategoryHomeVo categoryHomeVo) {

        // 根据分类id获取该分类所有商品
        List<Product> productList = productRepository.findByCategoryId(category.getId());

        // 创建Vo
        List<ProductHomeVo> productHomeVoList = null;

        if (productList != null) {

            productHomeVoList = new ArrayList<>(productList.size());


            // 将所有商品对应Vo一一填充
            for (Product product : productList) {

                ProductHomeVo productHomeVo = new ProductHomeVo();
                BeanUtils.copyProperties(product, productHomeVo);

                //设置商品图片
                List<ProductImage> productImageList = productImageRepository.findByProductIdAndTypeOrderByIdDesc(product.getId(), ProductImageService.TYPE_SINGLE);

                ProductImageVo productImageVo = new ProductImageVo();

                if (productImageList != null && !productImageList.isEmpty()) {
                    BeanUtils.copyProperties(productImageList.get(0), productImageVo);
                } else {
                    // 图片不存在，设置默认图片
                    productImageVo.setId(-1);
                }

                productHomeVo.setFirstProductImage(productImageVo);

                productHomeVoList.add(productHomeVo);

            }

        }

        BeanUtils.copyProperties(category, categoryHomeVo);

        // 设置商品Vo列表
        categoryHomeVo.setProductList(productHomeVoList);

        // 设置分类下商品的特点
        fillCategoryHomeVoByRow(categoryHomeVo);

    }

    public void fillCategoryHomeVoList(List<Category> categoryList, List<CategoryHomeVo> categoryHomeVoList) {


        IntStream.range(0, categoryList.size()).forEach(i -> categoryHomeVoList.add(new CategoryHomeVo()));

        // 根据分类列表逐个添加商品信息
        IntStream.range(0, categoryList.size())
                .forEach(i -> this.fillCategoryHomeVo(categoryList.get(i), categoryHomeVoList.get(i)));

    }

    @Override
    public ProductDetailVo getProductDetailVoById(Integer productId) {

        Product product = productRepository.findOne(productId);

        ProductDetailVo productDetailVo = new ProductDetailVo();

        BeanUtils.copyProperties(product, productDetailVo);

        return productDetailVo;
    }


    @Override
    public void setProductDetailVoReviews(ProductDetailVo productDetailVo) {

        List<Review> reviewList = this.reviewFeignClient.findByProductId(productDetailVo.getId());

        List<ReviewVo> reviewVoList = new ArrayList<>();

        if (reviewList != null && !reviewList.isEmpty()){

            for (Review review : reviewList){

                ReviewVo reviewVo = new ReviewVo();

                BeanUtils.copyProperties(review,reviewVo);

                User user = userFeignClient.findUserById(review.getUserId());

                reviewVo.setUser(user);

                reviewVoList.add(reviewVo);

            }
        }

        productDetailVo.setReviews(reviewVoList);

    }

    @Override
    public void setProductDetailVoPropertyAndValue(ProductDetailVo productDetailVo) {
        // 设置属性以及属性值

        Map<String, String> propertyAndValueMap = new HashMap<String, String>();

        List<PropertyValue> propertyValues = propertyValueFeignClient.findByProductId(productDetailVo.getId());

        List<Property> properties = propertyFeignClient.findByCategoryId(productDetailVo.getCategoryId());

        if ((propertyValues != null) && (properties != null)) {

            for (int i = 0; i < properties.size() - 1; i++) {
                propertyAndValueMap.put(properties.get(i).getName(), propertyValues.get(i).getValue());
            }
        }
        productDetailVo.setPropertyAndValueMap(propertyAndValueMap);

    }

    @Override
    public List<CategoryHomeVo> getAllCategoryHomeVoList() {

        List<Category> categories = categoryFeignClient.findAllCategory();

        List<CategoryHomeVo> categoryHomeVos = null;

        if (categories != null) {

            categoryHomeVos = new ArrayList<>(categories.size());
            this.fillCategoryHomeVoList(categories, categoryHomeVos);
        }


        return categoryHomeVos;
    }

    @Override
    public ProductDto getProductDtoById(Integer productId) {

        Product product = productRepository.findOne(productId);

        ProductDto productDto = null;

        if (product != null) {

            productDto = new ProductDto();

            this.fillProductDtoVo(product, productDto);
        }

        return productDto;
    }

    public void fillCategoryHomeVoByRow(CategoryHomeVo categoryHomeVo) {

        if (categoryHomeVo != null) {

            List<ProductHomeVo> productHomeVoList = categoryHomeVo.getProductList();

            List<List<ProductHomeVo>> productByRow = categoryHomeVo.getProductsByRow();

            if (productByRow == null) {
                productByRow = new ArrayList<>();
            }


            if (productHomeVoList != null) {

                int listSize = productHomeVoList.size();

                if (listSize > 0) {

                    int beachSize = listSize / ProductService.BLOCK_SIZE;

                    int remain = listSize % ProductService.BLOCK_SIZE;

                    for (int i = 0; i < beachSize; i++) {

                        int fromIndex = i * ProductService.BLOCK_SIZE;
                        int toIndex = fromIndex + ProductService.BLOCK_SIZE;
                        productByRow.add(productHomeVoList.subList(fromIndex, toIndex));

                    }

                    if (remain > 0) {
                        //System.out.println("fromIndex=" + (listSize - remain) + ", toIndex=" + (listSize));
                        productByRow.add(productHomeVoList.subList(listSize - remain, listSize));
                    }

                    categoryHomeVo.setProductsByRow(productByRow);
                }
            }
        }
    }

}
