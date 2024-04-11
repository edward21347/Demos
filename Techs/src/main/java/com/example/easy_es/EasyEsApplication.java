package com.example.easy_es;

import cn.easyes.starter.register.EsMapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EsMapperScan("com.example.easy_es.esmapper")
public class EasyEsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyEsApplication.class, args);
    }

}
