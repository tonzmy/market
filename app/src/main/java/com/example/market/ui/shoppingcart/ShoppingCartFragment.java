package com.example.market.ui.shoppingcart;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.market.R;
import com.example.market.adapter.ShoppingCartAdapter;
import com.example.market.models.Cart;
import com.example.market.ui.product.ProductDetailViewModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.Query;

public class ShoppingCartFragment extends Fragment {
    private final static String LOG_TAG = "shopping cart fragment";
    private ShoppingCartViewModel shoppingCartViewModel;
    private ShoppingCartAdapter shoppingCartAdapter;
    private double total;
    private TextView tvShoppingCartPrice;
    private Button buttonShoppingCartBuy;
    private SparseBooleanArray sparseBooleanArray;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        shoppingCartViewModel = new ViewModelProvider(this).get(ShoppingCartViewModel.class);
        total = shoppingCartViewModel.getTotal();
        final View root = inflater.inflate(R.layout.fragment_shoppingcart, container, false);

        tvShoppingCartPrice = root.findViewById(R.id.shopping_cart_price);
        buttonShoppingCartBuy = root.findViewById(R.id.shopping_cart_buy);
        final TextView textView = root.findViewById(R.id.text_notifications);
        recyclerView = root.findViewById(R.id.shopping_cart_recyclerview);
        linearLayout = root.findViewById(R.id.linear_layout_shoppingcart);

        if (shoppingCartViewModel.login()) {
            tvShoppingCartPrice.setText(Double.toString(total));
            Query query = shoppingCartViewModel.getShoppingCartDatabaseReference();
            FirebaseRecyclerOptions<Cart> options =
                    new FirebaseRecyclerOptions.Builder<Cart>().setQuery(query,Cart.class).build();
            shoppingCartAdapter = new ShoppingCartAdapter(options);
            linearLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(shoppingCartAdapter);

            shoppingCartAdapter.setOnCheckBoxClickListener(new ShoppingCartAdapter.OnCheckBoxClickListener() {
                @Override
                public void onCheckBoxClick(Cart cart, boolean checked) {

                    if (checked) {
                        total += Math.round(Double.parseDouble(cart.getCurrentUnitPrice())
                                * Integer.parseInt(cart.getQuantity()) * 100)/100.0;
                    } else {
                        total -= Math.round(Double.parseDouble(cart.getCurrentUnitPrice())
                                * Integer.parseInt(cart.getQuantity()) * 100)/100.0;
                    }
                    shoppingCartViewModel.setTotal(total);
                    total = shoppingCartViewModel.getTotal();
                    tvShoppingCartPrice.setText(Double.toString(total));

                    Snackbar.make(root, Double.toString(total), Snackbar.LENGTH_SHORT).show();
                }

            });

            shoppingCartAdapter.setOnButtonDeleteClickListener(new ShoppingCartAdapter.OnButtonDeleteClickListener() {
                @Override
                public void onButtonDeleteClick(Cart cart, boolean checked) {

                    shoppingCartViewModel.setTotal(0);
                    total = shoppingCartViewModel.getTotal();
                    tvShoppingCartPrice.setText(Double.toString(total));
                    shoppingCartAdapter.clearItemStateArray();
                    shoppingCartViewModel.delete(cart);
                }
            });

            shoppingCartAdapter.setOnButtonMinusClickListener(new ShoppingCartAdapter.OnButtonMinusClickListener() {
                @Override
                public void onButtonMinusClick(Cart cart, boolean checked) {
                    if (Integer.parseInt(cart.getQuantity()) > 1 && checked) {
//                        total -= Integer.parseInt(cart.getCurrentUnitPrice()) * (Integer.parseInt(cart.getQuantity()));
                        total -= Double.parseDouble(cart.getCurrentUnitPrice());
                        total = Math.round(total * 100) / 100.0;
                        shoppingCartViewModel.setTotal(total);
                        total = shoppingCartViewModel.getTotal();
                        shoppingCartViewModel.minusQuantity(cart.getCartId(), cart.getQuantity());
                        cart.setQuantity(Integer.toString(Integer.parseInt(cart.getQuantity())-1));
                        tvShoppingCartPrice.setText(Double.toString(total));
//                        total += Integer.parseInt(cart.getCurrentUnitPrice()) * (Integer.parseInt(cart.getQuantity()));
                    }
                    Snackbar.make(root, Double.toString(total), Snackbar.LENGTH_SHORT).show();
                }
            });

            shoppingCartAdapter.setOnButtonPlusClickListener(new ShoppingCartAdapter.OnButtonPlusClickListener() {
                @Override
                public void onButtonPlusClick(Cart cart, boolean checked) {
//                    total -= Integer.parseInt(cart.getCurrentUnitPrice()) * (Integer.parseInt(cart.getQuantity()));
                    if (checked) {
                        shoppingCartViewModel.plusQuantity(cart.getCartId(), cart.getQuantity());
                        cart.setQuantity(Integer.toString(Integer.parseInt(cart.getQuantity())+1));
//                    total += Integer.parseInt(cart.getCurrentUnitPrice()) * (Integer.parseInt(cart.getQuantity()));
                        total += Double.parseDouble(cart.getCurrentUnitPrice());
                        total = Math.round(total * 100) / 100.0;
                        shoppingCartViewModel.setTotal(total);
                        total = shoppingCartViewModel.getTotal();
                        tvShoppingCartPrice.setText(Double.toString(total));
                        Snackbar.make(root, Double.toString(total), Snackbar.LENGTH_SHORT).show();
                    }

                }
            });

        } else {
            textView.setText("you have not login in");
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonShoppingCartBuy = view.findViewById(R.id.shopping_cart_buy);
        buttonShoppingCartBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sparseBooleanArray = shoppingCartAdapter.getItemStateArray();
                for (int i = 0; i < sparseBooleanArray.size(); i++) {
                    if (sparseBooleanArray.get(i)) {
                        shoppingCartViewModel.placeOrder(shoppingCartAdapter.getCartAt(i));
                        total -= Math.round(Double.parseDouble(shoppingCartAdapter.getCartAt(i).getCurrentUnitPrice())
                                * Integer.parseInt(shoppingCartAdapter.getCartAt(i).getQuantity()) * 100)/100.0;
                        shoppingCartViewModel.setTotal(0);
                        total = shoppingCartViewModel.getTotal();
                        tvShoppingCartPrice.setText(Double.toString(total));
                        shoppingCartAdapter.clearItemStateArray();
                    }
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        if (shoppingCartViewModel.login()) {
            shoppingCartAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        if (shoppingCartViewModel.login()) {
            shoppingCartAdapter.stopListening();
        }
        super.onStop();
    }
}