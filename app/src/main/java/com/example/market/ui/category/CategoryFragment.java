package com.example.market.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import com.example.market.R;

public class CategoryFragment extends Fragment {

    private CategoryViewModel categoryViewModel;
    private Button buttonShoe;
    private Button buttonProduce;
    private Button buttonElectronics;
    private Button buttonGrocery;
    private SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_category, container, false);
        buttonShoe = root.findViewById(R.id.button_category_shoe);
        buttonProduce = root.findViewById(R.id.button_category_produce);
        buttonElectronics = root.findViewById(R.id.button_category_electronics);
        buttonGrocery = root.findViewById(R.id.button_category_grocery);
        searchView = root.findViewById(R.id.search_view);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Bundle bundle = new Bundle();
                bundle.putString("from", "home");
                bundle.putString("search", s);
//                Toast.makeText(getContext(), Integer.toString(query.length()), Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.navigation_search_result, bundle);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        buttonShoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("from", "category");
                bundle.putString("category", "shoe");
                navController.navigate(R.id.navigation_search_result, bundle);

            }
        });

        buttonProduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("from", "category");
                bundle.putString("category", "produce");
                navController.navigate(R.id.navigation_search_result, bundle);
            }
        });

        buttonElectronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("from", "category");
                bundle.putString("category", "electronics");
                navController.navigate(R.id.navigation_search_result, bundle);
            }
        });

        buttonGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("from", "category");
                bundle.putString("category", "grocery");
                navController.navigate(R.id.navigation_search_result, bundle);
            }
        });
    }
}
