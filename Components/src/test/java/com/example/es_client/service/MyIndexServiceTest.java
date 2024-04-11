package com.example.es_client.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.example.es_client.entity.MyIndex;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
@Slf4j
class MyIndexServiceTest {
    @Autowired
    private ElasticsearchClient esClient;

    @Test
    public void createIndex() throws Exception{
        esClient.indices().create(c -> c
                .index("my_index")
        );

    }

    @Test
    public void index() throws Exception{
        MyIndex myIndex = new MyIndex();
        myIndex.setName("肖旺");
        myIndex.setAge("30");
        myIndex.setSkill("云顶,lol");
        myIndex.setAddress("湖南长沙");

        IndexResponse response = esClient.index(i -> i
                .index("my_index")
                .document(myIndex)
        );

        log.info("Indexed with version " + response.version());
    }
}