package cn.edu.kmsut.store.category.service.impl;

import cn.edu.kmsut.store.category.entity.Category;
import cn.edu.kmsut.store.category.repository.CategoryRepository;
import cn.edu.kmsut.store.category.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {


    @Resource
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllCategory() {

        List<Category> categoryList = categoryRepository.findAll();

        return categoryList;
    }
}
