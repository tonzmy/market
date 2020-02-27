package com.example.market.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.market.R;
import com.example.market.models.Product;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class ProductAdapter extends FirebaseRecyclerAdapter<Product, ProductAdapter.ProductHolder> {
    private static final String LOG_TAG = "product adapter";
    private OnItemClickListener listener;

    public ProductAdapter(@NonNull FirebaseRecyclerOptions<Product> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull ProductHolder holder, int position, @NonNull Product model) {
        if (model.getPromotionPrice() != null && model.getPromotionPrice().length() > 0) {
//         TODO: notice  promotion price
            holder.tv_price.setText(model.getPromotionPrice());
        } else {
            holder.tv_price.setText(model.getPrice());
        }

//        holder.tv_price.setText(model.getPrice());
        holder.tv_title.setText(model.getName());
        Picasso.get().load(model.getSnapshot()).into(holder.iv_snapshot);

    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductHolder(v);
    }

    class ProductHolder extends RecyclerView.ViewHolder {
        TextView tv_price;
        TextView tv_title;
        ImageView iv_snapshot;


        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            iv_snapshot = itemView.findViewById(R.id.product_snapshot);
            tv_title = itemView.findViewById(R.id.text_view_product_title);
            tv_price = itemView.findViewById(R.id.text_view_product_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }
    public void setOnItemClickLister(OnItemClickListener listener) {
        this.listener = listener;
    }
}

