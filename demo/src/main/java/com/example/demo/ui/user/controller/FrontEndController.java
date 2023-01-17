package com.example.demo.ui.user.controller;

import com.example.demo.ui.user.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FrontEndController {


    @Autowired
    UserService userService;
    @Autowired
    BankEndConfig bankEndConfig;

    @GetMapping(value = {"/", "/index"})
    public String homePage() {
        return "Index";
    }

    @GetMapping("/login")
    public String getLoginPage(HttpSession session) {

        if (session.getAttribute("id") == null) {
            return "Login";
        }
        return "Index";
    }

    @PostMapping("/login")
    public @ResponseBody String postLoginPage(UserDTO user, HttpSession session) {
        String result = "";
        if (session.getAttribute("id") == null) {
            UserDTO userDTO = UserService.login(bankEndConfig.getUrlUser(), user);
            if (userDTO == null) {

                result = "Fail";
            } else {
                session.setAttribute("id", userDTO.getId());
                session.setAttribute("permission", userDTO.getPermission());
                result = "Success";
            }
        }
        return result;
    }

    //    @GetMapping("/logout")
//    public  String getLogoutPage( HttpSession session) {
//        //session.removeAttribute("id");
////        session.removeAttribute("id");
////        session.removeAttribute("permission");
//    return "Success";
//    }
    @GetMapping("/logout")
    public String logoutPage(HttpSession session) {
        //session.removeAttribute("id");
        session.removeAttribute("id");
        session.removeAttribute("permission");
        return "redirect:/login";
    }


//    @GetMapping("/register")
//    public String getRegisterPage(Model model, HttpSession session) {
//        model.addAttribute("user", new UserDTO());
//        return "Register";
//    }

    @GetMapping("/user")
    public String getUser(PageUser page, HttpSession session) {
        if (session.getAttribute("id") == null)
            return "redirect:/login";
        return "User";
    }

    @PostMapping("/user")
    public @ResponseBody String findListUserByRequest(@RequestBody UserRequest userRequest, HttpSession session) throws JsonProcessingException {
        String result = "";
        if (session.getAttribute("id") != null) {

            PageUser listUser = UserService.findUserFollowRequest(bankEndConfig.getUrlUser(), userRequest);
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.writeValueAsString(listUser.getContent());
        }
        return result;
    }

    @GetMapping("/addUser")
    public String getAddUser(HttpSession session) {
        if (session.getAttribute("id") == null)
            return "redirect:/login";
        if ("user".equalsIgnoreCase(session.getAttribute("permission").toString()))
            return "redirect:/user";

        return "AddUser";
    }

    @PostMapping("/addUser")
    public @ResponseBody String postAddUser(UserDTO user, HttpSession session) throws JsonProcessingException {
        if (session.getAttribute("id") == null)
            return "login";
        if ("user".equalsIgnoreCase(session.getAttribute("permission").toString()))
            return "user";
        String result = "";
        ResponseAddUser responseAddUser = UserService.addUser(bankEndConfig.getUrlUser(), user);
        ObjectMapper mapper = new ObjectMapper();
        result = mapper.writeValueAsString(responseAddUser);
//        model.addAttribute("errorUsername", "Username đã tồn tại");
        return result;
    }
//    @PostMapping("/findPageListUserByRequest")
//    public String findPageListUserByRequest(Model model, UserDTO user, PageUser pageUser) {
//        String uri = "http://localhost:8080/users/findPageFindListUserByRequest";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        String body = "{\n" +
//                "    \"name\": \"" + user.getName() + "\",\n" +
//                "    \"email\":\"" + user.getName() + "\",\n" +
//                "    \"limit\":5,\n" +
//                "    \"page\":0\n" +
//                "}";
//        RestTemplate restTemplate = new RestTemplate();
//        //List<UserDTO> result = List.of(restTemplate.postForObject(uri,request, UserDTO[].class));
//        ResponseEntity<PageUser> result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(body, headers), PageUser.class);
//
//        PageUser listUser = result.getBody();
//        model.addAttribute("listUser", listUser.getContent());
//        return getUser(model, pageUser);
//    }


}
