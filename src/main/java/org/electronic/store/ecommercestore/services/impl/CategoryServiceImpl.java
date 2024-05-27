package org.electronic.store.ecommercestore.services.impl;

import org.electronic.store.ecommercestore.dtos.CategoryDto;
import org.electronic.store.ecommercestore.dtos.PageableResponse;
import org.electronic.store.ecommercestore.entities.Category;
import org.electronic.store.ecommercestore.helper.Helper;
import org.electronic.store.ecommercestore.repositories.CategoryRepository;
import org.electronic.store.ecommercestore.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    private ModelMapper modelMapper;

    @Override
    public PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        List<CategoryDto> categoryDtos = categoryPage.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
        Page<CategoryDto> categoryDtoPage = new PageImpl<>(categoryDtos, pageable, categoryPage.getTotalElements());
        return Helper.getPageableResponse(categoryDtoPage, CategoryDto.class);
    }


    @Override
    public CategoryDto getCategoryById(String categoryId) {
        return null;
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        String categoryId = categoryRepository.findAll().size() + 1 + "";
        categoryDto.setCategoryId(categoryId);
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory =  categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto,String categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException("Category not found"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }

    public CategoryDto entityToDto(Category category){
        return modelMapper.map(category, CategoryDto.class);
    }

    public Category dtoToEntity(CategoryDto categoryDto){
        return modelMapper.map(categoryDto, Category.class);
    }

}
