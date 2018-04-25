package cn.edu.kmsut.store.category.service.impl;

import cn.edu.kmsut.store.category.entity.PropertyValue;
import cn.edu.kmsut.store.category.repository.PropertyValueRepository;
import cn.edu.kmsut.store.category.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class PropertyValueServiceImpl implements PropertyValueService {


    @Autowired
    private PropertyValueRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<PropertyValue> findPropertyValueByProductId(Integer id) {

        List<PropertyValue> propertyValueList = repository.findByProductIdOrderByPropertyId(id);

        return propertyValueList;

    }
}
