package org.electronic.store.ecommercestore.services;

import org.electronic.store.ecommercestore.dtos.ImageResponse;
import org.electronic.store.ecommercestore.dtos.PageableResponse;
import org.electronic.store.ecommercestore.dtos.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir);
    ProductDto getProductById(String productId);
    ProductDto create(ProductDto productDto);
    ProductDto update(ProductDto productDto,String productId);
    void delete(String productId);
    PageableResponse<ProductDto> getALlLive(int pageNumber,int pageSize,String sortBy,String sortDir);
    PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir);
    ImageResponse uploadImage(String productId, MultipartFile file);

    //create product with category
    ProductDto createWithCategory(ProductDto productDto, String categoryId);

    //update category of product
    ProductDto updateCategory(String productId, String categoryId);

    //get all products by category
    PageableResponse<ProductDto> getAllProductsByCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir);

}
