package com.project.productService.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.productService.dto.ProductAnalyticsDto;
import com.project.productService.service.ProductAnalyticsService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products/analytics")
public class ProductAnalyticsController {

    private final ProductAnalyticsService analyticsService;
    
    @Autowired
    public ProductAnalyticsController(ProductAnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }
    
    @GetMapping("/top-selling")
    public ResponseEntity<List<ProductAnalyticsDto>> getTopSellingProducts(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(analyticsService.getTopSellingProducts(limit));
    }
    
    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductAnalyticsDto>> getLowStockProducts(
            @RequestParam(defaultValue = "5") int threshold) {
        return ResponseEntity.ok(analyticsService.getLowStockProducts(threshold));
    }
    
    @GetMapping("/by-category")
    public ResponseEntity<Map<String, Long>> getProductsByCategory() {
        return ResponseEntity.ok(analyticsService.getProductsByCategory());
    }
    
    @GetMapping("/inventory-value")
    public ResponseEntity<Double> getTotalInventoryValue() {
        return ResponseEntity.ok(analyticsService.getTotalInventoryValue());
    }
}
