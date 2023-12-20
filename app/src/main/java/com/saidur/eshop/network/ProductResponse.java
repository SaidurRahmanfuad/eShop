package com.saidur.eshop.network;

import com.saidur.eshop.model.ModelCategory;
import com.saidur.eshop.model.ModelProduct;

import java.util.List;

public class ProductResponse {
     String message;
     boolean isError;
     int statusCode;
     String errorMessage;
     List<ModelProduct> result;

    public ProductResponse() {
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

    public List<ModelProduct> getResult() {
        return result;
    }

    public void setResult(List<ModelProduct> result) {
        this.result = result;
    }
}
