package com.example.Lab2.Locations.Controllers;

import com.example.Lab2.Locations.Models.LocationDTO;
import com.example.Lab2.Locations.Models.LocationRequest;
import com.example.Lab2.Response.Response;
import com.example.Lab2.Locations.Service.LocationService;

import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.*;

import java.util.List;

@RestController(value = "RestController")
public class LocationController {

    private final static Logger logger = LogManager.getLogger(LocationController.class);
    @PostMapping(value = "/getLocations")
    public static List<LocationDTO> getListLocations() {
        return LocationService.getListLocations();
    }

    @PostMapping(value = "/findLocations")
    public static ResponseEntity<Response>  findLocations(@RequestBody @Valid LocationRequest input, BindingResult bindingResult) throws IOException {

        Response locationResponse = new Response();
        if(bindingResult.hasErrors()){

            logger.error("Test find locations: "+bindingResult.getAllErrors().get(0).getDefaultMessage());
            locationResponse.setStatus("400");
            locationResponse.setMessage("ERROR: Test find locations: "+bindingResult.getAllErrors().get(0).getDefaultMessage());
            return ResponseEntity.badRequest().body(locationResponse);
        }
        locationResponse.setStatus("200");
        locationResponse.setResult(LocationService.findLocations(input));
        locationResponse.setMessage("Success");
        return  ResponseEntity.ok(locationResponse);
    }

    @PostMapping(value = "/exportJsonLocations")
    public static ResponseEntity<Response> exportJsonLocations(@RequestBody @Valid LocationRequest input, BindingResult bindingResult) throws IOException {
        //return  LocationService.exportJsonLocations(input);
        Response locationResponse = new Response();
        if(bindingResult.hasErrors()){

            logger.error("Test find locations: "+bindingResult.getAllErrors().get(0).getDefaultMessage());
            locationResponse.setStatus("400");
            locationResponse.setMessage("ERROR: Export json locations: "+bindingResult.getAllErrors().get(0).getDefaultMessage());
            return ResponseEntity.badRequest().body(locationResponse);
        }
        locationResponse.setStatus("200");
        locationResponse.setResult(LocationService.exportJsonLocations(input));
        locationResponse.setMessage("Success");
        return  ResponseEntity.ok(locationResponse);
    }
    @PostMapping(value = "/exportExcelLocations")
    public static ResponseEntity<Response> exportExcelLocations(@RequestBody @Valid LocationRequest input, BindingResult bindingResult) throws IOException {
        //return LocationService.exportExcelLocations(input);
        Response locationResponse = new Response();
        if(bindingResult.hasErrors()){

            logger.error("Test find locations: "+bindingResult.getAllErrors().get(0).getDefaultMessage());
            locationResponse.setStatus("400");
            locationResponse.setMessage("ERROR: Export json locations: "+bindingResult.getAllErrors().get(0).getDefaultMessage());
            return ResponseEntity.badRequest().body(locationResponse);
        }
        locationResponse.setStatus("200");
        locationResponse.setResult(LocationService.exportExcelLocations(input));
        locationResponse.setMessage("Success");
        return  ResponseEntity.ok(locationResponse);
    }

    //@RequestMapping(path = "/testfindlocations")
//    @PostMapping(value = "/testfindlocations")
//    public static ResponseEntity<Response> test(@RequestBody @Valid LocationRequest request, BindingResult bindingResult) throws Exception {
//        Response locationResponse = new Response();
//        if(bindingResult.hasErrors()){
//
//            logger.error("Test find locations: "+bindingResult.getAllErrors().get(0).getDefaultMessage());
//            locationResponse.setStatus("400");
//            locationResponse.setMessage("ERROR: Test find locations: "+bindingResult.getAllErrors().get(0).getDefaultMessage());
//            return ResponseEntity.badRequest().body(locationResponse);
//        }
//        //TODO: Thêm code gọi xuống service layer
//        //return (ResponseEntity<List<LocationDTO>>) LocationService.testfindLocations(request);
//        locationResponse.setStatus("200");
//        locationResponse.setResult(LocationService.testfindLocations(request));
//        locationResponse.setMessage("Success");
//        return  ResponseEntity.ok(locationResponse);
//    }
}
