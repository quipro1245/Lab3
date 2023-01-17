package com.example.lab2.user.controller;


import com.example.lab2.user.model.PageUser;
import com.example.lab2.user.model.ResponseAddUser;
import com.example.lab2.user.model.UserDTO;
import com.example.lab2.user.model.UserRequest;
import com.example.lab2.user.request.LoginRequest;
import com.example.lab2.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

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

    @GetMapping("")
    public List<UserDTO> list() {
        return userService.listAllUser();
    }

    @PostMapping("")
    public PageUser getListUser(@RequestBody UserRequest user) {
        Page<UserDTO> result = userService.findAll(user.getPage(), user.getLimit());
        PageUser pageUser = new PageUser();

        pageUser.setContent(result.getContent());
        pageUser.setTotalPages(result.getTotalPages());
        pageUser.setTotalElements(result.getTotalElements());
        pageUser.setNumber(result.getNumber());
        pageUser.setSize(result.getSize());
        return pageUser;
    }

    @PostMapping("/login")
    public List<UserDTO> loginConfig(Model model, @RequestBody LoginRequest user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            bindingResult.getFieldErrors().forEach(
                    error -> errors.put(error.getField(), error.getDefaultMessage())
            );
            for (String key : errors.keySet()) {
                model.addAttribute(key, errors.get(key));
            }
            return null;
        }
        List<UserDTO> userDTOS = userService.login(user.getUsername(), user.getPassword());
        if (userDTOS.size() < 1) {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không chính xác");
            return null;
        }
        return userDTOS;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable Integer id) {
        try {
            UserDTO user = userService.getUser(id);
            return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseAddUser add(@RequestBody UserDTO user) {
        ResponseAddUser responseAddUser = new ResponseAddUser();
        try {

            if ("Nu".equalsIgnoreCase(user.getGender()))
                user.setGender("Nữ");
            if (userService.findByCmnd(user.getCmnd()).size() != 0) {
                responseAddUser.setStatus("400");
                responseAddUser.setErrorCMND("errorCMND");
            }
            if (userService.findByUsername(user.getUsername()).size() != 0) {
                responseAddUser.setStatus("400");
                responseAddUser.setErrorUsername("errorUsername");
            }
            if (!"400".equalsIgnoreCase(responseAddUser.getStatus())) {
                responseAddUser.setStatus("200");
                userService.saveUser(user);
            }
            return responseAddUser;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/loginConfig")
    public List<UserDTO> loginConfig(@RequestBody UserDTO user) {
        return userService.loginConfig(user.getUsername(), user.getPassword());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody UserDTO user, @PathVariable Integer id) {
        try {
            UserDTO existUser = userService.getUser(id);
            user.setId(id);
            if (user.getPassword() != null)
                user.setPassword(getSHAHash(user.getPassword()));
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        userService.deleteUser(id);
    }

    @PostMapping("/findListUserByRequest")
    public List<UserDTO> findListUserByRequest(@RequestBody UserRequest user) {
        return userService.getListUserByRequest(user.getName(), user.getEmail(), user.getLimit(), user.getPage());
    }

    @PostMapping("/findUserByEmail")
    public List<UserDTO> findUserFollowEmail(@RequestBody UserDTO user) {
        return userService.findAllByEmailContains(user.getEmail());
    }

    @PostMapping("/findByUsername")
    public List<UserDTO> findByUsername(@RequestBody UserDTO user) {
        return userService.findByUsername(user.getUsername());
    }

    @PostMapping("/findByCmnd")
    public List<UserDTO> findByCmnd(@RequestBody UserDTO user) {
        return userService.findByCmnd(user.getCmnd());
    }

    @PostMapping("/page")
    public Page<UserDTO> page(@RequestBody UserDTO user) {
        return userService.paging(0, 2);
    }

    @PostMapping("/findPageFindListUserByRequest")
    public PageUser findPageFindListUserByRequest(@RequestBody UserRequest user) {
        Page<UserDTO> result;
        if (StringUtils.isBlank(user.getName()))
            result = userService.findAll(user.getPage(), user.getLimit());
        else
            result = userService.pageFindListUserByRequest(user.getName(), user.getName(), user.getPage(), user.getLimit());
        PageUser pageUser = new PageUser();
        pageUser.setContent(result.getContent());
        pageUser.setTotalPages(result.getTotalPages());
        pageUser.setTotalElements(result.getTotalElements());
        pageUser.setNumber(result.getNumber());
        pageUser.setSize(result.getSize());
        return pageUser;

    }

    @PostMapping("/findAllByNameContains")
    public Page<UserDTO> findAllByNameContains(@RequestBody UserDTO user) {
        if (StringUtils.isBlank(user.getName()))
            user.setName(null);
        return userService.findAllByNameContains(user.getName());
    }

    @PostMapping("/findAllByNameOrEmailContains")
    public Page<UserDTO> findAllByNameOrEmailContains(@RequestBody UserDTO user) {
        if (StringUtils.isBlank(user.getName()))
            user.setName(null);
        if (StringUtils.isBlank(user.getEmail()))
            user.setEmail(null);
        return userService.findAllByNameOrEmailContains(user.getName(), user.getEmail());
    }
}
