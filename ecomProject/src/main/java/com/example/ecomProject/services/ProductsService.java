package com.example.ecomProject.services;


import com.example.ecomProject.dto.MerchantDTO;
import com.example.ecomProject.entities.MerchantInfo;
import com.example.ecomProject.entities.Products;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;


public interface ProductsService {
    public Products addProductDetails(Products products);

    public void deleteProduct(String productId);

    public List allProducts();

    public List getProductsByCategory(String category);

    public Optional<Products> getProductsByProductId(String productId);

//    public List<Products> getProductsByProductName(String productName);

//    public List getProductsByPrice(Double price);

//    public MerchantDTO addMerchant(MerchantDTO merchantDTO);


    // Merchants

    public MerchantInfo addMerchantDetails(MerchantInfo merchantInfo);

    public void reduceQuantity(String productId);

    public List searchPidByName(String productName);

    public String getMerchantIdByProductId(String productId);

    public Double getPriceByProductId(String productId);


}