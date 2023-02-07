package com.example.ecomProject.feignclients;

import com.example.ecomProject.dto.ProductSearchDTO;
import com.example.ecomProject.dto.ProductsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "feignSearch",url="http://10.20.5.49:8085")
public interface FeignSearchService {

    @GetMapping("/search/query/{productName}")
    public ResponseEntity<Object> getSearchByName(@PathVariable("productName") String productName);

    @PostMapping("/search/addProducts")
    public String addProductDetails(@RequestBody ProductSearchDTO productSearchDTO);
}