package com.example.market.ui.home;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.market.R;
import com.example.market.adapter.ProductAdapter;
import com.example.market.models.Product;
import com.example.market.ui.search.SearchResultViewModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ProductAdapter productAdapter;
    private Toolbar toolbar;
    private TextView tvToolBar;
    private RecyclerView recyclerView;
    private SearchView searchView;
    EditText editText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
//         editText = root.findViewById(R.id.search_text);
//        tvToolBar = getActivity().findViewById(R.id.toolbar_text);
//        tvToolBar.setText("Home");


        Query query = homeViewModel.getProductDatabaseReference();
        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>().setQuery(query, Product.class).build();

        productAdapter = new ProductAdapter(options);
        recyclerView = root.findViewById(R.id.home_recyclerview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(productAdapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = Navigation.findNavController(view);
//        searchView = view.findViewById(R.id.search_view);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                Bundle bundle = new Bundle();
//                bundle.putString("from", "home");
//                bundle.putString("search", s);
////                Toast.makeText(getContext(), Integer.toString(query.length()), Toast.LENGTH_SHORT).show();
//                navController.navigate(R.id.navigation_search_result, bundle);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

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

//        searchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String query = editText.getText().toString();
//                Bundle bundle = new Bundle();
//                bundle.putString("from", "home");
//                bundle.putString("search", query);
//////                Toast.makeText(getContext(), Integer.toString(query.length()), Toast.LENGTH_SHORT).show();
//                navController.navigate(R.id.navigation_search_result, bundle);
//            }
//        });

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