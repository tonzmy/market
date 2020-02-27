package com.example.market.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeViewModel extends ViewModel {
    private static String productDB = "product";
    private static final DatabaseReference productDatabaseReference =
            FirebaseDatabase.getInstance().getReference().child(productDB);

    public static DatabaseReference getProductDatabaseReference() {
        return productDatabaseReference;
    }
}