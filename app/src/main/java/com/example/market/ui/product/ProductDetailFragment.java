package com.example.market.ui.product;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.market.R;
import com.example.market.models.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

public class ProductDetailFragment extends Fragment {
    TextView tvProductName;
    TextView tvProductPrice;
    TextView tvProductDescription;
    Button buttonProductAddToCart;
    private Toolbar toolbar;
    private TextView tvToolBar;
    ImageButton imageButton;
    ImageView ivDetailImage;
    ProductDetailViewModel productDetailViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        productDetailViewModel = new ViewModelProvider(getActivity()).get(ProductDetailViewModel.class);
        View root = inflater.inflate(R.layout.fragment_product_detail, container, false);
        tvProductName = root.findViewById(R.id.text_view_product_detail_name);
        tvProductPrice = root.findViewById(R.id.text_view_product_detail_price);
        tvProductDescription = root.findViewById(R.id.text_view_product_detail_description);
        toolbar = getActivity().findViewById(R.id.toolbar);
        tvToolBar = getActivity().findViewById(R.id.toolbar_text);
        ivDetailImage = root.findViewById(R.id.image_view_product_detail_image);
        imageButton = root.findViewById(R.id.image_button_product_detail);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

//        toolbar.getBackground().setAlpha(50);

//        getActivity().getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        getActivity().getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT );

        tvToolBar.setText("detail");
        tvProductName.setText(getArguments().getString("name"));
        tvProductDescription.setText(getArguments().getString("description"));
        tvProductPrice.setText(getArguments().getString("price"));
        productDetailViewModel.setPrice(Float.parseFloat(getArguments().getString("price")));


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonProductAddToCart = view.findViewById(R.id.button_product_deatil_add_to_cart);
        buttonProductAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
                Button buttonMinus = view.findViewById(R.id.bottom_sheet_minus);
                Button buttonPlus = view.findViewById(R.id.bottom_sheet_plus);
                Button buttonConfirm = view.findViewById(R.id.bottom_sheet_confirm);

                final TextView tvTotal = view.findViewById(R.id.bottom_sheet_total);
                final TextView tvQuantity = view.findViewById(R.id.bottom_sheet_quantity);


                tvQuantity.setText(Integer.toString(productDetailViewModel.getQuantity()));
                tvTotal.setText(Double.toString(productDetailViewModel.getTotal()));

                final BottomSheetDialog dialog = new BottomSheetDialog(getContext());
                dialog.setContentView(view);
                dialog.show();

                buttonMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (productDetailViewModel.getQuantity() > 1) {
                            productDetailViewModel.minusQuantity();
                            tvQuantity.setText(Integer.toString(productDetailViewModel.getQuantity()));
                            tvTotal.setText(Double.toString(productDetailViewModel.getTotal()));
                        }
                    }
                });

                buttonPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productDetailViewModel.plusQuantity();
                        tvQuantity.setText(Integer.toString(productDetailViewModel.getQuantity()));
                        tvTotal.setText(Double.toString(productDetailViewModel.getTotal()));
                    }
                });



                buttonConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (productDetailViewModel.login()) {
                            String productId = getArguments().getString("productId");
                            String name = getArguments().getString("name");
                            String price;
                            if (getArguments().getString("promotionPrice") != null &&
                                    getArguments().getString("promotionPrice").length() > 0) {
                                price = getArguments().getString("promotionPrice");
                            }else {
                                price = getArguments().getString("price");
                            }
                            String quantity = Integer.toString(productDetailViewModel.getQuantity());
                            String snapshot = getArguments().getString("snapshot");
                            productDetailViewModel.addToCart(productId, name, price, quantity, snapshot);
                            dialog.cancel();
//                            TODO: live data -> oncomplete
                            Toast.makeText(getContext(), "Added to your shopping cart", Toast.LENGTH_SHORT).show();
                        } else {
                            final NavController navController = Navigation.findNavController(getView());
                            dialog.cancel();
                            navController.navigate(R.id.navigation_me_login);
                            Toast.makeText(getContext(), "Please login first", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
    }

}
