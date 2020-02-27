package com.example.market.prevalent;

import com.example.market.models.User;

public class Prevalent {
    public static Boolean login = false;
    public static User currentOnlineUser;
    public static String phone;
    public static final String UserPhoneKey = "UserPhone";
    public static final String UserPasswordKey = "UserPassword";

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        Prevalent.phone = phone;
    }

    public static Boolean getLogin() {
        return login;
    }

    public static void setLogin(Boolean login) {
        Prevalent.login = login;
    }

    public static User getCurrentOnlineUser() {
        return currentOnlineUser;
    }

    public static void setCurrentOnlineUser(User currentOnlineUser) {
        Prevalent.currentOnlineUser = currentOnlineUser;
    }

    public static String getUserPhoneKey() {
        return UserPhoneKey;
    }

    public static String getUserPasswordKey() {
        return UserPasswordKey;
    }


}
