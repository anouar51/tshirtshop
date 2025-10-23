package com.ecommerce.tshirtshop.repository;

import com.ecommerce.tshirtshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
