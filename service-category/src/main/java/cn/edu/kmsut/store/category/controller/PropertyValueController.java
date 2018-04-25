package cn.edu.kmsut.store.category.controller;


import cn.edu.kmsut.store.category.entity.PropertyValue;
import cn.edu.kmsut.store.category.service.PropertyValueService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class PropertyValueController {


    @Resource
    private PropertyValueService propertyValueService;



    @RequestMapping("/propertyValue/productId/{id}")
    public List<PropertyValue> findPropertyByProductId(@PathVariable Integer id) {

        List<PropertyValue> propertyValues = propertyValueService.findPropertyValueByProductId(id);

        return propertyValues;

    }


}
