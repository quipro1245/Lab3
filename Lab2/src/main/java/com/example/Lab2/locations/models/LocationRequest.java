package com.example.Lab2.locations.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class LocationRequest {

    @JsonProperty("input")
    //@Pattern(regexp = "[^\\+]", message = "Không được nhập ký tự khoảng trắng!")
    @NotBlank(message = "input không được bỏ trống")
    private String input;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
