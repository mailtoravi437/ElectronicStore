package org.electronic.store.ecommercestore.repositories;

import org.electronic.store.ecommercestore.dtos.PageableResponse;
import org.electronic.store.ecommercestore.entities.Category;
import org.electronic.store.ecommercestore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {
    Page<Product> findByTitleContaining(String subTitle, Pageable pageable);
    Page<Product> findByLiveTrue(Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);
}
