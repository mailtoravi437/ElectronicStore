package org.electronic.store.ecommercestore.services.impl;

import org.electronic.store.ecommercestore.dtos.ImageResponse;
import org.electronic.store.ecommercestore.dtos.PageableResponse;
import org.electronic.store.ecommercestore.dtos.ProductDto;
import org.electronic.store.ecommercestore.entities.Category;
import org.electronic.store.ecommercestore.entities.Product;
import org.electronic.store.ecommercestore.exceptions.ResourceNotFoundException;
import org.electronic.store.ecommercestore.helper.Helper;
import org.electronic.store.ecommercestore.repositories.CategoryRepository;
import org.electronic.store.ecommercestore.repositories.ProductRepository;
import org.electronic.store.ecommercestore.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;



    @Override
    public ProductDto getProductById(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new RuntimeException("Product not found"));
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        String productId = productRepository.findAll().size() + 1 + "";
        Product product = modelMapper.map(productDto, Product.class);
        product.setProductId(productId);
        product.setAddedDate(new Date());
        Product saveProduct =  productRepository.save(product);
        return modelMapper.map(saveProduct, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new RuntimeException("Product not found"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductImageName(productDto.getProductImageName());
        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void delete(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

    @Override
    public PageableResponse<ProductDto> getALlLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle,pageable);
        return Helper.getPageableResponse(page, ProductDto.class);


    }

    //upload image
    @Override
    public ImageResponse uploadImage(String productId, MultipartFile file) {
        String imageName = file.getOriginalFilename();
        Product product = productRepository.findById(productId).orElseThrow(()->new RuntimeException("Product not found"));
        product.setProductImageName(imageName);
        productRepository.save(product);
        return ImageResponse.builder().imageName(imageName).message("Image uploaded successfully").success(true).build();

    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        //fetch category
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException("Category not found"));
        Product product = modelMapper.map(productDto, Product.class);
        product.setCategory(category);
        product.setAddedDate(new Date());
        Product saveProduct =  productRepository.save(product);
        return modelMapper.map(saveProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        //update category of product
        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not found"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found"));
        product.setCategory(category);
        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page page = productRepository.findAll(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    //get all products by category
    @Override
    public PageableResponse<ProductDto> getAllProductsByCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found"));
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page page = productRepository.findByCategory(category, pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }


}
