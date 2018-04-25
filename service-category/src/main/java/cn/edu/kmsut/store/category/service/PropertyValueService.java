package cn.edu.kmsut.store.category.service;

import cn.edu.kmsut.store.category.entity.PropertyValue;

import java.util.List;

public interface PropertyValueService {


    List<PropertyValue> findPropertyValueByProductId(Integer id);

}
