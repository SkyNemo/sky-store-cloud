package cn.edu.kmust.store.product.repository;

import cn.edu.kmust.store.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {



    List<ProductImage> findByProductIdAndTypeOrderByIdDesc(Integer productId, String type);
}
