package com.example.market.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.market.R;
import com.example.market.adapter.ProductAdapter;
import com.example.market.models.Product;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;

public class SearchResultFragment extends Fragment {
    private final static String LOG_TAG  = "search result fragment";
    SearchResultViewModel searchResultViewModel;
    ProductAdapter productAdapter;
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchResultViewModel = new ViewModelProvider(this).get(SearchResultViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_result, container, false);

        recyclerView = root.findViewById(R.id.result_recyclerview);
        String from = getArguments().getString("from");
        String typing;
        if (from == "home") {
            typing = getArguments().getString("search");
        } else {
            typing = getArguments().getString("category");
        }
//        Toast.makeText(getContext(), typing, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), from, Toast.LENGTH_SHORT).show();
        Query query = searchResultViewModel.getProduct(typing, from);
        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>().setQuery(query, Product.class).build();

        productAdapter = new ProductAdapter(options);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(productAdapter);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        productAdapter.setOnItemClickLister(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                Bundle bundle = new Bundle();
                bundle.putString("name", product.getName());
                bundle.putString("description", product.getDescription());
                bundle.putString("price", product.getPrice());
                bundle.putString("productId", product.getProductId());
                bundle.putString("snapshot", product.getSnapshot());
                if (product.getPromotionPrice() != null && product.getPromotionPrice().length() > 0) {
                    bundle.putString("promotionPrice", product.getPromotionPrice());
                }
//                navController.navigate(R.id.navigation_product_detail);
                navController.navigate(R.id.navigation_product_detail, bundle);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        productAdapter.startListening();
    }

    @Override
    public void onStop() {
        productAdapter.stopListening();
        super.onStop();
    }
}
