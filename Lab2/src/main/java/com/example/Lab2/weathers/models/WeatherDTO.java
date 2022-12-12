package com.example.Lab2.weathers.models;


import org.springframework.data.mongodb.core.mapping.Document;

@Document("Weather")
public class WeatherDTO {

    private int id;
    private int time_epoch;
    private String time;
    private Double temp_c;
    private Double temp_f;
    private int is_day;
    private Object condition;
    private Double wind_mph;
    private Double wind_kph;
    private int wind_degree;
    private String wind_dir;
    private Double pressure_mb;
    private Double pressure_in;
    private Double precip_mm;
    private Double precip_in;
    private int humidity;
    private int cloud;
    private Double feelslike_c;
    private Double feelslike_f;
    private Double windchill_c;
    private Double windchill_f;
    private Double heatindex_c;
    private Double heatindex_f;
    private Double dewpoint_c;
    private Double dewpoint_f;
    private int will_it_rain;
    private int chance_of_rain;
    private int will_it_snow;
    private int chance_of_snow;
    private Double vis_km;
    private Double vis_miles;
    private Double gust_mph;
    private Double gust_kph;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime_epoch() {
        return time_epoch;
    }

    public void setTime_epoch(int time_epoch) {
        this.time_epoch = time_epoch;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getTemp_c() {
        return temp_c;
    }

    public void setTemp_c(Double temp_c) {
        this.temp_c = temp_c;
    }

    public Double getTemp_f() {
        return temp_f;
    }

    public void setTemp_f(Double temp_f) {
        this.temp_f = temp_f;
    }

    public int getIs_day() {
        return is_day;
    }

    public void setIs_day(int is_day) {
        this.is_day = is_day;
    }

    public Object getCondition() {
        return condition;
    }

    public void setCondition(Object condition) {
        this.condition = condition;
    }

    public Double getWind_mph() {
        return wind_mph;
    }

    public void setWind_mph(Double wind_mph) {
        this.wind_mph = wind_mph;
    }

    public Double getWind_kph() {
        return wind_kph;
    }

    public void setWind_kph(Double wind_kph) {
        this.wind_kph = wind_kph;
    }

    public int getWind_degree() {
        return wind_degree;
    }

    public void setWind_degree(int wind_degree) {
        this.wind_degree = wind_degree;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public Double getPressure_mb() {
        return pressure_mb;
    }

    public void setPressure_mb(Double pressure_mb) {
        this.pressure_mb = pressure_mb;
    }

    public Double getPressure_in() {
        return pressure_in;
    }

    public void setPressure_in(Double pressure_in) {
        this.pressure_in = pressure_in;
    }

    public Double getPrecip_mm() {
        return precip_mm;
    }

    public void setPrecip_mm(Double precip_mm) {
        this.precip_mm = precip_mm;
    }

    public Double getPrecip_in() {
        return precip_in;
    }

    public void setPrecip_in(Double precip_in) {
        this.precip_in = precip_in;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getCloud() {
        return cloud;
    }

    public void setCloud(int cloud) {
        this.cloud = cloud;
    }

    public Double getFeelslike_c() {
        return feelslike_c;
    }

    public void setFeelslike_c(Double feelslike_c) {
        this.feelslike_c = feelslike_c;
    }

    public Double getFeelslike_f() {
        return feelslike_f;
    }

    public void setFeelslike_f(Double feelslike_f) {
        this.feelslike_f = feelslike_f;
    }

    public Double getWindchill_c() {
        return windchill_c;
    }

    public void setWindchill_c(Double windchill_c) {
        this.windchill_c = windchill_c;
    }

    public Double getWindchill_f() {
        return windchill_f;
    }

    public void setWindchill_f(Double windchill_f) {
        this.windchill_f = windchill_f;
    }

    public Double getHeatindex_c() {
        return heatindex_c;
    }

    public void setHeatindex_c(Double heatindex_c) {
        this.heatindex_c = heatindex_c;
    }

    public Double getHeatindex_f() {
        return heatindex_f;
    }

    public void setHeatindex_f(Double heatindex_f) {
        this.heatindex_f = heatindex_f;
    }

    public Double getDewpoint_c() {
        return dewpoint_c;
    }

    public void setDewpoint_c(Double dewpoint_c) {
        this.dewpoint_c = dewpoint_c;
    }

    public Double getDewpoint_f() {
        return dewpoint_f;
    }

    public void setDewpoint_f(Double dewpoint_f) {
        this.dewpoint_f = dewpoint_f;
    }

    public int getWill_it_rain() {
        return will_it_rain;
    }

    public void setWill_it_rain(int will_it_rain) {
        this.will_it_rain = will_it_rain;
    }

    public int getChance_of_rain() {
        return chance_of_rain;
    }

    public void setChance_of_rain(int chance_of_rain) {
        this.chance_of_rain = chance_of_rain;
    }

    public int getWill_it_snow() {
        return will_it_snow;
    }

    public void setWill_it_snow(int will_it_snow) {
        this.will_it_snow = will_it_snow;
    }

    public int getChance_of_snow() {
        return chance_of_snow;
    }

    public void setChance_of_snow(int chance_of_snow) {
        this.chance_of_snow = chance_of_snow;
    }

    public Double getVis_km() {
        return vis_km;
    }

    public void setVis_km(Double vis_km) {
        this.vis_km = vis_km;
    }

    public Double getVis_miles() {
        return vis_miles;
    }

    public void setVis_miles(Double vis_miles) {
        this.vis_miles = vis_miles;
    }

    public Double getGust_mph() {
        return gust_mph;
    }

    public void setGust_mph(Double gust_mph) {
        this.gust_mph = gust_mph;
    }

    public Double getGust_kph() {
        return gust_kph;
    }

    public void setGust_kph(Double gust_kph) {
        this.gust_kph = gust_kph;
    }
}
