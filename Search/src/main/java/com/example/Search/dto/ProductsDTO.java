package com.example.Search.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.stereotype.Component;

@Data
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
@SolrDocument(solrCoreName = "searchProducts")
@Component
public class ProductsDTO {

    @Id
    @Indexed(name = "id", type = "string")
    private String productId;
    @Indexed(name = "name", type = "string")
    private String productName;
//    @Indexed(name = "description", type = "string")
//    private String description;
//    @Indexed(name = "price", type = "double")
//    private Double price;
//    @Indexed(name = "image", type = "string")
//    private String image;
//    @Indexed(name = "category", type = "string")
//    private String category;
}

