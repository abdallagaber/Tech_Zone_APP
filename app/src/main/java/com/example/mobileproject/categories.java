package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class categories extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ConstraintLayout laptopCat;
    ConstraintLayout pcCat;
    ConstraintLayout acCat;
    ConstraintLayout comCat;
    String cat_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.categories);
        laptopCat = findViewById(R.id.laptop_category);
        pcCat = findViewById(R.id.pc_category);
        acCat = findViewById(R.id.ac_category);
        comCat = findViewById(R.id.com_category);


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
                        startActivity(new Intent(getApplicationContext(),MainOfContact.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });

        laptopCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat_product = "Laptop";
                Intent intent = new Intent(categories.this,categoryShow.class);
                intent.putExtra("category",cat_product);
                startActivity(intent);
            }
        });

        pcCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat_product = "PC";
                Intent intent = new Intent(categories.this,categoryShow.class);
                intent.putExtra("category",cat_product);
                startActivity(intent);
            }
        });

        acCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat_product = "Accessories";
                Intent intent = new Intent(categories.this,categoryShow.class);
                intent.putExtra("category",cat_product);
                startActivity(intent);
            }
        });

        comCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat_product = "PC Components";
                Intent intent = new Intent(categories.this,categoryShow.class);
                intent.putExtra("category",cat_product);
                startActivity(intent);
            }
        });

    }
}