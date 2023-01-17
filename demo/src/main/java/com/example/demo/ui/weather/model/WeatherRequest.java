package com.example.demo.ui.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherRequest {
    private String startDate;
    private String endDate;
    @JsonProperty("datetime_range")
    private String datetimeRange;

    @JsonProperty("location_id")
    private String locationId;
    private int page;
    private int limit;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDatetimeRange() {
        return datetimeRange;
    }

    public void setDatetimeRange(String datetimeRange) {
        this.datetimeRange = datetimeRange;
    }

    public String getLocationID() {
        return locationId;
    }

    public void setLocationID(String locationID) {
        this.locationId = locationID;
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
