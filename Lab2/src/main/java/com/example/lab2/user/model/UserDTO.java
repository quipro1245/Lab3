package com.example.lab2.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.Date;
@Entity
@Table(name = "user")

public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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

    public UserDTO() {
    }

    public UserDTO(int id, String name, String gender, String birthday, String cmnd, String email, String username, String password, String permission) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.cmnd = cmnd;
        this.email = email;
        this.username = username;
        this.password = password;
        this.permission = permission;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
