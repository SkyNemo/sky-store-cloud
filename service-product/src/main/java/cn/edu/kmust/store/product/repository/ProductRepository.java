package cn.edu.kmust.store.product.repository;

import cn.edu.kmust.store.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategoryId(Integer id);


}
