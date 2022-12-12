package com.example.Lab2.weathers.models;

import jakarta.validation.constraints.NotNull;

public class WeatherRequest {

    @NotNull(message = "datetime_range không được null")
    private String datetime_range;

    @NotNull(message = "location_id không được null")
    private String location_id;

    public String getDatetime_range() {
        return datetime_range;
    }

    public void setDatetime_range(String datetime_range) {
        this.datetime_range = datetime_range;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }
}
