package com.example.ecomProject.entities;


import com.example.ecomProject.dto.MerchantDTO;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@Document(collection = Products.COLLECTION_NAME)
public class Products {

    public static final String COLLECTION_NAME = "Products";

    @Id
    private String productId;
    private String productName;
    private String description;
    private String image;
    private String category;
    private String brand;
    private String merchantId;
    private Double price;
    private Integer productQuantity;
    // private String merchantCode
//    private MerchantDTO merchant;

}