package com.saidur.eshop.request;

public class Omaster {
    int id;
    String orderNo;
    int productCount;
    float totalPrice;
    float totalDiscount;
    float netTotal;

    public Omaster(int id, String orderNo, int productCount, float totalPrice, float totalDiscount, float netTotal) {
        this.id = id;
        this.orderNo = orderNo;
        this.productCount = productCount;
        this.totalPrice = totalPrice;
        this.totalDiscount = totalDiscount;
        this.netTotal = netTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(float totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public float getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(float netTotal) {
        this.netTotal = netTotal;
    }
}
