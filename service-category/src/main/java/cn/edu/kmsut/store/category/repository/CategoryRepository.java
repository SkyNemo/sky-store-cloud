package cn.edu.kmsut.store.category.repository;

import cn.edu.kmsut.store.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
