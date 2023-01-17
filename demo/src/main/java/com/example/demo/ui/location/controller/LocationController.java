package com.example.demo.ui.location.controller;

import com.example.demo.ui.location.model.LocationRequest;
import com.example.demo.ui.location.model.Response;
import com.example.demo.ui.location.service.LocationService;
import com.example.demo.ui.user.model.BankEndConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Controller
public class LocationController {

    @Autowired
    BankEndConfig bankEndConfig;
    @Autowired
    LocationService locationService;

    @GetMapping("/location")
    public String getLocation(HttpSession session) {
        if (session.getAttribute("id") == null)
            return "redirect:/login";
        return "Location";
    }

    @PostMapping("/location")
    public @ResponseBody String findListUserByRequest(@RequestBody LocationRequest locationRequest, HttpSession session) throws JsonProcessingException {
        String result = "";
        if (session.getAttribute("id") != null) {

            Response response = LocationService.findLocations(bankEndConfig.getUrl(), locationRequest.getInput());
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.writeValueAsString(response.getResult());
        }
        return result;
    }

    @PostMapping(value = "/exportAndDownloadJsonLocations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputStreamResource> exportAndDownloadJsonLocations(@RequestBody LocationRequest locationRequest, HttpSession session) throws JsonProcessingException, FileNotFoundException {
        File result;
        if (session.getAttribute("id") != null) {

            result = LocationService.exportAndDownloadJsonLocations(bankEndConfig.getUrl(), locationRequest);
            return ResponseEntity.ok()
//                   .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + result.getName() + "\"")
                    .contentLength(result.length()).body(new InputStreamResource(new FileInputStream(result)));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/exportAndDownloadExcelLocations", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> exportAndDownloadExcelLocations(@RequestBody LocationRequest locationRequest, HttpSession session) throws JsonProcessingException, FileNotFoundException {
        File result;
        if (session.getAttribute("id") != null) {
//            String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            result = LocationService.exportAndDownloadExcelLocations(bankEndConfig.getUrl(), locationRequest);
            return ResponseEntity.ok()
//                   .contentType(MediaType.parseMediaType(contentType))
                     .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + result.getName() + "\"")
                     .contentLength(result.length()).body(new InputStreamResource(new FileInputStream(result)));
        }

        return ResponseEntity.badRequest().build();
    }
}
