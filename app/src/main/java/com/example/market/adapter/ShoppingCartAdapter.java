package com.example.market.adapter;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.example.market.R;
import com.example.market.models.Cart;
import com.example.market.ui.product.ProductDetailViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;
//TODO: diff callback
public class ShoppingCartAdapter extends FirebaseRecyclerAdapter<Cart, ShoppingCartAdapter.ShoppingCartHolder> {
    private SparseBooleanArray itemStateArray = new SparseBooleanArray();
    private OnCheckBoxClickListener listener;
    private OnButtonMinusClickListener onButtonMinusClickListener;
    private OnButtonPlusClickListener onButtonPlusClickListener;
    private OnButtonDeleteClickListener onButtonDeleteClickListener;

    public SparseBooleanArray getItemStateArray() {
        return itemStateArray;
    }

    public void clearItemStateArray() {
        itemStateArray.clear();
    }

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ShoppingCartAdapter(@NonNull FirebaseRecyclerOptions<Cart> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ShoppingCartHolder holder, final int position, @NonNull Cart model) {


//        TODO: promotion price
        Picasso.get().load(model.getSnapshot()).into(holder.ivShoppingCartSnapshot);
        holder.tvShoppingCartTitle.setText(model.getName());
        holder.tvShoppingCartPrice.setText(model.getCurrentUnitPrice());
        holder.tvShoppingCartQuantity.setText(model.getQuantity());

        if (!itemStateArray.get(position, false)) {
            holder.checkBoxShoppingCartSelected.setChecked(false);
        } else {
            holder.checkBoxShoppingCartSelected.setChecked(true);
        }
    }

    @NonNull
    @Override
    public ShoppingCartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ShoppingCartHolder(root);
    }

    public Cart getCartAt(int position) {
        return getItem(position);
    }

    class ShoppingCartHolder extends RecyclerView.ViewHolder {
        ImageView ivShoppingCartSnapshot;
        TextView tvShoppingCartTitle;
        TextView tvShoppingCartPrice;
        TextView tvShoppingCartQuantity;
        Button buttonShoppingCartMinus;
        Button buttonShoppingCartPlus;
        Button buttonShoppingCartDelete;
        CheckBox checkBoxShoppingCartSelected;



            public ShoppingCartHolder(@NonNull View itemView) {
            super(itemView);
            ivShoppingCartSnapshot = itemView.findViewById(R.id.shopping_cart_snapshot);
            tvShoppingCartTitle = itemView.findViewById(R.id.text_view_shopping_cart_title);
            tvShoppingCartPrice = itemView.findViewById(R.id.text_view_shopping_cart_price);
            tvShoppingCartQuantity = itemView.findViewById(R.id.text_vew_shopping_cart_quantity);
            buttonShoppingCartMinus = itemView.findViewById(R.id.button_shopping_cart_minus);
            buttonShoppingCartPlus = itemView.findViewById(R.id.button_shopping_cart_plus);
            buttonShoppingCartDelete = itemView.findViewById(R.id.button_shopping_cart_delete);
            checkBoxShoppingCartSelected = itemView.findViewById(R.id.check_box_shopping_cart);

            buttonShoppingCartDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onButtonDeleteClickListener != null && position != RecyclerView.NO_POSITION) {
                        onButtonDeleteClickListener.onButtonDeleteClick(getItem(position), checkBoxShoppingCartSelected.isChecked());                    }
                }
            });


            buttonShoppingCartMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onButtonMinusClickListener != null && position != RecyclerView.NO_POSITION) {
                        onButtonMinusClickListener.onButtonMinusClick(getItem(position), checkBoxShoppingCartSelected.isChecked());
                    }
                }
            });

            buttonShoppingCartPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onButtonPlusClickListener != null && position != RecyclerView.NO_POSITION) {
                        onButtonPlusClickListener.onButtonPlusClick(getItem(position), checkBoxShoppingCartSelected.isChecked());
                    }
                }
            });

            checkBoxShoppingCartSelected.setOnClickListener(new CheckBox.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        if (!itemStateArray.get(position, false)) {
                            checkBoxShoppingCartSelected.setChecked(true);
                            itemStateArray.put(position, true);
                        }
                        else  {
                            checkBoxShoppingCartSelected.setChecked(false);
                            itemStateArray.put(position, false);
                        }
                        listener.onCheckBoxClick(getItem(position), checkBoxShoppingCartSelected.isChecked());
                    }
                }
            });

        }
    }

    public interface OnCheckBoxClickListener {
        void onCheckBoxClick(Cart cart, boolean checked);
    }

    public void setOnCheckBoxClickListener(OnCheckBoxClickListener listener) {
        this.listener = listener;
    }

    public interface OnButtonMinusClickListener {
        void onButtonMinusClick(Cart cart, boolean checked);
    }

    public void setOnButtonMinusClickListener(OnButtonMinusClickListener onButtonMinusClickListener) {
        this.onButtonMinusClickListener = onButtonMinusClickListener;
    }

    public interface OnButtonPlusClickListener {
        void onButtonPlusClick(Cart cart, boolean checked);
    }

    public void setOnButtonPlusClickListener(OnButtonPlusClickListener onButtonPlusClickListener) {
        this.onButtonPlusClickListener = onButtonPlusClickListener;
    }

    public interface OnButtonDeleteClickListener {
        void onButtonDeleteClick(Cart cart, boolean checked);
    }

    public void setOnButtonDeleteClickListener(OnButtonDeleteClickListener onButtonDeleteClickListener) {
        this.onButtonDeleteClickListener = onButtonDeleteClickListener;
    }
}
