package com.project.productService.dto;



import lombok.Data;

@Data
public class ProductAnalyticsDto {
    private Long id;
    private String name;
    private String category;
    private Integer currentStock;
    private Double price;
    private Double inventoryValue;
    private Integer salesCount; 
    private String imageUrl;// Would be populated from order data
}