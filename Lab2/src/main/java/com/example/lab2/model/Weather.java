package com.example.lab2.model;

import java.util.Map;

public class Weather {
    private Map<String, Object> location;
    private Map<String, Object> forecast;

    public Map<String, Object> getForecast() {
        return forecast;
    }

    public void setForecast(Map<String, Object> forecast) {
        this.forecast = forecast;
    }

    public Map<String, Object> getLocation() {
        return location;
    }

    public void setLocation(Map<String, Object> location) {
        this.location = location;
    }
}
