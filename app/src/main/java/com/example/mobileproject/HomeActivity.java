package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
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

import java.util.Locale;

public class HomeActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;


    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference productRef;
    private ProgressBar progressBar;
    private RecyclerView recyclerview;
    RecyclerView.LayoutManager layoutmanager;
    SearchView search;
    String searchInput;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressBar = findViewById(R.id.progressBar);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);
        search = findViewById(R.id.searchView);
        productRef = FirebaseDatabase.getInstance().getReference().child("Products");


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchInput = search.getQuery().toString();
                onStart();
                return false;
            }
        });

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

        recyclerview = findViewById(R.id.lap);
        recyclerview.setHasFixedSize(true);
        layoutmanager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerview.setLayoutManager(layoutmanager);

    }


    public void onBackPressed(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
        alertDialog.setTitle("Exit App");
        alertDialog.setMessage("Do you want to exit app?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    protected void onStart(){
        super.onStart();

        FirebaseRecyclerOptions<product> options =
                new FirebaseRecyclerOptions.Builder<product>().setQuery(productRef.orderByChild("name").startAt(searchInput).endAt(searchInput+"\uf8ff"),product.class).build();

        FirebaseRecyclerAdapter<product, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<product, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull product model) {

                    holder.txtProductName.setText(model.getName());
                    holder.txtProductPrice.setText(model.getPrice()+" LE");
                    Picasso.get().load(model.getImage()).into(holder.productImage);
                    progressBar.setVisibility(View.GONE);

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