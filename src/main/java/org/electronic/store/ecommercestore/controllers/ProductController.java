package org.electronic.store.ecommercestore.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.electronic.store.ecommercestore.dtos.*;
import org.electronic.store.ecommercestore.services.FileService;
import org.electronic.store.ecommercestore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imageUploadPath;

    @PostMapping("/create")
    public ResponseEntity<ProductDto> create(ProductDto productDto) {
        ProductDto productDto1 = productService.create(productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    @GetMapping("/get/{productId}")
    public ResponseEntity<ProductDto> getProductById(String productId) {
        ProductDto productDto = productService.getProductById(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDto> update(ProductDto productDto, String productId) {
        ProductDto updatedProduct = productService.update(productDto, productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponseMessage> delete(String productId) {
        productService.delete(productId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message("Product deleted successfully").success(true).status(HttpStatus.NO_CONTENT).build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {
       PageableResponse pageableResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
         return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    @GetMapping("/allLive")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        PageableResponse pageableResponse = productService.getALlLive(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<PageableResponse<ProductDto>> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {
        PageableResponse pageableResponse = productService.searchByTitle(subTitle, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    //upload image
    @PostMapping("/uploadImage/{productId}")
    public ResponseEntity<ImageResponse> uploadImage(@PathVariable String productId, @RequestBody MultipartFile multipartFile) {
        String fileName = fileService.uploadFile(multipartFile, imageUploadPath);
        ProductDto productDto = productService.getProductById(productId);
        productDto.setProductImageName(fileName);
        productService.update(productDto, productId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(fileName).message("Image uploaded successfully").build();
        return new ResponseEntity<>(imageResponse, HttpStatus.OK);
    }

    //serve image
    @GetMapping("/image/{productId}")
    public void serveUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        ProductDto productDto = productService.getProductById(productId);
        InputStream resource =  fileService.getResource(imageUploadPath,productDto.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
