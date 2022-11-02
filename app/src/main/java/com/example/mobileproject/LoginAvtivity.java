package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.regex.Pattern;

public class LoginAvtivity extends AppCompatActivity {
    EditText editTextLoginEmail , editTextLoginPassword;
    FirebaseAuth authProfile;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_avtivity);
        editTextLoginEmail = findViewById(R.id.editTextRegEmail);
        editTextLoginPassword = findViewById(R.id.editTextRegPass);

        authProfile = FirebaseAuth.getInstance();

        Button buttonLogin = findViewById(R.id.buttonRegSignup);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = editTextLoginEmail.getText().toString();
                String textPassword = editTextLoginPassword.getText().toString();

                if(TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(LoginAvtivity.this, "please enter your email", Toast.LENGTH_LONG).show();
                    editTextLoginEmail.setError("E-mail is required");
                    editTextLoginEmail.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(LoginAvtivity.this, "please enter your email again", Toast.LENGTH_LONG).show();
                    editTextLoginEmail.setError("Enter a valid E-mail ");
                    editTextLoginEmail.requestFocus();
                }else if(TextUtils.isEmpty(textPassword)){
                    Toast.makeText(LoginAvtivity.this, "please enter your password", Toast.LENGTH_LONG).show();
                    editTextLoginPassword.setError("Password is required");
                    editTextLoginPassword.requestFocus();
                }else{
                    loginUser(textEmail,textPassword);


                }
            }
        });

    }

    private void loginUser(String textEmail, String textPassword) {
        authProfile.signInWithEmailAndPassword(textEmail,textPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginAvtivity.this , "Logined successfuly" , Toast.LENGTH_LONG).show();
                }else{
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        editTextLoginEmail.setError("User doesn't exist! please sign up");
                        editTextLoginEmail.requestFocus();
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        editTextLoginEmail.setError("Invalid credentials . kindly, try again");
                        editTextLoginEmail.requestFocus();
                    }catch(Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(LoginAvtivity.this , e.getMessage() , Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}