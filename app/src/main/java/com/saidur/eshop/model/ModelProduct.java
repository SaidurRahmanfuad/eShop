package com.saidur.eshop.model;

import java.util.List;

public class ModelProduct {
    int id;
    String name;
    String short_desc;
    String long_desc;
    String additional_desc;
    int price;
    int sale_price;
    int review;
    int ratings;
    String until;//MM/dd/yyyy
    int stock;
    List<ModelImage> pictures;

    public ModelProduct() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_desc() {
        return short_desc;
    }

    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }

    public String getLong_desc() {
        return long_desc;
    }

    public void setLong_desc(String long_desc) {
        this.long_desc = long_desc;
    }

    public String getAdditional_desc() {
        return additional_desc;
    }

    public void setAdditional_desc(String additional_desc) {
        this.additional_desc = additional_desc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSale_price() {
        return sale_price;
    }

    public void setSale_price(int sale_price) {
        this.sale_price = sale_price;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public String getUntil() {
        return until;
    }

    public void setUntil(String until) {
        this.until = until;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<ModelImage> getPictures() {
        return pictures;
    }

    public void setPictures(List<ModelImage> pictures) {
        this.pictures = pictures;
    }
}
