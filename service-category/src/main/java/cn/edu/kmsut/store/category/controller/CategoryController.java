package cn.edu.kmsut.store.category.controller;


import cn.edu.kmsut.store.category.entity.Category;
import cn.edu.kmsut.store.category.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CategoryController {

    @Resource
    private CategoryService categoryService;



    /*
    * 返回所有分类
    * */
    @RequestMapping("/category")
    public List<Category> findAllCategory(){

        List<Category> categories = categoryService.findAllCategory();

        return categories;
    }

}
