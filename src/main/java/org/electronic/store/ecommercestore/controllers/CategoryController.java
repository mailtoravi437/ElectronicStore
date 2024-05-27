package org.electronic.store.ecommercestore.controllers;

import org.electronic.store.ecommercestore.dtos.ApiResponseMessage;
import org.electronic.store.ecommercestore.dtos.CategoryDto;
import org.electronic.store.ecommercestore.dtos.PageableResponse;
import org.electronic.store.ecommercestore.dtos.ProductDto;
import org.electronic.store.ecommercestore.services.CategoryService;
import org.electronic.store.ecommercestore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    @PostMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable String categoryId) {
        CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId) {
        categoryService.delete(categoryId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Category deleted successfully").success(true).status(HttpStatus.NO_CONTENT).build();
        return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/get/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId) {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse> getAllCategories(@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String sortBy, @RequestParam String sortDir) {
        PageableResponse pageableResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    //create product with category
    @PostMapping("/{categoryId}/products/create")
    public ResponseEntity<ProductDto> createWithProduct(@RequestBody ProductDto productDto, @PathVariable String categoryId) {
        ProductDto productDto1 = productService.createWithCategory(productDto, categoryId);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    //update category of product
    @PutMapping("/{categoryId}/products/update/{productId}")
    public ResponseEntity<ProductDto> updateCategory(@PathVariable String categoryId, @PathVariable String productId) {
        ProductDto productDto = productService.updateCategory(productId, categoryId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    //get all products by category
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse> getAllProductsByCategory(@PathVariable String categoryId, @RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String sortBy, @RequestParam String sortDir) {
        PageableResponse<ProductDto> pageableResponse = productService.getAllProductsByCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }




}
