package com.example.ecomProject.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ProductDetail {
    private String productId;
    private String productName;
    private String description;
    private String image;
    private String category;
    private String brand;
}
