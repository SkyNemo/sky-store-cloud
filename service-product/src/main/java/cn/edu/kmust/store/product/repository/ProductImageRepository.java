package cn.edu.kmust.store.product.repository;

import cn.edu.kmust.store.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {



    List<ProductImage> findByProductIdAndTypeOrderByIdDesc(Integer productId, String type);

    @Query("select p from ProductImage as p where type = :type and p.productId in (:productIds) group by p.productId")
    List<ProductImage> findByProductId(@Param(value = "type") String type,@Param(value = "productIds") List<Integer> productIds);

}
