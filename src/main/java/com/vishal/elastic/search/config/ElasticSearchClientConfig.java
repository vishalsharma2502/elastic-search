package com.vishal.elastic.search.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.io.IOException;

@Configuration
@EnableElasticsearchRepositories
@Slf4j
public class ElasticSearchClientConfig extends AbstractElasticsearchConfiguration {

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo("localhost:9200").build();
        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public Boolean createTestIndex(RestHighLevelClient restHighLevelClient) throws IOException {
        try{
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("hello-world");
            restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        } catch(Exception ex){
            log.error("Error in deleting hello-world index");
        }
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("hello-world");
        createIndexRequest.settings(Settings.builder().put("index.number_of_shards", 1)
        .put("index.number_of_replicas", 0));
        restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        return true;
    }
}
