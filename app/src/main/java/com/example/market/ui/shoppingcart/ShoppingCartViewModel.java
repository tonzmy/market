package com.example.market.ui.shoppingcart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Database;
import com.example.market.models.Cart;
import com.example.market.prevalent.Prevalent;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class ShoppingCartViewModel extends ViewModel {
    private final String parentDB = "cart";
    private double total = 0;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public DatabaseReference getShoppingCartDatabaseReference() {
        final DatabaseReference shoppingCartDatabaseReference =
                FirebaseDatabase.getInstance().getReference().child(parentDB).child(Prevalent.getPhone());
        return shoppingCartDatabaseReference;
    }

    public void minusQuantity(String key, String quantity) {
        final DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference().child(parentDB).child(Prevalent.getPhone());
        String newQuantity = Integer.toString(Integer.parseInt(quantity) - 1);
        Map<String, Object> map = new HashMap<>();
        map.put("quantity", newQuantity);
        databaseReference.child(key).updateChildren(map);
    }

    public void plusQuantity(String key, String quantity) {
        final DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference().child(parentDB).child(Prevalent.getPhone());
        String newQuantity = Integer.toString(Integer.parseInt(quantity) + 1);
        Map<String, Object> map = new HashMap<>();
        map.put("quantity", newQuantity);
        databaseReference.child(key).updateChildren(map);
    }

    public boolean login() {
        return Prevalent.getLogin();
    }

    public void placeOrder(Cart cart) {
        final DatabaseReference cartReference = FirebaseDatabase.getInstance().getReference().child("cart")
                .child(Prevalent.getPhone());
        final String cartId = cart.getCartId();
        cartReference.child(cartId).removeValue();

        final DatabaseReference orderReference = FirebaseDatabase.getInstance().getReference().child("order")
                .child(Prevalent.getPhone()).push();
        final String orderId = orderReference.getKey();
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        Map<String, Object> order = new HashMap<>();
        Map<String, Object> product = new HashMap<>();

        product.put("name", cart.getName());
        product.put("price", cart.getCurrentUnitPrice());
        product.put("snapshot", cart.getSnapshot());
        product.put("productId", cart.getProductId());

        order.put("orderId", orderId);
        order.put("productId", cart.getProductId());
        order.put("quantity", cart.getQuantity());
        order.put("status", "pending");
        order.put("time", ts);
        order.put("product", product);

        orderReference.updateChildren(order);


    }

    public void delete(Cart cart) {
        final DatabaseReference cartReference = FirebaseDatabase.getInstance().getReference().child("cart")
                .child(Prevalent.getPhone());
        cartReference.child(cart.getCartId()).removeValue();
    }
}