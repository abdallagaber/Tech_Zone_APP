package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileproject.ViewHolder.ProductViewHolder;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.*;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {
BottomNavigationView bottomNavigationView;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference productRef;
    private RecyclerView recyclerview;
    RecyclerView.LayoutManager layoutmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.categories:
                        startActivity(new Intent(getApplicationContext(),categories.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(),cart.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.contactUs:
                        startActivity(new Intent(getApplicationContext(),contactUs.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        recyclerview = findViewById(R.id.lap);
        recyclerview.setHasFixedSize(true);
        layoutmanager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerview.setLayoutManager(layoutmanager);

    }

    protected void onStart(){
        super.onStart();

        FirebaseRecyclerOptions<product> options =
                new FirebaseRecyclerOptions.Builder<product>().setQuery(productRef,product.class).build();

        FirebaseRecyclerAdapter<product, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<product, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull product model) {
                holder.txtProductName.setText(model.getName());
                holder.txtProductPrice.setText("EGP "+model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.productImage);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeActivity.this,productInfo.class);
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
        recyclerview.setAdapter(adapter);
        adapter.startListening();

    }
}