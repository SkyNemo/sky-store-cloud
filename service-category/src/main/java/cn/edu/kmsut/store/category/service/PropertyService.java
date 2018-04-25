package cn.edu.kmsut.store.category.service;

import cn.edu.kmsut.store.category.entity.Property;

import java.util.List;

public interface PropertyService {

    List<Property> findByCategoryId(Integer id);

}
