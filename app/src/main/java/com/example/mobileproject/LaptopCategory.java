package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobileproject.ViewHolder.ProductViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.*;
import com.squareup.picasso.Picasso;


public class LaptopCategory extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference productRef;
    private RecyclerView recyclerview;
    RecyclerView.LayoutManager layoutmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop_category);



        productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        recyclerview = findViewById(R.id.lap);
        recyclerview.setHasFixedSize(true);
        layoutmanager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutmanager);
    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseRecyclerOptions<product> options =
                new FirebaseRecyclerOptions.Builder<product>().setQuery(productRef,product.class).build();

        FirebaseRecyclerAdapter<product, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<product, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull product model) {
                holder.txtProductName.setText(model.getName());
                holder.txtProductPrice.setText(model.getPrice()+" LE");
                Picasso.get().load(model.getImage()).into(holder.productImage);
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items,parent,false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }
        };
        recyclerview.setAdapter(adapter);
        adapter.startListening();

    }



}