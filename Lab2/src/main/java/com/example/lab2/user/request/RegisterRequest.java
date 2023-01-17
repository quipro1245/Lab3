package com.example.lab2.user.request;

import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {
    @NotBlank(message = "Name cannot be blank!")
    private String name;

    private String gender;

    @NotBlank(message = "Birthday cannot be blank!")
    private String birthday;

    @NotBlank(message = "CMND cannot be blank!")
    private String cmnd;

    @NotBlank(message = "Email cannot be blank!")
    private String email;

    @NotBlank(message = "User name cannot be blank!")
    private String username;

    @NotBlank(message = "Password cannot be blank!")
    private String password;

    private String permission;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
