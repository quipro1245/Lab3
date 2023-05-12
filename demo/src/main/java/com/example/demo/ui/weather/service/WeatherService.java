package com.example.demo.ui.weather.service;

import com.example.demo.ui.location.model.LocationDTO;
import com.example.demo.ui.location.model.Response;
import com.example.demo.ui.weather.model.ViewWeather;
import com.example.demo.ui.weather.model.WeatherRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WeatherService {
    private final static Logger logger = LogManager.getLogger(WeatherService.class);

    public static Response findWeatherFollowRequestPaging(String url, WeatherRequest input, HttpSession session, ViewWeather viewWeather) {
        Response result = new Response();
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
            RestTemplate restTemplate = new RestTemplate();

            if ("manageRed".equalsIgnoreCase(session.getAttribute("permission").toString())) {
                String body = "{\n" +
                        "\"input\": \"" + viewWeather.getViewWeatherManageRed() + "\"\n" +
                        "}";
                ResponseEntity<Response> getNameViewWeatherManageBlue = restTemplate.exchange(url + "/findLocationByName", HttpMethod.POST, new HttpEntity<>(body, headers), Response.class);
                ObjectMapper mapper = new ObjectMapper();
                List<LocationDTO> location = List.of(mapper.convertValue(getNameViewWeatherManageBlue.getBody().getResult(), LocationDTO[].class));

//                LocationDTO locationDTO = location.get(0);
//                System.out.println(locationDTO);
//                System.out.println(locationDTO.getId());
                input.setLocationID(location.get(0).getId());
            }
            if ("manageBlue".equalsIgnoreCase(session.getAttribute("permission").toString())) {

                String body = "{\n" +
                        "\"input\": \"" + viewWeather.getViewWeatherManageBlue() + "\"\n" +
                        "}";
                ResponseEntity<Response> getNameViewWeatherManageBlue = restTemplate.exchange(url + "/findLocationByName", HttpMethod.POST, new HttpEntity<>(body, headers), Response.class);
                ObjectMapper mapper = new ObjectMapper();
                List<LocationDTO> listLocation = List.of(mapper.convertValue(getNameViewWeatherManageBlue.getBody().getResult(), LocationDTO[].class));
                int locationID = 0;
                if (!ObjectUtils.isEmpty(input.getLocationID())) {
                    for (LocationDTO locationDTO : listLocation) {
                        if (input.getLocationID().equalsIgnoreCase(locationDTO.getId())) {
                            locationID++;
                            break;
                        }
                    }

                }
                if (ObjectUtils.isEmpty(input.getLocationID()) || locationID == 0) {
                    result.setResult(new ArrayList<>());
                    return  result;
//                LocationDTO locationDTO = location.get(0);
//                System.out.println(locationDTO);
//                System.out.println(locationDTO.getId());
                }
            }
            String body = "{\n" +
                    "    \"datetime_range\": \"" + startDate + " - " + endDate + "\",\n" +
                    "    \"location_id\": \"" + input.getLocationID() + "\",\n" +
                    "    \"page\": \"1\",\n" +
                    "    \"limit\": \"5\"\n" +
                    "}";

            ResponseEntity<Response> response = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(body, headers), Response.class);
            result = response.getBody();
        } catch (Exception e) {
            logger.error("Find weather follow request paging: error: " + e);
            logger.error(String.format("Find weather follow request paging, url: %s, startDate: %s, endDate: %s, locationID: %s", uri, input.getStartDate(), input.getEndDate(), input.getLocationID()));
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
        } catch (Exception e) {
            logger.error("Export and download json weather, error: " + e);
            logger.error(String.format("Find weather follow request paging, url: %s, startDate: %s, endDate: %s, locationID: %s", uri, weatherRequest.getStartDate(), weatherRequest.getEndDate(), weatherRequest.getLocationID()));
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
        } catch (Exception e) {
            logger.error("Export and download json weather, error: " + e);
            logger.error(String.format("Find weather follow request paging, url: %s, startDate: %s, endDate: %s, locationID: %s", uri, weatherRequest.getStartDate(), weatherRequest.getEndDate(), weatherRequest.getLocationID()));
        }
        return file;
    }

    public static String importFileExcelWeather(String url, MultipartFile file) {
        String uri = url + "/importFileExcelWeather";
        String result = "";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            String fileName = file.getOriginalFilename();
            MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
            Resource resource = file.getResource();
            ContentDisposition contentDisposition = ContentDisposition
                    .builder("form-data")
                    .name("file")
                    .filename(fileName)
                    .build();
            fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
            HttpEntity<byte[]> fileEntity = new HttpEntity<>(resource.getInputStream().readAllBytes(), fileMap);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileEntity);
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Response> response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, Response.class);
            result = response.getBody().getStatus();
        } catch (Exception e) {
            logger.error("Weather service import file excel weather, error: " + e);
            logger.error(String.format("Weather service import file excel weather, uri: %s ", uri));
        }
        return result;
    }
}
