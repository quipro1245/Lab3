package com.example.lab2.user.controller.request;


import jakarta.validation.constraints.NotEmpty;

public class LoginRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    
}
