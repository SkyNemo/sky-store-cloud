package cn.edu.kmust.store.product.service.impl;

import cn.edu.kmust.store.product.entity.Category;
import cn.edu.kmust.store.product.entity.Product;
import cn.edu.kmust.store.product.entity.ProductImage;
import cn.edu.kmust.store.product.param.CategoryHomeVo;
import cn.edu.kmust.store.product.param.ProductHomeVo;
import cn.edu.kmust.store.product.repository.ProductImageRepository;
import cn.edu.kmust.store.product.repository.ProductRepository;
import cn.edu.kmust.store.product.service.ProductImageService;
import cn.edu.kmust.store.product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Integer id) {

        Product product = productRepository.findOne(id);

        return product;
    }

    @Override
    public void fillCategoryHomeVo(Category category, CategoryHomeVo categoryHomeVo) {

        List<Product> productList = productRepository.findByCategoryId(category.getId());

        List<ProductHomeVo> productHomeVoList = null;

        if (productList != null) {

            productHomeVoList = new ArrayList<>(productList.size());

            for (Product product : productList) {

                ProductHomeVo productHomeVo = new ProductHomeVo();
                BeanUtils.copyProperties(product, productHomeVo);

                productHomeVo.setFirstProductImage(productImageRepository
                        .findByProductIdAndTypeOrderByIdDesc(product.getId(), ProductImageService.TYPE_SINGLE).get(0));

                productHomeVoList.add(productHomeVo);

            }

        }

        BeanUtils.copyProperties(category, categoryHomeVo);

        categoryHomeVo.setProductList(productHomeVoList);

        fillCategoryHomeVoByRow(categoryHomeVo);


    }

    @Override
    public void fillCategoryHomeVoList(List<Category> categoryList, List<CategoryHomeVo> categoryHomeVoList) {

        IntStream.range(0, categoryList.size()).forEach(i -> categoryHomeVoList.add(new CategoryHomeVo()));

        IntStream.range(0, categoryList.size())
                .forEach(i -> this.fillCategoryHomeVo(categoryList.get(i), categoryHomeVoList.get(i)));

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
                        System.out.println("fromIndex=" + fromIndex + ", toIndex=" + toIndex);
                        productByRow.add(productHomeVoList.subList(fromIndex, toIndex));

                    }


                    if (remain > 0) {
                        System.out.println("fromIndex=" + (listSize - remain) + ", toIndex=" + (listSize));
                        productByRow.add(productHomeVoList.subList(listSize - remain, listSize));
                    }

                    categoryHomeVo.setProductsByRow(productByRow);
                }
            }
        }
    }

}
