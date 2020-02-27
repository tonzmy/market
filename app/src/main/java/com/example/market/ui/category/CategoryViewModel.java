package com.example.market.ui.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class CategoryViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public CategoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public Query getProduct(String name) {
        final Query query =
                FirebaseDatabase.getInstance().getReference().child("product").equalTo(name);
        return query;
    }
}