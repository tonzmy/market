package com.example.market.models;

public class Order {

    private String orderId;
    private String productId;
    private String quantity;
//    private String userId;
//    private String snapshot;
    private String status;
    private String time;
    private ProductSimple product;

    public Order() {

    }

    public Order(String orderId, String productId, String quantity, String status, String time, ProductSimple product) {
        this.orderId = orderId;
        this.productId = productId;
//        this.userId = userId;
//        this.snapshot = snapshot;
        this.status = status;
        this.quantity = quantity;
        this.time = time;
        this.product = product;
    }

    public ProductSimple getProduct() {
        return product;
    }

    public void setProduct(ProductSimple product) {
        this.product = product;
    }

    //    public void setSnapshot(String snapshot) {
//        this.snapshot = snapshot;
//    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

//    public void setUserId(String userId) {
//        this.userId = userId;
//    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public String getSnapshot() {
//        return snapshot;
//    }

    public String getOrderId() {
        return orderId;
    }

    public String getProductId() {
        return productId;
    }

//    public String getUserId() {
//        return userId;
//    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }
}
