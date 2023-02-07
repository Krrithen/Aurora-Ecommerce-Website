package com.example.Merchants.services;

import com.example.Merchants.entities.Merchants;
//import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Optional;

public interface MerchantsService {
    public Merchants addMerchantDetails(Merchants merchants);
//    public List getMerchantsByProductId(String productId);
    public Optional<Merchants> getMerchantById(String merchantId);
}