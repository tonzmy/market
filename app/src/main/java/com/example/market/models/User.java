package com.example.market.models;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String name, phoneNumber, password;

    public User() {

    }

    public User(String name, String phoneNumber, String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

//    public User(String name, String phoneNumber, String password, Order order) {
//        this.name = name;
//        this.phoneNumber = phoneNumber;
//        this.password = password;
//        this.order = order;
//    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("phoneNumber", phoneNumber);
        result.put("name", name);
        result.put("password", password);
        return result;
    }


}
