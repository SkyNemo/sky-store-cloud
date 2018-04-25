package cn.edu.kmust.store.product.service;

import cn.edu.kmust.store.product.entity.Category;
import cn.edu.kmust.store.product.entity.Product;
import cn.edu.kmust.store.product.param.CategoryHomeVo;

import java.util.List;

public interface ProductService {

    int BLOCK_SIZE = 8;

    Product getProductById(Integer id);


    void fillCategoryHomeVo(Category category, CategoryHomeVo categoryHomeVo);

    void fillCategoryHomeVoList(List<Category> categoryList, List<CategoryHomeVo> categoryHomeVoList);

}
