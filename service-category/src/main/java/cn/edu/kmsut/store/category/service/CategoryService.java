package cn.edu.kmsut.store.category.service;

import cn.edu.kmsut.store.category.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAllCategory();

    Category findCategoryById(Integer id);

}
