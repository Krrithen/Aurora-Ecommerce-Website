package com.example.Search.controller;

import com.example.Search.dto.ProductsDTO;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;



@RestController
@RequestMapping("/search")
public class SearchController {


    @Autowired
    SolrClient solrClient;

    @GetMapping("/query/{productName}")
    public ResponseEntity<SolrDocumentList> searchProducts(@PathVariable("productName") String productName) throws IOException, SolrServerException {
        SolrClient solrClient = new HttpSolrClient.Builder("http://localhost:8983/solr/searchProducts").build();        //create a solrclient for solr core(collection)
        SolrQuery solrQuery = new SolrQuery();          //create solr query

        String pName="";
        for (int i=0;i<productName.length();i++){
            pName+=productName.charAt(i)+"*";
        }
        solrQuery.setQuery("name:*"+pName);       //set query for search on product name
//        solrQuery.setParam("0", productName);
        QueryResponse response = solrClient.query(solrQuery);
//        System.out.println(response);
        Object solrDocument = response.getResults().toArray();
        System.out.println(solrDocument);
        return new ResponseEntity<SolrDocumentList>(response.getResults(),HttpStatus.OK);
    }



    @PostMapping("/addProducts")
    public String addProductDetails(@RequestBody ProductsDTO productsDTO) {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", productsDTO.getProductId());
        doc.addField("name", productsDTO.getProductName());

        try {

            solrClient.add(doc);
            solrClient.commit();
            return "Data added to Solr successfully";

        } catch (SolrServerException | IOException e) {
            return "Failed to add data to Solr: " + e.getMessage();
        }

    }


}