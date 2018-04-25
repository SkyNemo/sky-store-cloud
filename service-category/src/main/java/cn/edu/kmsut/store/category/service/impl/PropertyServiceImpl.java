package cn.edu.kmsut.store.category.service.impl;

import cn.edu.kmsut.store.category.entity.Property;
import cn.edu.kmsut.store.category.repository.PropertyRepository;
import cn.edu.kmsut.store.category.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PropertyServiceImpl implements PropertyService{


    @Autowired
    private PropertyRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Property> findByCategoryId(Integer id) {

        List<Property> propertyList = repository.findByCategoryId(id);

        return propertyList;
    }
}
