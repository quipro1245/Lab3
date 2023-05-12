package com.example.demo.ui.elasticsearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "weather")
public class WeatherElasticsearch {

    @Id
    private String id;
    @JsonProperty("location_id")
    @Field(type = FieldType.Long, name = "location_id")
    private String locationId;
    @JsonProperty("time_epoch")
    @Field(type = FieldType.Date, name = "time_epoch")
    private int timeEpoch;
    @JsonProperty("time")
    @Field(type = FieldType.Date, name = "time")
    private String time;
    @JsonProperty("temp_c")
    @Field(type = FieldType.Double, name = "temp_c")
    private Double tempC;
    @JsonProperty("temp_f")
    @Field(type = FieldType.Double, name = "temp_f")
    private Double tempF;
    @JsonProperty("is_day")
    @Field(type = FieldType.Long, name = "is_day")
    private int isDay;

//    @Field(type = FieldType.Long, name = "condition")
//    private ConditionDTO condition;
    @JsonProperty("condition_code")
    @Field(type = FieldType.Keyword, name = "condition_code")
    private String conditionCode;
    @JsonProperty("condition_icon")
    @Field(type = FieldType.Keyword, name = "condition_icon")
    private String conditionIcon;
    @JsonProperty("condition_text")
    @Field(type = FieldType.Keyword, name = "condition_text")
    private String conditionText;
    @JsonProperty("wind_mph")
    @Field(type = FieldType.Double, name = "wind_mph")
    private Double windMph;
    @JsonProperty("wind_kph")
    @Field(type = FieldType.Double, name = "wind_kph")
    private Double windKph;
    @JsonProperty("wind_degree")
    @Field(type = FieldType.Long, name = "wind_degree")
    private int windDegree;
    @JsonProperty("wind_dir")
    @Field(type = FieldType.Keyword, name = "wind_dir")
    private String windDir;
    @JsonProperty("pressure_mb")
    @Field(type = FieldType.Long, name = "pressure_mb")
    private Double pressureMb;
    @JsonProperty("pressure_in")
    @Field(type = FieldType.Double, name = "pressure_in")
    private Double pressureIn;
    @JsonProperty("precip_mm")
    @Field(type = FieldType.Double, name = "precip_mm")
    private Double precipMm;
    @JsonProperty("precip_in")
    @Field(type = FieldType.Double, name = "precip_in")
    private Double precipIn;
    @JsonProperty("humidity")
    @Field(type = FieldType.Long, name = "humidity")
    private int humidity;
    @JsonProperty("cloud")
    @Field(type = FieldType.Long, name = "cloud")
    private int cloud;
    @JsonProperty("feelslike_c")
    @Field(type = FieldType.Double, name = "feelslike_c")
    private Double feelsLikeC;
    @JsonProperty("feelslike_f")
    @Field(type = FieldType.Double, name = "feelslike_f")
    private Double feelsLikeF;
    @JsonProperty("windchill_c")
    @Field(type = FieldType.Double, name = "windchill_c")
    private Double windChillC;
    @JsonProperty("windchill_f")
    @Field(type = FieldType.Double, name = "windchill_f")
    private Double windChillF;
    @JsonProperty("heatindex_c")
    @Field(type = FieldType.Double, name = "heatindex_c")
    private Double heatIndexC;
    @JsonProperty("heatindex_f")
    @Field(type = FieldType.Double, name = "heatindex_f")
    private Double heatIndexF;
    @JsonProperty("dewpoint_c")
    @Field(type = FieldType.Double, name = "dewpoint_c")
    private Double dewPointC;
    @JsonProperty("dewpoint_f")
    @Field(type = FieldType.Double, name = "dewpoint_f")
    private Double dewPointF;
    @JsonProperty("will_it_rain")
    @Field(type = FieldType.Long, name = "will_it_rain")
    private int willItRain;
    @JsonProperty("chance_of_rain")
    @Field(type = FieldType.Long, name = "chance_of_rain")
    private int chanceOfRain;
    @JsonProperty("will_it_snow")
    @Field(type = FieldType.Long, name = "will_it_snow")
    private int willItSnow;
    @JsonProperty("chance_of_snow")
    @Field(type = FieldType.Long, name = "chance_of_snow")
    private int chanceOfSnow;
    @JsonProperty("vis_km")
    @Field(type = FieldType.Double, name = "vis_km")
    private Double visKm;
    @JsonProperty("vis_miles")
    @Field(type = FieldType.Long, name = "vis_miles")
    private Double visMiles;
    @JsonProperty("gust_mph")
    @Field(type = FieldType.Double, name = "gust_mph")
    private Double gustMph;
    @JsonProperty("gust_kph")
    @Field(type = FieldType.Double, name = "gust_kph")
    private Double gustKph;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public int getTimeEpoch() {
        return timeEpoch;
    }

    public void setTimeEpoch(int timeEpoch) {
        this.timeEpoch = timeEpoch;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getConditionCode() {
        return conditionCode;
    }

    public void setConditionCode(String conditionCode) {
        this.conditionCode = conditionCode;
    }

    public String getConditionIcon() {
        return conditionIcon;
    }

    public void setConditionIcon(String conditionIcon) {
        this.conditionIcon = conditionIcon;
    }

    public String getConditionText() {
        return conditionText;
    }

    public void setConditionText(String conditionText) {
        this.conditionText = conditionText;
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
}
