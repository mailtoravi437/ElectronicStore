package org.electronic.store.ecommercestore.services;

import org.electronic.store.ecommercestore.dtos.CategoryDto;
import org.electronic.store.ecommercestore.dtos.PageableResponse;
import org.electronic.store.ecommercestore.entities.Category;

import java.util.List;

public interface CategoryService {
    PageableResponse<CategoryDto> getAllCategories(int pageNumber,int pageSize,String sortBy,String sortDir);
    CategoryDto getCategoryById(String categoryId);
    CategoryDto create(CategoryDto categoryDto);
    CategoryDto update(CategoryDto categoryDto,String categoryId);
    void delete(String categoryId);

}
