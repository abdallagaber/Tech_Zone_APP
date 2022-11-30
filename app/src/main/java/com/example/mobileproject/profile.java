package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {
BottomNavigationView bottomNavigationView;
    private FirebaseAuth auth;
    private Button btnsignout;
    private TextView textFullName, textEmail, textMobile, textAddress;
    private String name, email, phone, address;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textFullName = findViewById(R.id.name);
        textEmail = findViewById(R.id.email);
        textMobile = findViewById(R.id.mobile);
        textAddress = findViewById(R.id.address);

        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser == null){
            Toast.makeText(profile.this, "Something went wrong, no details are found.", Toast.LENGTH_LONG).show();
        } else {
            showUserProfile(firebaseUser);
        }


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.profile);

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
                        startActivity(new Intent(getApplicationContext(),contactUs.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });


        auth = FirebaseAuth.getInstance();
        btnsignout = findViewById(R.id.signOutButton);

        btnsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(profile.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                Toast.makeText(profile.this, "Signed out Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onBackPressed(){
        Intent i = new Intent(this,HomeActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
    }


    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readWriteUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if ( readWriteUserDetails != null){
                    name = firebaseUser.getDisplayName();
                    email = firebaseUser.getEmail();
                    phone = readWriteUserDetails.phone;
                    address = readWriteUserDetails.address;
                    textFullName.setText(name);
                    textEmail.setText(email);
                    textMobile.setText(phone);
                    textAddress.setText(address);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(profile.this, "Something went wrong!", Toast.LENGTH_LONG).show();

            }
        });

    }


}