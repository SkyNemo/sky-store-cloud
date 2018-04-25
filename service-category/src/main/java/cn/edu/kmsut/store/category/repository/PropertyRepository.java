package cn.edu.kmsut.store.category.repository;

import cn.edu.kmsut.store.category.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Integer> {

    List<Property> findByCategoryId(Integer id);

}
