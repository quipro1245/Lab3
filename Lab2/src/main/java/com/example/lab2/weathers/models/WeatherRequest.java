package com.example.lab2.weathers.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class WeatherRequest {


    @JsonProperty("datetime_range")
    @NotBlank(message = "datetime_range không được null")
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])[ /](0[1-9]|1[012])[ /](19|20)\\d\\d\\s([0-1][0-9]|2[0-3])\\:([0-5][0-9])\\:([0-5][0-9])\\s[ -]\\s(0[1-9]|[12][0-9]|3[01])[ /](0[1-9]|1[012])[ /](19|20)\\d\\d\\s([0-1][0-9]|2[0-3])\\:([0-5][0-9])\\:([0-5][0-9])$", message = "Phải nhập đúng format dd/MM/yyyy HH:mm:ss - dd/MM/yyyy HH:mm:ss!")
    private String datetimeRange;
    @JsonProperty("location_id")
    @NotBlank(message = "location_id không được null")
    @Pattern(regexp = "^[0-9|\\\\,]{0,100}$", message = "Nhập location_id phải là số và nhiều location_id thì cách nhau bằng dấu phẩy và không có khoảng trắng")
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
