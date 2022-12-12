package com.example.Lab2.Weathers.Controllers;


import com.example.Lab2.Locations.Controllers.LocationController;
import com.example.Lab2.Response.Response;
import com.example.Lab2.Weathers.Models.WeatherDTO;
import com.example.Lab2.Weathers.Models.WeatherRequest;

import com.example.Lab2.Weathers.Service.WeatherService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WeatherController {

    private final static Logger logger = LogManager.getLogger(LocationController.class);
    @PostMapping(value = "/postWeather")
    public static List<WeatherDTO> postListWeather( @RequestBody String date) {
        return WeatherService.getListWeather(date);
    }
    @PostMapping(value = "/findWeatherFollowDate")
    public static ResponseEntity<Response> findWeatherFollowDate(@RequestBody @Valid WeatherRequest input, BindingResult bindingResult) {
        Response weatherResponse = new Response();
        if(bindingResult.hasErrors()){

            logger.error("Test find locations: "+bindingResult.getAllErrors().get(0).getDefaultMessage());
            weatherResponse.setStatus("400");
            ArrayList message = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                message.add(error.getDefaultMessage());
            }
            weatherResponse.setMessage("ERROR: Test find locations: "+message);
            return ResponseEntity.badRequest().body(weatherResponse);
        }

        //return WeatherService.findWeatherFollowDate(input);
        weatherResponse.setStatus("200");
        weatherResponse.setResult(WeatherService.findWeatherFollowDate(input));
        weatherResponse.setMessage("Success");
        return  ResponseEntity.ok(weatherResponse);
    }
    @PostMapping(value = "/exportJsonWeather")
    public static ResponseEntity<Response> exportJsonWeather(@RequestBody @Valid WeatherRequest input, BindingResult bindingResult) {
        Response weatherResponse = new Response();
        if(bindingResult.hasErrors()){

            logger.error("Test find locations: "+bindingResult.getAllErrors().get(0).getDefaultMessage());
            weatherResponse.setStatus("400");
            weatherResponse.setMessage("ERROR: Test find locations: "+bindingResult.getAllErrors().get(0).getDefaultMessage());
            return ResponseEntity.badRequest().body(weatherResponse);
        }
        //return WeatherService.exportJsonWeather(input);

        weatherResponse.setStatus("200");
        weatherResponse.setResult(WeatherService.exportJsonWeather(input));
        weatherResponse.setMessage("Success");
        return  ResponseEntity.ok(weatherResponse);
    }

    @PostMapping(value = "/exportExcelWeather")
    public static ResponseEntity<Response> exportExcelWeather(@RequestBody @Valid WeatherRequest input, BindingResult bindingResult) throws IOException {
        Response weatherResponse = new Response();
        if(bindingResult.hasErrors()){

            logger.error("Test find locations: "+bindingResult.getAllErrors().get(0).getDefaultMessage());
            weatherResponse.setStatus("400");
            weatherResponse.setMessage("ERROR: Test find locations: "+bindingResult.getAllErrors().get(0).getDefaultMessage());
            return ResponseEntity.badRequest().body(weatherResponse);
        }
        //return  WeatherService.exportExcelWeather(input);
        weatherResponse.setStatus("200");
        weatherResponse.setResult(WeatherService.exportExcelWeather(input));
        weatherResponse.setMessage("Success");

        return  ResponseEntity.ok(weatherResponse);
    }
}
