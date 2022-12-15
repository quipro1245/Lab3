package com.example.lab2.weathers.controllers;


import com.example.lab2.controller.MongoConfig;
import com.example.lab2.response.Response;
import com.example.lab2.weathers.models.WeatherRequest;
import com.example.lab2.weathers.service.WeatherService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class WeatherController {

    private final static Logger logger = LogManager.getLogger(WeatherController.class);
    @Autowired
    MongoConfig mongoConfig;

//    @PostMapping(value = "/postWeather")
//    public List<WeatherDTO> postListWeather(@RequestBody String date) {
//        return WeatherService.getListWeather(date);
//    }

//    @PostMapping(value = "/findWeatherFollowDate")
//    public ResponseEntity<Response> findWeatherFollowDate(@RequestBody @Valid WeatherRequest input, BindingResult bindingResult) {
//        Response weatherResponse = new Response();
//        if (bindingResult.hasErrors()) {
//
//            logger.error("Find weather follow date: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
//            weatherResponse.setStatus("400");
//            ArrayList message = new ArrayList<>();
//            for (ObjectError error : bindingResult.getAllErrors()) {
//                message.add(error.getDefaultMessage());
//            }
//            weatherResponse.setMessage("ERROR: find weather: " + message);
//            return ResponseEntity.badRequest().body(weatherResponse);
//        }
//
//        //return WeatherService.findWeatherFollowDate(input);
//        weatherResponse.setStatus("200");
//        weatherResponse.setResult(WeatherService.findWeatherFollowDate(input));
//        weatherResponse.setMessage("Success");
//        return ResponseEntity.ok(weatherResponse);
//    }

    @PostMapping(value = "/exportJsonWeather")
    public ResponseEntity<Response> exportJsonWeather(@RequestBody @Valid WeatherRequest input, BindingResult bindingResult) {
        Response weatherResponse = new Response();
        if (bindingResult.hasErrors()) {

            logger.error("Export json weather: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
            weatherResponse.setStatus("400");
            ArrayList message = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                message.add(error.getDefaultMessage());
            }
            weatherResponse.setMessage("ERROR, export json weather: " + message);
            //weatherResponse.setMessage("ERROR: export json weather: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
            return ResponseEntity.badRequest().body(weatherResponse);
        }
        //return WeatherService.exportJsonWeather(input);

        weatherResponse.setStatus("200");
        weatherResponse.setResult(WeatherService.exportJsonWeather(input, mongoConfig.getUrl(), mongoConfig.getDb()));
        weatherResponse.setMessage("Success");
        return ResponseEntity.ok(weatherResponse);
    }

    @PostMapping(value = "/exportExcelWeather")
    public ResponseEntity<Response> exportExcelWeather(@RequestBody @Valid WeatherRequest input, BindingResult bindingResult) throws IOException {
        Response weatherResponse = new Response();
        if (bindingResult.hasErrors()) {

            logger.error("Export excel weather: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
            weatherResponse.setStatus("400");
            ArrayList message = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                message.add(error.getDefaultMessage());
            }
            weatherResponse.setMessage("ERROR, export excel weather: " + message);
            //weatherResponse.setMessage("ERROR: export excel weather: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
            return ResponseEntity.badRequest().body(weatherResponse);
        }
        //return  WeatherService.exportExcelWeather(input);
        weatherResponse.setStatus("200");
        weatherResponse.setResult(WeatherService.exportExcelWeather(input, mongoConfig.getUrl(), mongoConfig.getDb()));
        weatherResponse.setMessage("Success");

        return ResponseEntity.ok(weatherResponse);
    }
    @PostMapping(value = "/findWeatherFollowRequest")
    public ResponseEntity<Response> findWeatherFollowRequest(@RequestBody @Valid WeatherRequest input, BindingResult bindingResult ) {

        Response weatherResponse = new Response();
        if (bindingResult.hasErrors()) {

            logger.error("Export excel weather: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
            weatherResponse.setStatus("400");
            ArrayList message = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                message.add(error.getDefaultMessage());
            }
            weatherResponse.setMessage("ERROR, find weather follow request: " + message);
            //weatherResponse.setMessage("ERROR: export excel weather: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
            return ResponseEntity.badRequest().body(weatherResponse);
        }
        weatherResponse.setStatus("200");
        weatherResponse.setResult(WeatherService.findWeatherFollowRequest(input, mongoConfig.getUrl(), mongoConfig.getDb()));
        weatherResponse.setMessage("Success");

        return ResponseEntity.ok(weatherResponse);

    }

}
