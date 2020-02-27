package com.example.market.ui.product;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import com.example.market.models.Cart;
import com.example.market.prevalent.Prevalent;
import com.google.firebase.database.*;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

public class ProductDetailViewModel extends ViewModel {
    private static final String LOG_TAG = "product detail view model";
    private int quantity = 1;
    private double price;
    private final String parentDB = "cart";
    private final String current = Prevalent.getPhone();
    private final DatabaseReference shoppingCartDatabaseReference =
            FirebaseDatabase.getInstance().getReference();

    public int getQuantity() {
        return quantity;
    }

    public void minusQuantity() {
        quantity--;
    }

    public void plusQuantity() {
        quantity++;
    }


    public double getTotal() {
        return Math.round(price * quantity * 100)/100.0;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean login() {
        return Prevalent.getLogin();
    }

    public void addToCart(final String productId, final String name, final String currentUnitPrice, final String quantity, final String snapshot) {

                final DatabaseReference existDatabaseReference = shoppingCartDatabaseReference.child("cart").child(Prevalent.getPhone());
                final Query query = existDatabaseReference.orderByChild("productId").equalTo(productId);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                        if (dataSnapshot1.exists()) {

                            String oldCart = dataSnapshot1.getValue().toString();
                            int quantity_first = oldCart.indexOf("quantity");
                            int quantity_last= oldCart.indexOf(",", quantity_first);
                            String oldQuantity = oldCart.substring(quantity_first+9, quantity_last);

                            int id_last = oldCart.indexOf("=");
                            String cartId = oldCart.substring(1, id_last);


                            Map<String, Object> map = new HashMap<>();
                            int newQuantity = Integer.parseInt(oldQuantity) + Integer.parseInt(quantity);
                            map.put("quantity", Integer.toString(newQuantity));
                            existDatabaseReference.child(cartId).updateChildren(map);
                            Log.i(LOG_TAG, cartId);
                        } else {
                            DatabaseReference newReference = shoppingCartDatabaseReference.child("cart").child(Prevalent.getPhone()).push();
                            String cartId = newReference.getKey();
                            Cart cart = new Cart(cartId, productId, name,currentUnitPrice, quantity, snapshot);
                            Map<String, Object> resultMap = cart.toMap();
                            newReference.updateChildren(resultMap);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//                    DatabaseReference newReference = shoppingCartDatabaseReference.child("cart").child(Prevalent.getPhone()).push();
//                    String cartId = newReference.getKey();
//                    Cart cart = new Cart(cartId, productId, name,currentUnitPrice, quantity, snapshot);
//                    Map<String, Object> resultMap = cart.toMap();
//                    newReference.updateChildren(resultMap);


    }


}
