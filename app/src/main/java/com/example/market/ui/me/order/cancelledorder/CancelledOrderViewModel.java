package com.example.market.ui.me.order.cancelledorder;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.example.market.FirebaseRepository;
import com.example.market.models.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static com.example.market.prevalent.Prevalent.getPhone;

public class CancelledOrderViewModel extends ViewModel {
    private final static String orderDB = "order";
    private final static String key = "1";
    private static String userDB = "user";
    private String current = getPhone();

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().
            getReference().child(orderDB).child(key);

    private final FirebaseRepository liveData = new FirebaseRepository(databaseReference);

    private final LiveData<Order> cancelledOrderLiveData = Transformations.map(liveData, new Function<DataSnapshot, Order>() {
        @Override
        public Order apply(DataSnapshot input) {
            return input.getValue(Order.class);
        }
    });

//    private Query recyclerDB = FirebaseDatabase.getInstance().getReference().child(orderDB).orderByChild("status").equalTo("cancelled");
    private Query recyclerDB = FirebaseDatabase.getInstance().getReference().child(orderDB).child(current).orderByChild("status").equalTo("cancelled");
    public Query getRecyclerDB() {
        return recyclerDB;
    }

    public LiveData<Order> getCancelledOrderLiveData() {
        return cancelledOrderLiveData;
    }
}
