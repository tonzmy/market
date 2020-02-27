package com.example.market.ui.me.order.deliveringorder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.market.R;
import com.example.market.models.Order;
import com.example.market.adapter.OrderAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;

public class DeliveringOrderFragment extends Fragment {
    private DeliveringOrderViewModel deliveringOrderViewModel;
    private OrderAdapter orderAdapter;

    public static DeliveringOrderFragment newInstance() {
        DeliveringOrderFragment deliveringOrderFragment = new DeliveringOrderFragment();
        return deliveringOrderFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deliveringOrderViewModel = new ViewModelProvider(this).get(DeliveringOrderViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_me_deliveringorder, container, false);
        Query query = deliveringOrderViewModel.getRecyclerDB();
        FirebaseRecyclerOptions<Order> options = new FirebaseRecyclerOptions.
                Builder<Order>().setQuery(query, Order.class).build();
        orderAdapter = new OrderAdapter(options);
        RecyclerView recyclerView = root.findViewById(R.id.delivering_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(orderAdapter);
        return root;
    }
    @Override
    public void onStart() {
        super.onStart();
        orderAdapter.startListening();
    }

    @Override
    public void onStop() {
        orderAdapter.stopListening();
        super.onStop();
    }
}
