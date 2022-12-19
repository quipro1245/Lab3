package com.example.lab2.locations.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class LocationRequest {

    @JsonProperty("input")
    @Pattern(regexp = "^[a-zA-Z0-9]{0,50}$", message = "Không được nhập ký tự đặt biệt!")
    @NotBlank(message = "input không được bỏ trống")
    private String input;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
