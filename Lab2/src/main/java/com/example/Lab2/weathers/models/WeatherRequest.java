package com.example.Lab2.weathers.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;

public class WeatherRequest {


    @JsonProperty("datetime_range")
    @NotBlank(message = "datetime_range không được null")
    private String datetimeRange;
    @JsonProperty("location_id")
    @NotBlank(message = "location_id không được null")
    private String locationId;

    public String getDatetimeRange() {
        return datetimeRange;
    }

    public void setDatetimeRange(String datetimeRange) {
        this.datetimeRange = datetimeRange;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
