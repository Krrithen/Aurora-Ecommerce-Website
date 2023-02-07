package com.example.ecomProject.controller;

import com.example.ecomProject.dto.*;
import com.example.ecomProject.entities.MerchantInfo;
import com.example.ecomProject.entities.Products;
import com.example.ecomProject.feignclients.FeignMerchantService;
import com.example.ecomProject.feignclients.FeignSearchService;
import com.example.ecomProject.repository.MerchantInfoRepository;
import com.example.ecomProject.services.ProductsService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/products")
@JsonIgnoreProperties
public class ProductsController {

    @Autowired
    ProductsService productsService;

    @Autowired
    FeignMerchantService feignMerchantService;

    @Autowired
    FeignSearchService feignSearchService;

    @Autowired
    MerchantInfoRepository merchantInfoRepository;

    @PostMapping("/addProducts/{}")
    public ResponseEntity<String> addProductDetails(@RequestBody ProductsDTO productsDTO)
    {
        ResponseEntity<Object> response =feignMerchantService.getMerchantsById(productsDTO.getMerchantId());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MerchantDTO merchantDTO = mapper.convertValue(response.getBody(), MerchantDTO.class);
        System.out.println(merchantDTO);
        Products  productDetails = new Products();
        MerchantInfo merchantDetails = new MerchantInfo();
        BeanUtils.copyProperties(productsDTO,productDetails);
        BeanUtils.copyProperties(productsDTO,merchantDetails);
        BeanUtils.copyProperties(merchantDTO,merchantDetails);
        productsService.addProductDetails(productDetails);
        productsService.addMerchantDetails(merchantDetails);
        ProductSearchDTO productSearchDTO= new ProductSearchDTO();
        productSearchDTO.setProductId(productsDTO.getProductId());
        productSearchDTO.setProductName(productsDTO.getProductName());
//        productSearchDTO.setPrice(productsDTO.getPrice());
//        productSearchDTO.setImage(productsDTO.getImage());
//        productSearchDTO.setDescription(productsDTO.getDescription());
//        productSearchDTO.setCategory(productsDTO.getCategory());

        feignSearchService.addProductDetails(productSearchDTO);
        return new ResponseEntity<>("Products Added With Respect To Merchant", HttpStatus.OK);
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") String productId) {
        productsService.deleteProduct(productId);
        return new ResponseEntity<>("Product Deleted", HttpStatus.OK);
    }

    @GetMapping("/GetAllProducts")
    public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> productList = productsService.allProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/GetProductsByProductId/{productId}")
    public ResponseEntity<Object> getProductsByProductId(@PathVariable("productId") String productId) {
        Optional<Products> productIdList = productsService.getProductsByProductId(productId);
        if(productIdList.isPresent()){
            System.out.println(productIdList);
            return new ResponseEntity<>(productIdList, HttpStatus.OK);
        }
        return new ResponseEntity<Object>(null,HttpStatus.OK);
    }

    @GetMapping("/GetProductsByCategory/{category}")
    public ResponseEntity<List<Products>> getProductsByCategory(@PathVariable("category") String category) {
        List<Products> categoryList = productsService.getProductsByCategory(category);
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }


    @GetMapping("/GetProductsBySearch/{productName}")
    public ResponseEntity<List<Products>> getProductsBySearch(@PathVariable("productName") String productName) {
        Object response =feignSearchService.getSearchByName(productName).getBody();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<SolrDTO> solrDTO = mapper.convertValue(response, new TypeReference<List<SolrDTO>>(){});
        List<Products> list= new ArrayList<Products>();
        for(int i=0;i<solrDTO.size();i++){
            System.out.println("check");
            Optional<Products> product=productsService.getProductsByProductId(solrDTO.get(i).getId());
            if(product.isPresent()){
                System.out.println(product.get());
                Products  productDetails = new Products();
                BeanUtils.copyProperties(product.get(),productDetails);
                list.add(productDetails);
            }
        }
        System.out.println("check");
        System.out.println(list);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/reduceQuantity/{productId}")
    public ResponseEntity<String> reduceQuantity(@PathVariable("productId") String productId)
    {
        productsService.reduceQuantity(productId);
        return new ResponseEntity<>("Quantity Reduced By 1", HttpStatus.OK);
    }


    @GetMapping("/GetPriceByProductId/{productId}")
    public ResponseEntity<Double> getPriceByProductId(@PathVariable("productId") String productId) {
        Double price = productsService.getPriceByProductId(productId);
        return new ResponseEntity<>(price, HttpStatus.OK);
    }


    @GetMapping("/SearchMidByName/{productName}")
    public ResponseEntity<List<MerchantSearchDTO>> searchMidByName(@PathVariable("productName") String productName) {

//        List mids = productsService.searchMidByName(productName);
//        List merchantNames = new ArrayList();
//        for(int i=0;i<mids.size();i++){
//            merchantNames.add(feignMerchantService.getMerchantsById(mids.get(i).toString()).getBody());
//        }
        List pids = productsService.searchPidByName(productName);
        System.out.println(pids);
        List<MerchantSearchDTO> msDTO = new ArrayList<>();
        for(int i=0;i<pids.size();i++){
            MerchantSearchDTO merchantSearchDTO = new MerchantSearchDTO();
            merchantSearchDTO.setProductId(pids.get(i).toString());
            String mId = productsService.getMerchantIdByProductId(pids.get(i).toString());
            merchantSearchDTO.setMerchantId(mId);
            merchantSearchDTO.setPrice(productsService.getPriceByProductId(pids.get(i).toString()));
            Optional merName = merchantInfoRepository.findById(mId);
            String name="";
            if(merName.isPresent()){
                name = merchantInfoRepository.findById(mId).get().getMerchantName();
            }else{
                name="Not Found";
            }
            merchantSearchDTO.setMerchantName(name);
            msDTO.add(merchantSearchDTO);
        }
        System.out.println(msDTO);
        return new ResponseEntity<>(msDTO, HttpStatus.OK);
    }




//    @GetMapping("/GetProductsByName/{productName}")
//    public ResponseEntity<List<Products>> getProductsByProductName(@PathVariable("productName") String productName) {
//        List<Products> productNameList = productsService.getProductsByProductName(productName);
//        return new ResponseEntity<>(productNameList, HttpStatus.OK);
//    }
//
//    @GetMapping("/GetProductsByPrice/{price}")
//    public ResponseEntity<List<Products>> getProductsByPrice(@PathVariable("price") Double price) {
//        List<Products> priceList = productsService.getProductsByPrice(price);
//        return new ResponseEntity<>(priceList, HttpStatus.OK);
//    }




}