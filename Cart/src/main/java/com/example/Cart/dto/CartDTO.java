package com.example.Cart.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class CartDTO {

    @Id
    private String cartId;
    private String productId;
    private String userId;

}
