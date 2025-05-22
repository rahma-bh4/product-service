package com.project.productService.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.productService.repository.ProductRepository;
import com.project.productService.dto.ProductAnalyticsDto;
import com.project.productService.model.Product;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductAnalyticsService {

    private final ProductRepository productRepository;
    
    @Autowired
    public ProductAnalyticsService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public List<ProductAnalyticsDto> getTopSellingProducts(int limit) {
        // In a real implementation, this would query from order data
        // Here we'll simulate by assuming products with low stock are popular
        List<Product> products = productRepository.findAllByOrderByQuantityAsc();
        return products.stream()
                .limit(limit)
                .map(this::mapToAnalyticsDto)
                .collect(Collectors.toList());
    }
    
    public List<ProductAnalyticsDto> getLowStockProducts(int threshold) {
        List<Product> products = productRepository.findLowStockProducts(threshold);
        return products.stream()
                .map(this::mapToAnalyticsDto)
                .collect(Collectors.toList());
    }
    
    public Map<String, Long> getProductsByCategory() {
        List<Product> products = productRepository.findAll();
        Map<String, Long> categoryCounts = new HashMap<>();
        
        for (Product product : products) {
            String category = product.getCategory() != null ? product.getCategory() : "Uncategorized";
            categoryCounts.put(category, categoryCounts.getOrDefault(category, 0L) + 1);
        }
        
        return categoryCounts;
    }
    
    public double getTotalInventoryValue() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .mapToDouble(product -> product.getPrice() * product.getQuantity())
                .sum();
    }
    
    private ProductAnalyticsDto mapToAnalyticsDto(Product product) {
        ProductAnalyticsDto dto = new ProductAnalyticsDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCategory(product.getCategory());
        dto.setCurrentStock(product.getQuantity());
        dto.setPrice(product.getPrice());
        dto.setInventoryValue(product.getPrice() * product.getQuantity());
        return dto;
    }
}