package com.example.Merchants.controller;

import com.example.Merchants.entities.Merchants;
import com.example.Merchants.services.MerchantsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@Slf4j
@RequestMapping("/merchants")
public class MerchantsController {
    @Autowired
    MerchantsService merchantsService;

    @PostMapping("/addMerchants")
    public ResponseEntity<Merchants> addMerchantsDetails(@RequestBody Merchants merchants) {
        merchantsService.addMerchantDetails(merchants);
        return new ResponseEntity<Merchants>(merchants, HttpStatus.OK);
    }

    @GetMapping("/getMerchantsById/{merchantId}")
    public ResponseEntity<Object> getMerchantsById(@PathVariable("merchantId") String merchantId){
        Optional<Merchants> merchantsList=merchantsService.getMerchantById(merchantId);
        if(merchantsList.isPresent()){
            return new ResponseEntity<Object>(merchantsList,HttpStatus.OK);
        }
        return new ResponseEntity<Object>(null,HttpStatus.OK);
    }



//    @GetMapping("/GetMerchantsByProductId/{productId}")
//    public ResponseEntity<List<Merchants>> getMerchantsByProductId(@PathVariable("productId") String productId) {
//        List<Merchants> merchantsList = merchantsService.getMerchantsByProductId(productId);
//        return new ResponseEntity<>(merchantsList, HttpStatus.OK);
//    }
}
