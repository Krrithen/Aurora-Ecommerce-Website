package com.example.Search.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories(
        basePackages = "com.example.Search.repository",
        namedQueriesLocation = "classpath:application.properties")
@ComponentScan
public class SolrConfig {
    @Bean
    public SolrClient solrClient() {  // to create Solr Client to access the Solr Database
        HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/search").build();
        solr.setParser(new XMLResponseParser());
        return solr;
    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient client) throws Exception {
        return new SolrTemplate(client);
    }
}
