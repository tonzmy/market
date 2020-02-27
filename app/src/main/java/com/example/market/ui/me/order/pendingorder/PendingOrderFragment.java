package com.example.market.ui.me.order.pendingorder;

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

public class PendingOrderFragment extends Fragment {

    PendingOrderViewModel pendingOrderViewModel;
    private OrderAdapter adapter;

    public static PendingOrderFragment newInstance() {
        PendingOrderFragment pendingOrderFragment = new PendingOrderFragment();
        return pendingOrderFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pendingOrderViewModel = new ViewModelProvider(this).get(PendingOrderViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_me_pendingorder, container, false);
        Query query = pendingOrderViewModel.getPendingRecyclerDB();
        FirebaseRecyclerOptions<Order> options = new FirebaseRecyclerOptions.
                Builder<Order>().setQuery(query, Order.class).build();
        adapter = new OrderAdapter(options);
        RecyclerView recyclerView = root.findViewById(R.id.pendingorder_recyclerview);
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
