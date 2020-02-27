package com.example.market.ui.me.order.deliveringorder;

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

public class DeliveringOrderViewModel extends ViewModel {
    private static final String orderDB = "order";
    private static final String key = "1";

    private static String userDB = "user";
    private String current = getPhone();

    private static final DatabaseReference databasereference = FirebaseDatabase.getInstance().
            getReference().child(orderDB).child(key);

    private final FirebaseRepository liveData = new FirebaseRepository(databasereference);

    private final LiveData<Order> deliveringLiveData = Transformations.map(liveData, new Function<DataSnapshot, Order>() {
        @Override
        public Order apply(DataSnapshot input) {
            return input.getValue(Order.class);
        }
    });

//    private Query recyclerDB = FirebaseDatabase.getInstance().
//            getReference().child(orderDB).orderByChild("status").equalTo("delivering");
    private Query recyclerDB = FirebaseDatabase.getInstance().getReference().child(orderDB).child(current).orderByChild("status").equalTo("delivering");

    public Query getRecyclerDB() {
        return recyclerDB;
    }

    public LiveData<Order> getDeliveringLiveData() {
        return deliveringLiveData;
    }
}
