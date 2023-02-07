package com.example.ecomProject.repository;

import com.example.ecomProject.entities.MerchantInfo;
import com.example.ecomProject.entities.Products;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantInfoRepository extends MongoRepository<MerchantInfo, String> {
}
