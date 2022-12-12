package com.example.Lab2.Locations.Models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
public class LocationRequest {

    @NotNull(message = "input không được bỏ trống")
    private String input;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
