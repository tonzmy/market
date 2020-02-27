package com.example.market.ui.me.order.allorders;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.example.market.FirebaseRepository;
import com.example.market.models.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.market.prevalent.Prevalent.getPhone;

public class AllOrderViewModel extends ViewModel {
    private static String orderDB = "order";
    private static String userDB = "user";
    private String current = getPhone();
    private static String key = "1";
    private static final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(orderDB).child(key);
    private final FirebaseRepository liveData = new FirebaseRepository(databaseReference);

    private final LiveData<Order> allOrderLiveData = Transformations.map(liveData, new Function<DataSnapshot, Order>() {
        @Override
        public Order apply(DataSnapshot input) {
            return input.getValue(Order.class);
        }
    });

    public LiveData<Order> getOrder() {
        return allOrderLiveData;
    }

    private DatabaseReference recyclerDB = FirebaseDatabase.getInstance().getReference().child(orderDB).child(current);
    public DatabaseReference getRecyclerDB() {
        return recyclerDB;
    }
}
