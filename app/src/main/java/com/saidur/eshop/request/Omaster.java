package com.saidur.eshop.request;

public class Omaster {
    int id;
    int orderNo;
    int productCount;
    float totalPrice;
    float totalDiscount;
    float netTotal;

    public Omaster() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
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
