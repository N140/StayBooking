package com.example.staybooking.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class ElasticClient extends AbstractElasticsearchConfiguration {
    @Value("${elasticsearch.address}")
    private String elasticSearchAddress;
    @Value("elasticsearch.username")
    private String elasticSearchUsername;
    @Value("elasticsearch.password")
    private String elasicSearchPw;
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(elasticSearchAddress)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
