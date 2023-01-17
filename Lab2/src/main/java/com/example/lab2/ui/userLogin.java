package com.example.lab2.ui;

import com.example.lab2.user.model.UserDTO;
import com.example.lab2.user.model.UserRepository;
import com.example.lab2.user.request.LoginRequest;
import com.example.lab2.user.service.UserService;
import jakarta.validation.Valid;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.lab2.user.controller.UserController.getSHAHash;

@Controller

public class userLogin {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    UserService userService;

    @GetMapping("/index")
    public String viewHomePage() {
        return "index";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDTO());

        return "register";
    }
    @PostMapping("/register")
    public String processRegister(UserDTO user) {
        if(user.getPassword()!=null)
            user.setPassword(getSHAHash(user.getPassword()));
        if(user.getPermission()==null)
            user.setPermission("user");
        userRepo.save(user);

        return "register_success";
    }
    @GetMapping("/listUsers")
    public String listUser(Model model,UserDTO userDTO) {
        List<UserDTO> listUsers;
        if(!StringUtils.isBlank(userDTO.getName())){
//            listUsers = userService.findAllByNameContains(userDTO.getName());
//            if(listUsers.isEmpty())
//                listUsers = userService.findAllByEmailContains(userDTO.getName());
            listUsers = userService.findListUserByRequest(userDTO.getName(),userDTO.getName());
        }else {
            listUsers= userRepo.findAll();
        }
        model.addAttribute("listUsers", listUsers);
        return "user";
    }


//    @PostMapping("/users")
//    public String findUsers(Model model,UserDTO userDTO) {
////        List<UserDTO> listUsers;
////        if(!StringUtils.isBlank(userDTO.getName())){
////            listUsers = userService.findAllByNameContains(userDTO.getName());
////            if(listUsers.isEmpty())
////                listUsers = userService.findAllByEmailContains(userDTO.getName());
////        }else {
////            listUsers= userRepo.findAll();
////        }
////        model.addAttribute("listUsers", listUsers);
//        return listUser(model,userDTO);
//    }
    @GetMapping("/addUser")
    public String getAddUser(Model model,UserDTO userDTO) {
        model.addAttribute("user",userDTO);
        return "addUser";
    }
    @PostMapping("/addUser")
    public String postAddUser( Model model,@Valid UserDTO user, BindingResult bindingResult ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors= new HashMap<>();

            bindingResult.getFieldErrors().forEach(
                    error -> errors.put(error.getField(), error.getDefaultMessage())
            );
            for(String key: errors.keySet()){
                model.addAttribute(key,errors.get(key));
            }
            return getAddUser(model,user);
        }
        if(user.getGender()=="Nu")
            user.setGender("Nữ");
        if(user.getPassword()!=null)
            user.setPassword(getSHAHash(user.getPassword()));
        if(user.getPermission()==null)
            user.setPermission("user");
        userRepo.save(user);
        return listUser(model,user);
    }
    @GetMapping("/user/delete/{id}")
    public String getDeleteUser( Model model,@PathVariable Integer id,UserDTO userDTO) {
        userDTO.setId(id);
        return "DeleteUser";
    }
    @PostMapping("/user/delete/{id}")
    public String postDeleteUser( Model model,@PathVariable Integer id,UserDTO user) {
        userService.deleteUser(user.getId());
        return "DeleteUser";
    }
}
