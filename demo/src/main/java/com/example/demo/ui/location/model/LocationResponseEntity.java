package com.example.demo.ui.location.model;

import org.springframework.core.io.Resource;

import java.util.ArrayList;

public class LocationResponseEntity {
    private ArrayList header;
    private Resource body;

    public ArrayList getHeader() {
        return header;
    }

    public void setHeader(ArrayList header) {
        this.header = header;
    }

    public Resource getBody() {
        return body;
    }

    public void setBody(Resource body) {
        this.body = body;
    }
}
