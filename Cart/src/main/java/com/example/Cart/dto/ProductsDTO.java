package com.example.Cart.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@JsonIgnoreProperties
public class ProductsDTO {

    @Id
    private String productId;
    private String productName;
    private String description;
    private Double price;
    private Integer productQuantity;
    private String image;
    private String category;
    private String brand;
    private String merchantId;

}