package com.example.market.ui.me.order;

import androidx.arch.core.util.Function;
import androidx.lifecycle.*;
import com.example.market.FirebaseRepository;
import com.example.market.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.market.prevalent.Prevalent.getPhone;

public class OrderViewModel extends ViewModel {
    private String productId;
    private DatabaseReference productDatabaseReference;
    private FirebaseRepository liveData;
    private LiveData<Product> productLiveData;

    private final DatabaseReference orderDatabaseReference = FirebaseDatabase.getInstance().getReference().child("order").child(getPhone());


    public DatabaseReference getOrderDatabaseReference() {
        return orderDatabaseReference;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
        productDatabaseReference = FirebaseDatabase.getInstance().getReference().child("product").child(getProductId());
        liveData = new FirebaseRepository(productDatabaseReference);
        setProductLiveData(liveData);
    }

    private void setProductLiveData(FirebaseRepository liveData) {
        productLiveData = Transformations.map(liveData, new Function<DataSnapshot, Product>() {
            @Override
            public Product apply(DataSnapshot input) {
                return input.getValue(Product.class);
            }
        });
    }

    public LiveData<Product> getProductLiveData(String productId) {
        this.productId = productId;
        productDatabaseReference = FirebaseDatabase.getInstance().getReference().child("product").child(getProductId());
        liveData = new FirebaseRepository(productDatabaseReference);
        productLiveData = Transformations.map(liveData, new Function<DataSnapshot, Product>() {
            @Override
            public Product apply(DataSnapshot input) {
                return input.getValue(Product.class);
            }
        });
        return productLiveData;
    }
}
