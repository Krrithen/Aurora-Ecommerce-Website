package com.example.Orders.dto;


import lombok.Data;
import org.springframework.data.annotation.Id;


@Data
public class OrderProductsDTO {

    @Id
    private String productId;
    private String productName;
    private Double price;
    private Integer Quantity=1;
    private String merchantId;

}