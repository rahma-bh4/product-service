package com.project.productService.service;



import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.productService.dto.ProductDto;
import com.project.productService.exception.ResourceNotFoundException;
import com.project.productService.model.Product;
import com.project.productService.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        Product savedProduct = productRepository.save(product);
        ProductDto responseDto = new ProductDto();
        BeanUtils.copyProperties(savedProduct, responseDto);
        return responseDto;
    }
    
    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }
    
    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> {
                    ProductDto dto = new ProductDto();
                    BeanUtils.copyProperties(product, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        BeanUtils.copyProperties(productDto, product);
        product.setId(id);
        Product updatedProduct = productRepository.save(product);
        
        ProductDto responseDto = new ProductDto();
        BeanUtils.copyProperties(updatedProduct, responseDto);
        return responseDto;
    }
    
    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }
    
    @Override
    public List<ProductDto> searchProducts(String keyword) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(keyword);
        return products.stream()
                .map(product -> {
                    ProductDto dto = new ProductDto();
                    BeanUtils.copyProperties(product, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ProductDto> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);
        return products.stream()
                .map(product -> {
                    ProductDto dto = new ProductDto();
                    BeanUtils.copyProperties(product, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ProductDto> getLowStockProducts(int threshold) {
        List<Product> products = productRepository.findLowStockProducts(threshold);
        return products.stream()
                .map(product -> {
                    ProductDto dto = new ProductDto();
                    BeanUtils.copyProperties(product, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}