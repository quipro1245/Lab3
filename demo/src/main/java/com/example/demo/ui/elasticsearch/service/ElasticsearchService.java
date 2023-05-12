package com.example.demo.ui.elasticsearch.service;

import com.example.demo.ui.elasticsearch.model.WeatherElasticsearch;
import com.example.demo.ui.elasticsearch.repository.WeatherRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElasticsearchService {

    @Autowired
    private WeatherRepository weatherRepository;

    public Page<WeatherElasticsearch> findAll(){
        return weatherRepository.findAll(PageRequest.of(1, 20));
    }

    public List<WeatherElasticsearch> findByTime(){
        return weatherRepository.findByTime("2022-09-01 10:00");
    }

    public Page<WeatherElasticsearch> findByRequest(){
        return weatherRepository.findRequest("2801268","2022-09-01 10:00","2022-09-02 12:00",PageRequest.of(1, 10));
    }
}
