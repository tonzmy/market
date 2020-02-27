package com.example.market.models;

public class Product {
    private String category;
    private String description;
    private String name;
    private String price;
    private String productId;
    private String promotionPrice;
    private String snapshot;
    private String subcategory;

    public Product() {

    }

    public Product(String category, String description, String name, String price, String productId, String promotionPrice, String snapshot, String subcategory) {
        this.category = category;
        this.description = description;
        this.name = name;
        this.price = price;
        this.productId = productId;
        this.promotionPrice = promotionPrice;
        this.snapshot = snapshot;
        this.subcategory = subcategory;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getProductId() {
        return productId;
    }

    public String getPromotionPrice() {
        return promotionPrice;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setPromotionPrice(String promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
}
