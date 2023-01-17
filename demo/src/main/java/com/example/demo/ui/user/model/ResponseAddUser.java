package com.example.demo.ui.user.model;

import java.util.List;

public class ResponseAddUser {
    private String status;
    private String errorCMND;
    private String errorUsername;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCMND() {
        return errorCMND;
    }

    public void setErrorCMND(String errorCMND) {
        this.errorCMND = errorCMND;
    }

    public String getErrorUsername() {
        return errorUsername;
    }

    public void setErrorUsername(String errorUsername) {
        this.errorUsername = errorUsername;
    }
}
