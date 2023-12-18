package com.saidur.eshop.network;

import com.saidur.eshop.model.ModelBanner;

import java.util.List;

public class BannerResponse {
     String message;
     boolean isError;
     int statusCode;
     String errorMessage;
     List<ModelBanner> result;

    public BannerResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<ModelBanner> getResult() {
        return result;
    }

    public void setResult(List<ModelBanner> result) {
        this.result = result;
    }
}
