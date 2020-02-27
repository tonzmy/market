package com.example.market.models;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private String cartId;
    private String productId;
    private String name;
    private String currentUnitPrice;
    private String quantity;
    private String snapshot;

    public Cart() {
    }

    public Cart(String cartId, String productId, String name, String currentUnitPrice, String quantity, String snapshot) {
        this.cartId = cartId;
        this.productId = productId;
        this.name = name;
        this.currentUnitPrice = currentUnitPrice;
        this.quantity = quantity;
        this.snapshot = snapshot;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
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

    public String getCurrentUnitPrice() {
        return currentUnitPrice;
    }

    public void setCurrentUnitPrice(String currentUnitPrice) {
        this.currentUnitPrice = currentUnitPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("cartId", cartId);
        hashMap.put("productId", productId);
        hashMap.put("name", name);
        hashMap.put("currentUnitPrice", currentUnitPrice);
        hashMap.put("quantity", quantity);
        hashMap.put("snapshot", snapshot);
        return hashMap;
    }
}
