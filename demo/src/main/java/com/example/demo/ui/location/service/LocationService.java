package com.example.demo.ui.location.service;

import com.example.demo.ui.location.model.*;
import com.example.demo.ui.weather.model.ViewWeather;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;


@Service
public class LocationService {

    private final static Logger logger = LogManager.getLogger(LocationService.class);

    public static Response findLocations(String url, String input) {
        String uri = url + "/findLocations";
        Response response = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            String body = "{\n" +
                        "\"input\": \"" + input + "\"\n" +
                    "}";
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Response> result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(body, headers), Response.class);
            response = result.getBody();
        } catch (Exception e) {
            logger.error("Find location, error: " + e);
            logger.error(String.format("Find location error, uri: %s, input: %s ", uri, input));
        }
        return response;
    }

    public static Response getLocations(String url, HttpSession session, ViewWeather viewWeather) {
        String uri = url + "/getLocations";
        Response response = new Response();

//        if ("manageRed".equalsIgnoreCase(session.getAttribute("permission").toString())) {
//            List<LocationDTO> listLocationDTO = new ArrayList<>();
//            LocationDTO locationWimbledon = new LocationDTO();
//            locationWimbledon.setId("2819887");
//            locationWimbledon.setName("Wimbledon");
//            listLocationDTO.add(locationWimbledon);
//            response.setResult(listLocationDTO);
//            return response;
//        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            RestTemplate restTemplate = new RestTemplate();
            if ("manageBlue".equalsIgnoreCase(session.getAttribute("permission").toString())) {
                String body = "{\n" +
                        "\"input\": \"" + viewWeather.getViewWeatherManageBlue() + "\"\n" +
                        "}";
                ResponseEntity<Response> result = restTemplate.exchange(url+"/findLocationByName", HttpMethod.POST, new HttpEntity<>(body,headers), Response.class);
                return  result.getBody();
            }
            ResponseEntity<Response> result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(headers), Response.class);
            response = result.getBody();
        } catch (Exception e) {
            logger.error("Find location, error: " + e);
            logger.error(String.format("Find location error, uri: %s ", uri));
        }
        return response;
    }
    public static File exportAndDownloadJsonLocations(String url, LocationRequest locationRequest) {
        String uri = url + "/exportAndDownloadJsonLocations";
        File file = null;
        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//            String body = "{\n" +
//                    "    \"input\": \"" + input + "\"\n" +
//                    "}";
            ObjectMapper mapper = new ObjectMapper();
            RequestCallback callback = request -> {
                request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                mapper.writeValue(request.getBody(), locationRequest);
            };
            RestTemplate restTemplate = new RestTemplate();
            file = restTemplate.execute(uri, HttpMethod.POST, callback, clientHttpResponse -> {
                File ret = File.createTempFile("location", "tmp.json");
                StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
                return ret;
            });
            //result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(body, headers), ArrayList.class);
        } catch (Exception e) {
            logger.error("Export and download json location, error: " + e);
            logger.error(String.format("Export and download json location, uri: %s, input: %s ", uri, locationRequest.getInput()));
        }
        return file;
    }

    public static File exportAndDownloadExcelLocations(String url, LocationRequest locationRequest) {
        String uri = url + "/exportAndDownloadExcelLocations";
        File file = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
//            String body = "{\n" +
//                    "\"input\": \"" + locationRequest.getInput() + "\"\n" +
//                "}";
            ObjectMapper mapper = new ObjectMapper();
            RequestCallback callback = request -> {
                request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                mapper.writeValue(request.getBody(), locationRequest);
            };
//            ResponseExtractor<?> extractor = response ->
//                    mapper.readValue(response.getBody(), ArrayList.class);
            RestTemplate restTemplate = new RestTemplate();
            //result = (ResponseEntity<?>) restTemplate.execute(uri, HttpMethod.POST, callback, extractor, 1);
            //result =  restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(body,headers), byte[].class);
            file = restTemplate.execute(uri, HttpMethod.POST, callback, clientHttpResponse -> {
                File ret = File.createTempFile("location", "tmp.xlsx");
                StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
                return ret;
            });


//            RequestCallback requestCallback = restTemplate.httpEntityCallback(new HttpEntity<>(body, headers), Object.class);
//            result = (ResponseEntity<?>) restTemplate.execute(uri, HttpMethod.POST, requestCallback, extractor);


        } catch (Exception e) {
            logger.error("Export and download json location, error: " + e);
            logger.error(String.format("Export and download json location, uri: %s, input: %s ", uri, locationRequest.getInput()));
        }
        return file;
    }
    public static String importFileExcelLocation(String url, MultipartFile file) {
        String uri = url + "/importFileExcelLocation";
        String result = "";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

//            String body = "{\n" +
//                    "\"input\": \"" + locationRequest.getInput() + "\"\n" +
//                "}";
//            ObjectMapper mapper = new ObjectMapper();
//            LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
//            map.add("file", file);
            MultiValueMap<String, Object> body
                    = new LinkedMultiValueMap<>();
            body.add("file", file);
//            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new    HttpEntity<LinkedMultiValueMap<String, Object>>(
//                    map, headers);
            HttpEntity<MultiValueMap<String, Object>> requestEntity
                    = new HttpEntity<>(body, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(uri,  requestEntity, String.class);
            result = response.getBody();
        } catch (Exception e) {
            logger.error("Export and download json location, error: " + e);
            logger.error(String.format("Export and download json location, uri: %s ", uri));
        }
        return result;
    }
}
