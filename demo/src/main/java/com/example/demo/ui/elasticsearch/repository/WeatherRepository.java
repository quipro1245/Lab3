package com.example.demo.ui.elasticsearch.repository;

import com.example.demo.ui.elasticsearch.model.WeatherElasticsearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface WeatherRepository extends ElasticsearchRepository<WeatherElasticsearch, String> {


    Page<WeatherElasticsearch> findAll(Pageable pageable);
    List<WeatherElasticsearch> findByTime(String time);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"location_id\": \"?0\"}},{\"range\" : {\"time\" : {\"gte\" : \"?1\",\"lte\" : \"?2\"}}}]}")
    Page<WeatherElasticsearch> findRequest(String locationId, String startDate, String endDate, Pageable pageable);
}
