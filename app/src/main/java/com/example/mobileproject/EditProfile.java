package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfile extends AppCompatActivity {

    private EditText nameEdit, mobileEdit, addressEdit;
    private ProgressBar progressBar;
    private ImageView image;
    private String name, phone, address,imageUri;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        progressBar = findViewById(R.id.prog);
        nameEdit = findViewById(R.id.editText_name);
        mobileEdit = findViewById(R.id.editText_phone);
        addressEdit = findViewById(R.id.editText_address);
        image = findViewById(R.id.no_profile_image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditProfile.this, UploadPic.class);
                startActivity(i);
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        showProfile(firebaseUser);

        edit = findViewById(R.id.new_edit_btn);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile(firebaseUser);
            }
        });

    }


    private void updateProfile(FirebaseUser firebaseUser){

        String phoneRegex = "[0][1][0,1,2,5][0-9]{8}";
        Matcher phoneMatcher;
        Pattern mobilePattern = Pattern.compile(phoneRegex);
        phoneMatcher = mobilePattern.matcher(phone);



        if (TextUtils.isEmpty(name)){
            nameEdit.setError("Name is required");
            nameEdit.requestFocus();
        }  else if (TextUtils.isEmpty(phone)){
            mobileEdit.setError("Phone is required");
            mobileEdit.requestFocus();
        } else if(phone.length() != 11){
            mobileEdit.setError("Please enter a valid phone number");
            mobileEdit.requestFocus();
        } else if (!phoneMatcher.find()) {
            mobileEdit.setError("Please enter a valid phone number");
            mobileEdit.requestFocus();
        } else {
            name = nameEdit.getText().toString();
            phone = mobileEdit.getText().toString();
            address = addressEdit.getText().toString();


            HashMap<String, Object> userInfo = new HashMap<>();
            userInfo.put("phone",phone);
            userInfo.put("address",address);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            String userID = firebaseUser.getUid();
            progressBar.setVisibility(View.VISIBLE);

            reference.child(userID).updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                        firebaseUser.updateProfile(profileChangeRequest);

                        Toast.makeText(EditProfile.this, " Updated Successfully ", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(EditProfile.this, profile.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    } else {
                        try {
                            throw task.getException();
                        } catch (Exception e){
                            Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    progressBar.setVisibility(View.GONE);
                }
            });

        }
    }


    private void showProfile(FirebaseUser firebaseUser){
        String userId = firebaseUser.getUid();

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");

        progressBar.setVisibility(View.VISIBLE);

        referenceProfile.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readWriteUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if( readWriteUserDetails != null){
                    name = firebaseUser.getDisplayName();
                    phone = readWriteUserDetails.phone;
                    address = readWriteUserDetails.address;
                    imageUri = readWriteUserDetails.image;
                    nameEdit.setText(name);
                    mobileEdit.setText(phone);
                    addressEdit.setText(address);
                    Picasso.get().load(imageUri).into(image);
                } else {
                    Toast.makeText(EditProfile.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfile.this, " Something went wrong!", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}