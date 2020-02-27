package com.example.market.ui.search;

import androidx.lifecycle.ViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SearchResultViewModel extends ViewModel {
    public Query getProduct(String name, String from) {
        DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference();
        if (from == "home") {
            return databaseReference.child("product").orderByChild("name").startAt(name).endAt(name+"\uf8ff");
        } else {
            return databaseReference.child("product").orderByChild("category").equalTo(name);
//            return databaseReference.child("product").orderByChild("name").startAt("N").endAt("N"+"\uf8ff");
        }

    }
}
