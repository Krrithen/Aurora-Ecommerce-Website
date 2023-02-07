package com.example.ecomProject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchDTO {

    @Id
    private String productId;
    private String productName;
//    private String description;
//    private Double price;
//    private String image;
//    private String category;
}
