package com.example.lab2.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class MongoConfig {
    @Value("${spring.data.mongodb.url}")
    private String url;

    @Value("${spring.data.mongodb.db}")
    private String db;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }
}
