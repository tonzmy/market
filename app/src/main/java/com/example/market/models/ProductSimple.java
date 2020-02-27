package com.example.market.models;

public class ProductSimple {
    private String productId;
    private String name;
    private String snapshot;
    private String price;

    public ProductSimple () {

    }

    public ProductSimple(String productId, String name, String snapshot, String price) {
        this.productId = productId;
        this.name = name;
        this.snapshot = snapshot;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
