package com.example.demo.ui.user.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {


    public static PageUser getUser(String url, PageUser page) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String body = "{\n" +
                "    \"limit\":5,\n" +
                "    \"page\":0\n" +
                "}";
        RestTemplate restTemplate = new RestTemplate();
        //List<UserDTO> result = List.of(restTemplate.getForObject(uri, UserDTO[].class));
        ResponseEntity<PageUser> result = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers), PageUser.class);
        //model.addAttribute("listUser", result);
        return result.getBody();
    }

    public static PageUser findUserFollowRequest(String url, UserRequest userRequest) {
        String uri = url + "/findPageFindListUserByRequest";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String body = "{\n" +
                "    \"name\": \"" + userRequest.getInput() + "\",\n" +
                "    \"email\":\"" + userRequest.getInput() + "\",\n" +
                "    \"limit\":5,\n" +
                "    \"page\":" + userRequest.getPage() + "\n" +
                "}";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PageUser> result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(body, headers), PageUser.class);
        return result.getBody();
    }

    public static String convertByteToHex(byte[] data) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            sb.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String getSHAHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes());
            return convertByteToHex(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static UserDTO login(String url, UserDTO user) {
        String uri = url + "/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String body = "{\n" +
                "    \"username\": \"" + user.getUsername() + "\",\n" +
                "    \"password\":\"" + getSHAHash(user.getPassword()) + "\"\n" +
                "}";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserDTO[]> result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(body, headers), UserDTO[].class);
        if (result.getBody() != null) {
            List<UserDTO> list = List.of(result.getBody());
            return list.get(0);
        }
        return null;
    }

    public static ResponseAddUser addUser(String url, UserDTO user) throws JsonProcessingException {
        String uri = url + "/";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        ObjectMapper objectMapper = new ObjectMapper();
        user.setPassword(getSHAHash(user.getPassword()));

        String body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResponseAddUser> result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(body, headers), ResponseAddUser.class);
        if (result.getBody() == null)
            return null;
        return result.getBody();
    }

    public static boolean findByUsername(String url, UserDTO user) {
        String uri = url + "/findByUsername";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String body = "{\n" +
                "    \"username\": \"" + user.getUsername() + "\"\n" +
                "}";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserDTO[]> result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(body, headers), UserDTO[].class);
        return result.getBody().length == 0;
    }


}
