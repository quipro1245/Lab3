package com.example.demo.ui.elasticsearch.controller;


import com.example.demo.ui.elasticsearch.model.WeatherElasticsearch;
import com.example.demo.ui.elasticsearch.service.ElasticsearchService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ElasticsearchController {

    @Autowired
    ElasticsearchService elasticSearchService;

    @GetMapping("/elasticsearch/findAll")
    public Page<WeatherElasticsearch> findAll(){
        return elasticSearchService.findAll();
    }

    @GetMapping("/elasticsearch/findByTime")
    public List<WeatherElasticsearch> findByTime(){
        return elasticSearchService.findByTime();
    }
    @GetMapping("/elasticsearch/findByRequest")
    public Page<WeatherElasticsearch> findByRequest(){
        return elasticSearchService.findByRequest();
    }

}
