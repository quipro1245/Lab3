package com.example.demo.ui.user.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BankEndConfig {
    @Value("${spring.backend.url}")
    private String url;

    @Value("${spring.backend.url.user}")
    private String urlUser;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlUser() {
        return urlUser;
    }

    public void setUrlUser(String urlUser) {
        this.urlUser = urlUser;
    }
}
