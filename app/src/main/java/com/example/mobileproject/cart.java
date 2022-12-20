package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileproject.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class cart extends AppCompatActivity {
    private RecyclerView recyclerview;
    private RecyclerView.LayoutManager layoutmanager;
    private Button checkout;
    private TextView totalPrice, total;
    private DatabaseReference productRef;
    private int overTotalPrice = 0;
    private Dialog orderDone;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerview = findViewById(R.id.cartLap);
        recyclerview.setHasFixedSize(true);
        layoutmanager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutmanager);

        checkout = findViewById(R.id.checkout);
        total = findViewById(R.id.price);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        productRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("Cart");

        orderDone = new Dialog(this);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button done;
                orderDone.setContentView(R.layout.pop_up_checkout);
                done = orderDone.findViewById(R.id.button_done);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        orderDone.dismiss();
                    }
                });
                total.setText("0 LE");
                productRef.removeValue();
                orderDone.show();

            }
        });



        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.cart);

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
                        finish();
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),profile.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(),cart.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.contactUs:
                        startActivity(new Intent(getApplicationContext(),MainOfContact.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }
    protected void onStart(){
        super.onStart();

        FirebaseRecyclerOptions<product> options =
                new FirebaseRecyclerOptions.Builder<product>().setQuery(productRef.orderByChild("name"),product.class).build();

        FirebaseRecyclerAdapter<product, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<product, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull product model) {
                int price = ((Integer.valueOf(model.getPrice())));
                overTotalPrice = overTotalPrice+price;
                total.setText(overTotalPrice+" LE");
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(cart.this , productInfo.class);
//                        intent.putExtra("id" , model.getId());
//                        startActivity(intent);
//                    }
//                });

                holder.txtProductName.setText(model.getName());
                holder.txtProductPrice.setText(model.getPrice()+" LE");
                Picasso.get().load(model.getImage()).into(holder.productImage);
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        productRef.child(model.getId()).removeValue();
                        overTotalPrice = overTotalPrice-price;
                        total.setText(overTotalPrice+" LE");
                    }
                });


            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items2,parent,false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }
        };
        recyclerview.setAdapter(adapter);
        adapter.startListening();


    }

    public void onBackPressed(){
        Intent i = new Intent(this,HomeActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
    }
}