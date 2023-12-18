package com.saidur.eshop.network;

import com.saidur.eshop.model.ModelBanner;
import com.saidur.eshop.model.ModelCategory;

import java.util.List;

public class CategoryResponse {
     String message;
     boolean isError;
     int statusCode;
     String errorMessage;
     List<ModelCategory> result;

    public CategoryResponse() {
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

    public List<ModelCategory> getResult() {
        return result;
    }

    public void setResult(List<ModelCategory> result) {
        this.result = result;
    }
}
