package com.example.market.ui.me.order.cancelledorder;

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

public class CancelledOrderFragment extends Fragment {
    private CancelledOrderViewModel cancelledOrderViewModel;
    private OrderAdapter adapter;

    public static CancelledOrderFragment newInstance() {
        CancelledOrderFragment cancelledOrderFragment = new CancelledOrderFragment();
        return cancelledOrderFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cancelledOrderViewModel = new ViewModelProvider(this).get(CancelledOrderViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_me_cancelledorder, container, false);
        Query query = cancelledOrderViewModel.getRecyclerDB();
        FirebaseRecyclerOptions<Order> options = new FirebaseRecyclerOptions.
                Builder<Order>().setQuery(query, Order.class).build();

        adapter = new OrderAdapter(options);

        RecyclerView recyclerView = root.findViewById(R.id.cancelled_order_recyclerview);
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
