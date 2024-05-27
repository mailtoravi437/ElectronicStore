package org.electronic.store.ecommercestore.repositories;

import org.electronic.store.ecommercestore.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByCategoryId(String categoryId);
}
