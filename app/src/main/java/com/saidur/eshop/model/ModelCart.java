package com.saidur.eshop.model;

public class ModelCart {
    private int quantity,buyNow;
    private String productid;
    private int  cartId;
    private String product;

    public ModelCart() {
    }

    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getBuyNow() {
        return buyNow;
    }
    public void setBuyNow(int buyNow) {
        this.buyNow = buyNow;
    }
    public String getProductid() {
        return productid;
    }
    public void setProductid(String productid) {
        this.productid = productid;
    }
    public int getCartId() {
        return cartId;
    }
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }
}
