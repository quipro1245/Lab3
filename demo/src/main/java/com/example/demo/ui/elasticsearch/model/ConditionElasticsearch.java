package com.example.demo.ui.elasticsearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class ConditionElasticsearch {

    @JsonProperty("condition_code")
    @Field(type = FieldType.Keyword, name = "condition_code")
    private String conditionCode;
    @JsonProperty("condition_icon")
    @Field(type = FieldType.Keyword, name = "condition_icon")
    private String conditionIcon;
    @JsonProperty("condition_text")
    @Field(type = FieldType.Keyword, name = "condition_text")
    private String conditionText;

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
}
