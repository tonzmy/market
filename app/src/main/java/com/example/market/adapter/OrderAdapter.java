package com.example.market.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.market.R;
import com.example.market.models.Order;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.*;
import com.squareup.picasso.Picasso;

import static com.example.market.prevalent.Prevalent.getPhone;

public class OrderAdapter extends FirebaseRecyclerAdapter<Order, OrderAdapter.OrderHolder>{
    private static final String LOG_TAG = "order adapter";

    public OrderAdapter(@NonNull FirebaseRecyclerOptions<Order> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final OrderHolder holder, int position, @NonNull final Order model) {

        if (model.getStatus().equals("pending")) {
            holder.button_cancel.setText("cancel");
            holder.button_cancel.setVisibility(View.VISIBLE);

            holder.button_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference().child("order").child(getPhone()).child(model.getOrderId()).child("status").setValue("cancelled");
                    Toast.makeText(v.getContext(), "cancel", Toast.LENGTH_LONG).show();
                }
            });
        }

//        holder.tv_description.setText(model.getProduct().getDescription());
        holder.tv_title.setText(model.getProduct().getName());
        Picasso.get().load(model.getProduct().getSnapshot()).into(holder.iv_snapshot);
//        final DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("product").child(model.getProductId());
//        db.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Product product = dataSnapshot.getValue(Product.class);
//                holder.tv_description.setText(product.getDescription());
//                holder.tv_title.setText(product.getName());
//                Picasso.get().load(product.getSnapshot()).into(holder.iv_snapshot);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        holder.tv_status.setText(model.getStatus());
//        holder.tv_productId.setText(model.getProductId());
//        Picasso.get().load(model.getSnapshot()).into(holder.iv_snapshot);
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderHolder(v);
    }



    class OrderHolder extends RecyclerView.ViewHolder {
//        TextView tv_productId;
        TextView tv_title;
        TextView tv_status;
//        TextView tv_description;
        ImageView iv_snapshot;
        Button button_cancel;


        public OrderHolder(@NonNull View itemView) {
            super(itemView);
//            tv_productId = itemView.findViewById(R.id.text_view_productId);
            iv_snapshot = itemView.findViewById(R.id.order_snapshot);
            tv_status = itemView.findViewById(R.id.text_view_order_status);
            button_cancel = itemView.findViewById(R.id.button_cancel);
//            tv_description = itemView.findViewById(R.id.text_view_order_description);
            tv_title = itemView.findViewById(R.id.text_view_order_title);
        }
    }

}

