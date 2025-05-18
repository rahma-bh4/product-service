package com.project.productService.service;


import java.util.List;

import com.project.productService.dto.ProductDto;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    
    ProductDto getProductById(Long id);
    
    List<ProductDto> getAllProducts();
    
    ProductDto updateProduct(Long id, ProductDto productDto);
    
    void deleteProduct(Long id);
    
    List<ProductDto> searchProducts(String keyword);
    
    List<ProductDto> getProductsByCategory(String category);
    
    List<ProductDto> getLowStockProducts(int threshold);
}
