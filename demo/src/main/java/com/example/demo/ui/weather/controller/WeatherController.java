package com.example.demo.ui.weather.controller;

import com.example.demo.ui.location.model.Response;
import com.example.demo.ui.user.model.BankEndConfig;
import com.example.demo.ui.weather.model.ViewWeather;
import com.example.demo.ui.weather.model.WeatherDTO;
import com.example.demo.ui.weather.model.WeatherRequest;
import com.example.demo.ui.weather.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@Controller
public class WeatherController {

    @Autowired
    BankEndConfig bankEndConfig;
    @Autowired
    WeatherService weatherService;
    @Autowired
    ViewWeather viewWeather;


    @GetMapping("/weather")
    public String getWeather(HttpSession session) {
        if (session.getAttribute("id") == null)
            return "redirect:/login";
        return "Weather";
    }

    //    @GetMapping("/weatherDetail")
//    public String getWeatherWithID(HttpSession session, WeatherDTO weatherDTO) {
//        if (session.getAttribute("id") == null)
//            return "redirect:/login";
//        return "WeatherDetail";
//    }
//    @PostMapping("/weatherDetail")
//    public String postWeatherWithID(@RequestBody WeatherDTO weatherDTO,HttpSession session) {
//        if (session.getAttribute("id") == null)
//            return "redirect:/login";
//        return getWeatherWithID(session, weatherDTO);
//    }
    @PostMapping("/weather")
    public @ResponseBody String findListUserByRequest(@RequestBody WeatherRequest input, HttpSession session) throws JsonProcessingException {
        String result = "";
        if (session.getAttribute("id") != null) {

            Response response = WeatherService.findWeatherFollowRequestPaging(bankEndConfig.getUrl(), input, session, viewWeather);
            List<WeatherDTO> listWeather = (List<WeatherDTO>) response.getResult();

            ObjectMapper mapper = new ObjectMapper();
            result = mapper.writeValueAsString(listWeather);
        }
        return result;
    }

    @PostMapping(value = "/exportAndDownloadJsonWeather", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputStreamResource> exportAndDownloadJsonWeather(@RequestBody WeatherRequest input, HttpSession session) throws JsonProcessingException, FileNotFoundException {
        File file;
        if (session.getAttribute("id") != null && ("user".equalsIgnoreCase(session.getAttribute("permission").toString()) || "admin".equalsIgnoreCase(session.getAttribute("permission").toString()))) {
            file = WeatherService.exportAndDownloadJsonWeather(bankEndConfig.getUrl(), input);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .contentLength(file.length()).body(new InputStreamResource(new FileInputStream(file)));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/exportAndDownloadExcelWeather", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> exportAndDownloadExcelWeather(@RequestBody WeatherRequest input, HttpSession session) throws JsonProcessingException, FileNotFoundException {
        File file;
        if (session.getAttribute("id") != null && ("user".equalsIgnoreCase(session.getAttribute("permission").toString()) || "admin".equalsIgnoreCase(session.getAttribute("permission").toString()))) {

            file = WeatherService.exportAndDownloadExcelWeather(bankEndConfig.getUrl(), input);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .contentLength(file.length()).body(new InputStreamResource(new FileInputStream(file)));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/importFileExcelWeather")
    public @ResponseBody String importFileExcelWeather(HttpSession session, @RequestParam("file") MultipartFile file) {
        String result = "";
        if (session.getAttribute("id") != null && ("manageImport".equalsIgnoreCase(session.getAttribute("permission").toString()) || "admin".equalsIgnoreCase(session.getAttribute("permission").toString()))) {
            result = WeatherService.importFileExcelWeather(bankEndConfig.getUrl(), file);
        }
        return result;
    }
}
