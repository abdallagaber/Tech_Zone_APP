package com.example.mobileproject;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private EditText editTextFName,editTextLName,editTextEmail,editTextPhone,editTextPass,editTextConfirmPass;
    private static final String TAG = "SignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextFName = findViewById(R.id.editTextRegFname);
        editTextLName = findViewById(R.id.editTextRegLname);
        editTextPhone = findViewById(R.id.editTextRegPhone);
        editTextEmail = findViewById(R.id.editTextRegEmail);
        editTextPass = findViewById(R.id.editTextRegPass);
        editTextConfirmPass = findViewById(R.id.editTextRegConfirmPass);


        FrameLayout frameLayout = findViewById(R.id.SignUpBtn);
        ProgressBar progressBar = findViewById(R.id.SignUpProgress);
        TextView textView = findViewById(R.id.text);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = editTextFName.getText().toString();
                String lastName = editTextLName.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editTextPhone.getText().toString();
                String pass = editTextPass.getText().toString();
                String confirmPass = editTextConfirmPass.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                        textView.setVisibility(View.VISIBLE);

                    }
                },800);


                String phoneRegex = "[0][1][0,1,2,5][0-9]{8}";
                Matcher phoneMatcher;
                Pattern mobilePattern = Pattern.compile(phoneRegex);
                phoneMatcher = mobilePattern.matcher(phone);



                if (TextUtils.isEmpty(firstName)){
                    editTextFName.setError("First Name is required");
                    editTextFName.requestFocus();
                } else if (TextUtils.isEmpty(lastName)){
                    editTextLName.setError("Last Name is required");
                    editTextLName.requestFocus();
                } else if (TextUtils.isEmpty(email)){
                    editTextEmail.setError("Email is required");
                    editTextEmail.requestFocus();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editTextEmail.setError("Valid Email is required");
                    editTextEmail.requestFocus();
                }  else if (TextUtils.isEmpty(phone)){
                    editTextPhone.setError("Phone is required");
                    editTextPhone.requestFocus();
                } else if(phone.length() != 11){
                    editTextPhone.setError("Please enter a valid phone number");
                    editTextPhone.requestFocus();
                } else if (!phoneMatcher.find()){
                    editTextPhone.setError("Please enter a valid phone number");
                    editTextPhone.requestFocus();
                } else if (TextUtils.isEmpty(pass)){
                    editTextPass.setError("Password is required");
                    editTextPass.requestFocus();
                } else if (TextUtils.isEmpty(confirmPass)){
                    editTextConfirmPass.setError("Password Confirmation is required");
                    editTextConfirmPass.requestFocus();
                } else if (pass.length() < 8){
                    editTextPass.setError("Password is weak");
                    editTextPass.requestFocus();
                }  else if (!pass.equals(confirmPass)){
                    editTextConfirmPass.setError("Password Confirmation is required");
                    editTextConfirmPass.requestFocus();
                } else {
                    firstName = firstName.replaceAll("\\s", "");
                    firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
                    lastName = lastName.replaceAll("\\s", "");
                    lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
                    registerUser(firstName,lastName,phone,email,pass);
                }


            }
        });


    }

    private void registerUser(String firstName, String lastName, String phone, String email, String pass) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){



                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(firstName+" "+lastName).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(firstName, lastName,phone," "," ");

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    reference.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(SignUp.this, "User registered succesfully", Toast.LENGTH_LONG).show();
                            finish();

                        }});
                }else {
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        editTextPass.setError("Your password is too weak. Kindly use a mix of alphabets, numbers and special characters");
                        editTextPass.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        editTextEmail.setError("Your email is invalid or already in use, Kindly re-enter");
                        editTextEmail.requestFocus();
                    }catch (FirebaseAuthUserCollisionException e){
                        editTextEmail.setError("User is already registered with this email, Use another email");
                        editTextEmail.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(SignUp.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}