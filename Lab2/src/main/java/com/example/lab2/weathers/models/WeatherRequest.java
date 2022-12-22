package com.example.lab2.weathers.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public class WeatherRequest {


    @JsonProperty("datetime_range")
    @NotBlank(message = "datetime_range không được bỏ trống")
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])[ /](0[1-9]|1[012])[ /](19|20)\\d\\d\\s([0-1][0-9]|2[0-3])\\:([0-5][0-9])\\:([0-5][0-9])\\s[ -]\\s(0[1-9]|[12][0-9]|3[01])[ /](0[1-9]|1[012])[ /](19|20)\\d\\d\\s([0-1][0-9]|2[0-3])\\:([0-5][0-9])\\:([0-5][0-9])$", message = "Phải nhập đúng format dd/MM/yyyy HH:mm:ss - dd/MM/yyyy HH:mm:ss!")
    private String datetimeRange;
    @JsonProperty("location_id")
    //@NotBlank(message = "location_id không được null")
    @Pattern(regexp = "^[0-9|\\\\,]{0,100}$", message = "Nhập location_id phải là số và nhiều location_id thì cách nhau bằng dấu phẩy và không có khoảng trắng")
    private String locationId;


    @JsonProperty("page")
    //@Min(value = 1,message = "page bắt đầu từ trang 1")

    private int page;

    @JsonProperty("limit")
//    @Min(value = 10,message = "limit phải lớn hơn hoặc bằng 10")
//    @Max(value = 50,message = "limit phải nhó hơn hoặc bằng 50")

    private int limit;
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
