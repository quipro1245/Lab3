package com.example.lab2.locations.controllers;

import com.example.lab2.controller.MongoConfig;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import com.example.lab2.locations.models.LocationRequest;
import com.example.lab2.locations.service.LocationService;
import com.example.lab2.response.Response;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.*;

import static com.example.lab2.locations.service.LocationService.exportDownloadJsonLocations;

@RestController
public class LocationController {
    private final static Logger logger = LogManager.getLogger(LocationController.class);
    @Autowired
    MongoConfig mongoConfig;

//    @PostMapping(value = "/getLocations")
//    public List<LocationDTO> getListLocations() {
//        return LocationService.getListLocations(mongoConfig.getUrl(), mongoConfig.getDb());
//    }


    @PostMapping(value = "/findLocations")
    public ResponseEntity<Response> findLocations(@RequestBody @Valid LocationRequest input, BindingResult bindingResult) throws IOException {

        Response locationResponse = new Response();
        if (bindingResult.hasErrors()) {

            logger.error("Find locations: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
            locationResponse.setStatus("400");
            locationResponse.setMessage("ERROR: find locations: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
            return ResponseEntity.badRequest().body(locationResponse);
        }
        locationResponse.setStatus("200");
        locationResponse.setResult(LocationService.findLocations(input, mongoConfig.getUrl(), mongoConfig.getDb()));
        locationResponse.setMessage("Success");
        return ResponseEntity.ok(locationResponse);
    }

    @PostMapping(value = "/exportJsonLocations")
    public ResponseEntity<Response> exportJsonLocations(@RequestBody @Valid LocationRequest input, BindingResult bindingResult) throws IOException {
        //return  LocationService.exportJsonLocations(input);
        Response locationResponse = new Response();
        if (bindingResult.hasErrors()) {
            logger.error("Export json locations: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
            locationResponse.setStatus("400");
            locationResponse.setMessage("ERROR: Export json locations: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
            return ResponseEntity.badRequest().body(locationResponse);
        }
        locationResponse.setStatus("200");
        locationResponse.setResult(LocationService.exportJsonLocations(input, mongoConfig.getUrl(), mongoConfig.getDb()));
        locationResponse.setMessage("Success");
        return ResponseEntity.ok(locationResponse);
    }

    @PostMapping(value = "/exportExcelLocations")
    public ResponseEntity<Response> exportExcelLocations(@RequestBody @Valid LocationRequest input, BindingResult bindingResult) throws IOException {
        //return LocationService.exportExcelLocations(input);
        Response locationResponse = new Response();
        if (bindingResult.hasErrors()) {
            logger.error("Export excel locations: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
            locationResponse.setStatus("400");
            locationResponse.setMessage("ERROR: Export excel locations: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
            return ResponseEntity.badRequest().body(locationResponse);
        }
        locationResponse.setStatus("200");
        locationResponse.setResult(LocationService.exportExcelLocations(input, mongoConfig.getUrl(), mongoConfig.getDb()));
        locationResponse.setMessage("Success");
        return ResponseEntity.ok(locationResponse);
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

//    @PostMapping(value = "/testGetListLocations")
//    public ResponseEntity<Response> testGetListLocations(@RequestBody @Valid LocationRequest input, BindingResult bindingResult) throws IOException {
//
//        Response locationResponse = new Response();
//        if (bindingResult.hasErrors()) {
//
//            logger.error("testGetListLocations: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
//            locationResponse.setStatus("400");
//            locationResponse.setMessage("ERROR: testGetListLocations: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
//            return ResponseEntity.badRequest().body(locationResponse);
//        }
//        locationResponse.setStatus("200");
//        locationResponse.setResult(LocationService.testGetListLocations(input, mongoConfig.getUrl(), mongoConfig.getDb()));
//        locationResponse.setMessage("Success");
//        return ResponseEntity.ok(locationResponse);
//    }
    @GetMapping(value = "/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam("name") String fileName,
                                                 HttpServletRequest request) throws FileNotFoundException {
        Resource resource = null;
        File file = new File("./export/"+fileName);
        InputStream  stream = new FileInputStream(file);
        resource = new InputStreamResource(stream);
        //resource =new UrlResource(filePath.toUri());
        if (fileName != null && !fileName.isEmpty()) {

            // Try to determine file's content type
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                //logger.info("Could not determine file type.");
            }
            // Fallback to the default content type if type could not be determined
            if (contentType == null) {
                contentType = "application/json";
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(resource);
        } else {

            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping(value = "/exportAndDownloadJsonLocations")
    public ResponseEntity<Resource> exportAndDownloadJsonLocations(@RequestBody @Valid LocationRequest input, BindingResult bindingResult,HttpServletRequest request) throws IOException {
        //return  LocationService.exportJsonLocations(input);
        String fileName = exportDownloadJsonLocations(input, mongoConfig.getUrl(), mongoConfig.getDb());

        File file = new File(fileName);
        InputStream  stream = new FileInputStream(file);
        Resource resource = new InputStreamResource(stream);
        String contentType = null;
        if (contentType == null) {
            contentType = "application/json";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);

    }
}
