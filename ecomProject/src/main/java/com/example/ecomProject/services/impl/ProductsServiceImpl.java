package com.example.ecomProject.services.impl;

import com.example.ecomProject.dto.MerchantDTO;
import com.example.ecomProject.dto.ProductDetailInfoDTO;
import com.example.ecomProject.dto.ProductsDTO;
import com.example.ecomProject.entities.MerchantInfo;
import com.example.ecomProject.entities.Products;
import com.example.ecomProject.feignclients.FeignMerchantService;
import com.example.ecomProject.feignclients.FeignSearchService;
import com.example.ecomProject.repository.MerchantInfoRepository;
import com.example.ecomProject.repository.ProductsRepository;
import com.example.ecomProject.services.ProductsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;


@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    MerchantInfoRepository merchantInfoRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    FeignMerchantService feignMerchantService;

    @Autowired
    FeignSearchService feignSearchService;

    @Override
    public Products addProductDetails(Products products) {

        return productsRepository.save(products);
    }

    @Override
    public void deleteProduct(String productId) {
        productsRepository.deleteById(productId);
    }

    @Override
    public List<Products> allProducts() {
        return productsRepository.findAll();
    }

    @Override
    public List<Products> getProductsByCategory(String category) {
        return productsRepository.findByCategory(category);
    }

    @Override
    public Optional<Products> getProductsByProductId(String productId) {
        return productsRepository.findById(productId);
    }

    @Override
    public MerchantInfo addMerchantDetails(MerchantInfo merchantInfo) {
        return merchantInfoRepository.save(merchantInfo);
    }

    @Override
    public void reduceQuantity(String productId) {
        Optional product = productsRepository.findById(productId);
        if(product.isPresent()) {
            int quantity = productsRepository.findById(productId).get().getProductQuantity();
            System.out.println(quantity);
            quantity = quantity-1;
            System.out.println(quantity);
            Optional temp = productsRepository.findById(productId);

            if(temp.isPresent()){
                Products temp1 = productsRepository.findById(productId).get();
                System.out.println(temp1);
                Products p = new Products();
                BeanUtils.copyProperties(temp1,p);
                p.setProductQuantity(quantity);
                System.out.println(p);
                productsRepository.save(p);
            }
//            productsRepository.save(temp);
        }
    }



    //Merchant

    public List searchPidByName(String productName){
        System.out.println(productName);
        List<Products> list = productsRepository.findByProductName(productName);
        System.out.println(list);
//        Collections.sort(list, Comparator.comparing(Products::getPrice));
        List pId = new ArrayList();
        for(int i =0;i<list.size();i++){
            pId.add(list.get(i).getProductId());
        }
        System.out.println(pId);
        return pId;
    }

    @Override
    public String getMerchantIdByProductId(String productId) {
        Optional p = productsRepository.findById(productId);
        String mId;
        if(p.isPresent()){
            mId = productsRepository.findById(productId).get().getMerchantId();
        }else{
            mId = "Not Found";
        }
        return mId;
    }

    @Override
    public Double getPriceByProductId(String productId) {
        Optional p = productsRepository.findById(productId);
        Double price;
        if(p.isPresent()){
            price = productsRepository.findById(productId).get().getPrice();
        }else{
            price = 0.0;
        }
        return price;
    }


//    @Override   // Do Not Use
//    public List<Products> getProductsByProductName(String productName) {
//        ProductDetailInfoDTO productDetailInfoDTO = new ProductDetailInfoDTO();
//
//        Products products = productsRepository.findById(productName).get();
//
//        BeanUtils.copyProperties(products,productDetailInfoDTO);
//        return productsRepository.findByProductName(productName);
//    }

//    @Override
//    public List<Products> getProductsByPrice(Double price) {
//        return productsRepository.findByPrice(price);
//    }

//    @Override
//    public MerchantDTO addMerchant(MerchantDTO merchantDTO) {
//        Aggregation aggregation = Aggregation.newAggregation(Aggregation.lookup("merchantDTO", "merchantId", "_id", "merchant"));
//        mongoTemplate.aggregate(aggregation, "Products", Products.class);
//        return null;
//    }


}