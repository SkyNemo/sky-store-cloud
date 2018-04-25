package cn.edu.kmsut.store.category.controller;


import cn.edu.kmsut.store.category.entity.Property;
import cn.edu.kmsut.store.category.service.PropertyService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class PropertyController {



    @Resource
    private PropertyService propertyService;



    @RequestMapping("/property/categoryId/{id}")
    public List<Property> findPropertyByCategoryId(@PathVariable Integer id){

        List<Property> properties = propertyService.findByCategoryId(id);

        return properties;
    }

}
