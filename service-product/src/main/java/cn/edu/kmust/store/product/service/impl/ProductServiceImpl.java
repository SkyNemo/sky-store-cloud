package cn.edu.kmust.store.product.service.impl;

import cn.edu.kmust.store.product.client.*;
import cn.edu.kmust.store.product.entity.*;
import cn.edu.kmust.store.product.param.*;
import cn.edu.kmust.store.product.repository.ProductImageRepository;
import cn.edu.kmust.store.product.repository.ProductRepository;
import cn.edu.kmust.store.product.service.ProductImageService;
import cn.edu.kmust.store.product.service.ProductService;
import cn.edu.kmust.store.product.util.VoUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
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

    @Resource
    private OrderFeignClient orderFeignClient;


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    /**
     * 填充商品信息
     */
    public void fillProductDtoVo(Product product, ProductDto productDto) {

        BeanUtils.copyProperties(product, productDto);

        // 获取数据库中的商品图片列表
        List<ProductImage> productImageList = productImageRepository.findByProductIdAndTypeOrderByIdDesc(product.getId(), ProductImageService.TYPE_SINGLE);

        // 商品可能没有图片，需要做判断
        if (productImageList != null && !productImageList.isEmpty()) {
            productDto.setProductImageId(productImageList.get(0).getId());
        } else {
            productDto.setProductImageId(-1);
        }

    }


    /**
     * 根据商品id返回商品详细信息
     */
    @Override
    public ProductDetailVo getProductDetailVoById(Integer productId) {

        // 根据id获取数据库中的商品信息
        Product product = productRepository.findOne(productId);

        if (product == null) {
            product = productRepository.findLastProduct();
        }

        // 封装Vo
        ProductDetailVo productDetailVo = new ProductDetailVo();

        // 对拷信息
        BeanUtils.copyProperties(product, productDetailVo);

        // 设置销量
        Integer sale = orderFeignClient.findSaleByProductId(productId);

        productDetailVo.setSale(sale);

        return productDetailVo;
    }


    /**
     * 设置评价列表
     */
    @Override
    public void setProductDetailVoReviews(ProductDetailVo productDetailVo) {

        // 远程获取评价列表信息
        List<Review> reviewList = this.reviewFeignClient.findByProductId(productDetailVo.getId());

        List<ReviewVo> reviewVoList = new ArrayList<>();

        if (reviewList != null && !reviewList.isEmpty()) {

            for (Review review : reviewList) {

                ReviewVo reviewVo = new ReviewVo();

                BeanUtils.copyProperties(review, reviewVo);

                User user = userFeignClient.findUserById(review.getUserId());

                if (user != null){

                    reviewVo.setUser(user);

                    reviewVoList.add(reviewVo);
                }else {

                    User defaultUser = new User();

                    defaultUser.setId(-1);

                    defaultUser.setName("获取名称失败");

                    reviewVo.setUser(defaultUser);

                }

            }
        }

        productDetailVo.setReviews(reviewVoList);

    }

    /**
     * 设置属性以及属性值
     */
    @Override
    public void setProductDetailVoPropertyAndValue(ProductDetailVo productDetailVo) {

        Map<String, String> propertyAndValueMap = new HashMap<>();

        // 远程调用获取属性值信息
        List<PropertyValue> propertyValues = propertyValueFeignClient.findByProductId(productDetailVo.getId());

        // 根据分类id调用属性信息
        List<Property> properties = propertyFeignClient.findByCategoryId(productDetailVo.getCategoryId());

        if ((propertyValues != null) && (properties != null)) {

            for (int i = 0; i < properties.size() - 1; i++) {
                propertyAndValueMap.put(properties.get(i).getName(), propertyValues.get(i).getValue());
            }
        }
        productDetailVo.setPropertyAndValueMap(propertyAndValueMap);

    }


    /**
     * 根据商品id返回商品信息DTO
     */
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


    /**
     * 返回分类对象
     */
    @Override
    public List<CategoryHomeVo> getCategoryHomeVoImprove(List<Category> categoryList) {

        List<Product> productList = null;

        if (categoryList == null) {

            // 获取数据库中所有商品信息
            productList = productRepository.findAll();

        } else {
            // 获取最后添加的分类下的商品信息
            productList = productRepository.findByCategoryId(categoryList.get(0).getId());
        }

        // 创建Vo
        List<ProductHomeVo> productHomeVoList = null;

        List<Integer> productIds = null;

        if (productList != null) {

            productHomeVoList = new ArrayList<>(productList.size());

            productIds = new ArrayList<>();

            for (Product product : productList) {
                productIds.add(product.getId());
            }

            // 根据商品id查询图片
            List<ProductImage> images = productImageRepository.findByProductId(ProductImageService.TYPE_SINGLE, productIds);


            for (Product product : productList) {

                ProductHomeVo productHomeVo = new ProductHomeVo();
                BeanUtils.copyProperties(product, productHomeVo);

                if (images != null) {

                    Iterator<ProductImage> iterator = images.iterator();
                    ProductImageVo productImageVo = new ProductImageVo();

                    // 是否找到图片
                    boolean findImg = false;

                    while (iterator.hasNext()) {

                        ProductImage productImage = iterator.next();

                        if (productImage.getProductId().equals(product.getId())) {

                            BeanUtils.copyProperties(productImage, productImageVo);

                            findImg = true;
                            iterator.remove();
                            break;
                        }
                    }

                    // 图片不存在，设置默认图片
                    if (!findImg) {
                        productImageVo.setId(-1);
                    }

                    // 设置商品图片
                    productHomeVo.setFirstProductImage(productImageVo);
                    // 将添加完图片的商品加入商品列表
                    productHomeVoList.add(productHomeVo);
                }

            }

        }

        List<CategoryHomeVo> categoryHomeVoList = null;


        if (categoryList == null || categoryList.size() == 0) {
            // 获取所有分类
            List<Category> allCategory = categoryFeignClient.findAllCategory();

            if (allCategory != null) {
                categoryHomeVoList = VoUtils.copyList(allCategory, CategoryHomeVo.class);
            } else {

                categoryHomeVoList = new ArrayList<>();
            }

            // 调用 填充分类下商品 的方法
            this.fillCategoryHomeVoListImprove(categoryHomeVoList, productHomeVoList, true);

        } else {

            categoryHomeVoList = VoUtils.copyList(categoryList, CategoryHomeVo.class);
            // 调用 填充分类下商品 的方法
            this.fillCategoryHomeVoListImprove(categoryHomeVoList, productHomeVoList, false);
        }

        return categoryHomeVoList;

    }


    /**
     * 填充分类下的商品
     */
    public void fillCategoryHomeVoListImprove(List<CategoryHomeVo> categoryHomeVoList, List<ProductHomeVo> productHomeVoList, boolean isFillProperty) {


        if (categoryHomeVoList != null) {


            // 遍历所有分类
            for (CategoryHomeVo categoryHomeVo : categoryHomeVoList) {

                List<ProductHomeVo> productHomeVoListPart = new ArrayList<>();

                Iterator<ProductHomeVo> iterator = productHomeVoList.iterator();

                while (iterator.hasNext()) {

                    ProductHomeVo productHomeVo = iterator.next();

                    if (productHomeVo.getCategoryId().equals(categoryHomeVo.getId())) {

                        productHomeVoListPart.add(productHomeVo);

                        // 遍历后将该商品删除，剩下的即为其他分类的商品，最后剩下的即为默认分类
                        iterator.remove();

                    }
                }

                // 设置该分类下商品列表
                categoryHomeVo.setProductList(productHomeVoListPart);


                if (isFillProperty) {

                    // 调用填充分类属性值的方法
                    this.fillCategoryHomeVoByRow(categoryHomeVo);
                }

            }

        }


        if (productHomeVoList.size() != 0) {

            CategoryHomeVo categoryHomeVo = new CategoryHomeVo();

            categoryHomeVo.setId(-1);

            categoryHomeVo.setProductList(productHomeVoList);

            categoryHomeVo.setName("默认分类");

            categoryHomeVoList.add(categoryHomeVo);


            if (isFillProperty) {

                // 调用填充分类属性值的方法
                this.fillCategoryHomeVoByRow(categoryHomeVo);
            }

        }


    }


    /**
     * 填充属性值
     */
    public void fillCategoryHomeVoByRow(CategoryHomeVo categoryHomeVo) {

        if (categoryHomeVo != null) {

            // 将一个List等分为多个List，余数也作为一个List
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
                        productByRow.add(productHomeVoList.subList(listSize - remain, listSize));
                    }

                    categoryHomeVo.setProductsByRow(productByRow);
                }

            }
        } else {
            return;
        }
    }


    /**
     * 根据分类id返回分类
     */
    @Override
    public CategoryHomeVo getCategoryHomeVoByCategoryId(Integer id) {

        Category category = categoryFeignClient.findByCategoryId(id);

        List<Category> categoryList = null;

        if (category != null) {

            categoryList = new ArrayList<>();
            categoryList.add(category);

        } else {
            return null;
        }


        List<CategoryHomeVo> categoryHomeVoList = this.getCategoryHomeVoImprove(categoryList);

        if (categoryHomeVoList != null && !categoryHomeVoList.isEmpty()) {
            return categoryHomeVoList.get(0);
        } else {
            return null;
        }

    }

    /**
     * 校验库存
     */
    @Override
    public boolean checkStock(Integer productId, Integer productNumber) {

        Product product = productRepository.getOne(productId);

        if (product != null) {
            if (product.getStock() > productNumber) {
                return true;
            }
        }
        return false;
    }

    /**
     * 更新库存
     */
    @Override
    public boolean updateStock(Integer productId, Integer productNumber) {

        Product product = productRepository.getOne(productId);

        if (product != null) {
            if (product.getStock() > productNumber) {
                product.setStock(product.getStock() - productNumber);
                productRepository.save(product);
                return true;
            }
        }
        return false;
    }


    // 以下为重构前的方法


    @Transactional(readOnly = true)
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


    public List<CategoryHomeVo> getAllCategoryHomeVoList() {

        List<Category> categories = categoryFeignClient.findAllCategory();

        List<CategoryHomeVo> categoryHomeVos = null;

        if (categories != null) {

            categoryHomeVos = new ArrayList<>(categories.size());
            this.fillCategoryHomeVoList(categories, categoryHomeVos);
        }


        return categoryHomeVos;
    }


}
