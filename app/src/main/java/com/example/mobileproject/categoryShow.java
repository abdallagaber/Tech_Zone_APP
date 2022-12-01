package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.mobileproject.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class categoryShow extends AppCompatActivity {

    String productCategory;

    private DatabaseReference productRef;

    RecyclerView precyclerview;
    RecyclerView.LayoutManager layoutmanager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_show);

        productCategory = getIntent().getStringExtra("category");


        productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        precyclerview = findViewById(R.id.lap_cat);
        precyclerview.setHasFixedSize(true);
        layoutmanager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        precyclerview.setLayoutManager(layoutmanager);



    }

    protected void onStart(){
        super.onStart();

        FirebaseRecyclerOptions<product> options =
                new FirebaseRecyclerOptions.Builder<product>()
                        .setQuery(productRef.orderByChild("category").equalTo(productCategory),product.class).build();

        FirebaseRecyclerAdapter<product, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<product, ProductViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull product model) {

                holder.txtProductName.setText(model.getName());
                holder.txtProductPrice.setText("EGP "+model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.productImage);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(categoryShow.this,productInfo.class);
                        intent.putExtra("id",model.getId());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items,parent,false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }
        };
        precyclerview.setAdapter(adapter);
        adapter.startListening();


    }
}