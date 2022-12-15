package com.example.lab2.weathers.models;


import com.example.lab2.weathers.service.WeatherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document("Weather")
public class WeatherDTO {
    private final static Logger logger = LogManager.getLogger(WeatherService.class);
    private String id;
    private int time_epoch;
    private String time;
    private Double tempC;
    private Double tempF;
    private int isDay;
    private Object condition;
    private Double windMph;
    private Double windKph;
    private int windDegree;
    private String windDir;
    private Double pressureMb;
    private Double pressureIn;
    private Double precipMm;
    private Double precipIn;
    private int humidity;
    private int cloud;
    private Double feelsLikeC;
    private Double feelsLikeF;
    private Double windChillC;
    private Double windChillF;
    private Double heatIndexC;
    private Double heatIndexF;
    private Double dewPointC;
    private Double dewPointF;
    private int willItRain;
    private int chanceOfRain;
    private int willItSnow;
    private int chanceOfSnow;
    private Double visKm;
    private Double visMiles;
    private Double gustMph;
    private Double gustKph;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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



    public Object getCondition() {
        return condition;
    }

    public void setCondition(Object condition) {
        this.condition = condition;
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

    public Double getTempC() {
        return tempC;
    }

    public void setTempC(Double tempC) {
        this.tempC = tempC;
    }

    public Double getTempF() {
        return tempF;
    }

    public void setTempF(Double tempF) {
        this.tempF = tempF;
    }

    public int getIsDay() {
        return isDay;
    }

    public void setIsDay(int isDay) {
        this.isDay = isDay;
    }

    public Double getWindMph() {
        return windMph;
    }

    public void setWindMph(Double windMph) {
        this.windMph = windMph;
    }

    public Double getWindKph() {
        return windKph;
    }

    public void setWindKph(Double windKph) {
        this.windKph = windKph;
    }

    public int getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(int windDegree) {
        this.windDegree = windDegree;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public Double getPressureMb() {
        return pressureMb;
    }

    public void setPressureMb(Double pressureMb) {
        this.pressureMb = pressureMb;
    }

    public Double getPressureIn() {
        return pressureIn;
    }

    public void setPressureIn(Double pressureIn) {
        this.pressureIn = pressureIn;
    }

    public Double getPrecipMm() {
        return precipMm;
    }

    public void setPrecipMm(Double precipMm) {
        this.precipMm = precipMm;
    }

    public Double getPrecipIn() {
        return precipIn;
    }

    public void setPrecipIn(Double precipIn) {
        this.precipIn = precipIn;
    }

    public Double getFeelsLikeC() {
        return feelsLikeC;
    }

    public void setFeelsLikeC(Double feelsLikeC) {
        this.feelsLikeC = feelsLikeC;
    }

    public Double getFeelsLikeF() {
        return feelsLikeF;
    }

    public void setFeelsLikeF(Double feelsLikeF) {
        this.feelsLikeF = feelsLikeF;
    }

    public Double getWindChillC() {
        return windChillC;
    }

    public void setWindChillC(Double windChillC) {
        this.windChillC = windChillC;
    }

    public Double getWindChillF() {
        return windChillF;
    }

    public void setWindChillF(Double windChillF) {
        this.windChillF = windChillF;
    }

    public Double getHeatIndexC() {
        return heatIndexC;
    }

    public void setHeatIndexC(Double heatIndexC) {
        this.heatIndexC = heatIndexC;
    }

    public Double getHeatIndexF() {
        return heatIndexF;
    }

    public void setHeatIndexF(Double heatIndexF) {
        this.heatIndexF = heatIndexF;
    }

    public Double getDewPointC() {
        return dewPointC;
    }

    public void setDewPointC(Double dewPointC) {
        this.dewPointC = dewPointC;
    }

    public Double getDewPointF() {
        return dewPointF;
    }

    public void setDewPointF(Double dewPointF) {
        this.dewPointF = dewPointF;
    }

    public int getWillItRain() {
        return willItRain;
    }

    public void setWillItRain(int willItRain) {
        this.willItRain = willItRain;
    }

    public int getChanceOfRain() {
        return chanceOfRain;
    }

    public void setChanceOfRain(int chanceOfRain) {
        this.chanceOfRain = chanceOfRain;
    }

    public int getWillItSnow() {
        return willItSnow;
    }

    public void setWillItSnow(int willItSnow) {
        this.willItSnow = willItSnow;
    }

    public int getChanceOfSnow() {
        return chanceOfSnow;
    }

    public void setChanceOfSnow(int chanceOfSnow) {
        this.chanceOfSnow = chanceOfSnow;
    }

    public Double getVisKm() {
        return visKm;
    }

    public void setVisKm(Double visKm) {
        this.visKm = visKm;
    }

    public Double getVisMiles() {
        return visMiles;
    }

    public void setVisMiles(Double visMiles) {
        this.visMiles = visMiles;
    }

    public Double getGustMph() {
        return gustMph;
    }

    public void setGustMph(Double gustMph) {
        this.gustMph = gustMph;
    }

    public Double getGustKph() {
        return gustKph;
    }

    public void setGustKph(Double gustKph) {
        this.gustKph = gustKph;
    }

    public String getObjectToString(Object condition){
        String result ="";
        try {
            Map<String,Object> stringObjectMap = (Map<String,Object>)condition;
            for (String item:stringObjectMap.keySet()) {
                result+= item+": "+stringObjectMap.get(item)+"; ";
            }

        }catch (Exception e){
            logger.error(""+e);
        }
        return result;
    }
}
