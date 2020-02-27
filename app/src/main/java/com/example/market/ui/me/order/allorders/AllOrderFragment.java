package com.example.market.ui.me.order.allorders;

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

public class AllOrderFragment extends Fragment {
    private AllOrderViewModel allOrderViewModel;
    private OrderAdapter adapter;
    private final static String LOG_TAG =  "All Order Fragment";

    public static AllOrderFragment newInstance() {
        AllOrderFragment allOrderFragment = new AllOrderFragment();
        return allOrderFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allOrderViewModel = new ViewModelProvider(this).get(AllOrderViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_me_allorder, container, false);
        Query query = allOrderViewModel.getRecyclerDB();

        FirebaseRecyclerOptions<Order> options = new FirebaseRecyclerOptions.
                Builder<Order>().setQuery(query, Order.class).build();

        adapter = new OrderAdapter(options);

        RecyclerView recyclerView = root.findViewById(R.id.all_order_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return root;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        adapter.stopListening();
        super.onStop();
    }
}
