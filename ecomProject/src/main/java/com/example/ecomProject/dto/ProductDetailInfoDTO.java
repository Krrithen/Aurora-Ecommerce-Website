package com.example.ecomProject.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailInfoDTO {
    private ProductDetail productDetail;
    private List<MerchantInfo> merchantInfoList;
}
