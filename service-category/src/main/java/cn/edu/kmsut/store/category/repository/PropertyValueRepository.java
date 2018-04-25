package cn.edu.kmsut.store.category.repository;

import cn.edu.kmsut.store.category.entity.PropertyValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyValueRepository extends JpaRepository<PropertyValue, Integer> {

    List<PropertyValue> findByProductIdOrderByPropertyId(Integer id);

}
