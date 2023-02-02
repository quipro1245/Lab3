package com.example.demo.ui.weather.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ViewWeather {
    @Value("${spring.manageBlue.viewWeather}")
    private String viewWeatherManageBlue;

    @Value("${spring.manageRed.viewWeather}")
    private String viewWeatherManageRed;

    public String getViewWeatherManageBlue() {
        return viewWeatherManageBlue;
    }

    public void setViewWeatherManageBlue(String viewWeatherManageBlue) {
        this.viewWeatherManageBlue = viewWeatherManageBlue;
    }

    public String getViewWeatherManageRed() {
        return viewWeatherManageRed;
    }

    public void setViewWeatherManageRed(String viewWeatherManageRed) {
        this.viewWeatherManageRed = viewWeatherManageRed;
    }
}
