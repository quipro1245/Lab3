package com.example.demo.ui.weather.service;

import com.example.demo.ui.location.model.Response;
import com.example.demo.ui.location.service.LocationService;
import com.example.demo.ui.weather.model.WeatherRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

@Service
public class WeatherService {
    private final static Logger logger = LogManager.getLogger(WeatherService.class);
    public static Response findWeatherFollowRequestPaging(String url, WeatherRequest input) {
        Response result = null;
        String uri = url + "/findWeatherFollowRequestPaging";
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

            LocalDateTime dateTimeStartDate = LocalDateTime.parse(input.getStartDate(), formatter);
            LocalDateTime dateTimeEndDate = LocalDateTime.parse(input.getEndDate(), formatter);
            String startDate = dateTimeStartDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            String endDate = dateTimeEndDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            String body = "{\n" +
                    "    \"datetime_range\": \"" + startDate + " - " + endDate + "\",\n" +
                    "    \"location_id\": \"" + input.getLocationID() + "\",\n" +
                    "    \"page\": \"1\",\n" +
                    "    \"limit\": \"5\"\n" +
                    "}";
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Response> response = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(body, headers), Response.class);
            result = response.getBody();
        } catch (Exception e){
            logger.error("Find weather follow request paging: error: "+e);
            logger.error(String.format("Find weather follow request paging, url: %s, startDate: %s, endDate: %s, locationID: %s",uri,input.getStartDate(),input.getEndDate(),input.getLocationID()));
        }
        return result;
    }
    public static File exportAndDownloadJsonWeather(String url, WeatherRequest weatherRequest) {
        File file = null;
        String uri = url + "/exportAndDownloadJsonWeather";
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

            LocalDateTime dateTimeStartDate = LocalDateTime.parse(weatherRequest.getStartDate(), formatter);
            LocalDateTime dateTimeEndDate = LocalDateTime.parse(weatherRequest.getEndDate(), formatter);
            String startDate = dateTimeStartDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            String endDate = dateTimeEndDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

            weatherRequest.setDatetimeRange(startDate + " - " + endDate);

            ObjectMapper mapper = new ObjectMapper();
            RequestCallback callback = request -> {
                request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                mapper.writeValue(request.getBody(), weatherRequest);
            };
            RestTemplate restTemplate = new RestTemplate();
            //result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(body, headers), ArrayList.class);
            file = restTemplate.execute(uri, HttpMethod.POST, callback, clientHttpResponse -> {
                File ret = File.createTempFile("weather", "tmp.json");
                StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
                return ret;
            });
        } catch (Exception e){
            logger.error("Export and download json weather, error: "+e);
            logger.error(String.format("Find weather follow request paging, url: %s, startDate: %s, endDate: %s, locationID: %s",uri,weatherRequest.getStartDate(),weatherRequest.getEndDate(),weatherRequest.getLocationID()));
        }
        return file;
    }
    public static File exportAndDownloadExcelWeather(String url, WeatherRequest weatherRequest) {
        File file = null;
        String uri = url + "/exportAndDownloadExcelWeather";
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

            LocalDateTime dateTimeStartDate = LocalDateTime.parse(weatherRequest.getStartDate(), formatter);
            LocalDateTime dateTimeEndDate = LocalDateTime.parse(weatherRequest.getEndDate(), formatter);
            String startDate = dateTimeStartDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            String endDate = dateTimeEndDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

            weatherRequest.setDatetimeRange(startDate + " - " + endDate);

            ObjectMapper mapper = new ObjectMapper();
            RequestCallback callback = request -> {
                request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                mapper.writeValue(request.getBody(), weatherRequest);
            };
            RestTemplate restTemplate = new RestTemplate();
            //result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(body, headers), ArrayList.class);
            file = restTemplate.execute(uri, HttpMethod.POST, callback, clientHttpResponse -> {
                File ret = File.createTempFile("weather", "tmp.xlsx");
                StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
                return ret;
            });
        } catch (Exception e){
            logger.error("Export and download json weather, error: "+e);
            logger.error(String.format("Find weather follow request paging, url: %s, startDate: %s, endDate: %s, locationID: %s",uri,weatherRequest.getStartDate(),weatherRequest.getEndDate(),weatherRequest.getLocationID()));
        }
        return file;
    }
}
