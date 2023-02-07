package com.example.Merchants.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class MerchantsDTO {
    @Id
    private String merchantId;
    private String merchantName;
    private String merchantProductId;
    private String merchantEmail;
//    private Products productId;

}
