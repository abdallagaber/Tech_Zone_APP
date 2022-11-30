package com.example.mobileproject;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class LoginAvtivity extends AppCompatActivity {
    EditText editTextLoginEmail, editTextLoginPassword;
    FirebaseAuth authProfile;
    private static final String TAG = "LoginActivity";

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_avtivity);
        editTextLoginEmail = findViewById(R.id.editTextRegEmail);
        editTextLoginPassword = findViewById(R.id.editTextRegPass);

        authProfile = FirebaseAuth.getInstance();

        FrameLayout buttonLogin = findViewById(R.id.SignUpBtn);
        ProgressBar progressBar = findViewById(R.id.SignUpProgress);
        TextView textView = findViewById(R.id.text);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = editTextLoginEmail.getText().toString();
                String textPassword = editTextLoginPassword.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                        textView.setVisibility(View.VISIBLE);

                    }
                }, 1200);


                if (TextUtils.isEmpty(textEmail)) {
                    editTextLoginEmail.setError("E-mail is required");
                    editTextLoginEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    editTextLoginEmail.setError("Enter a valid E-mail ");
                    editTextLoginEmail.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)) {
                    editTextLoginPassword.setError("Password is required");
                    editTextLoginPassword.requestFocus();
                } else {
                    loginUser(textEmail, textPassword);
                }
            }
        });

    }

    private void loginUser(String textEmail, String textPassword) {
        authProfile.signInWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginAvtivity.this, "Logined successfuly", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginAvtivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        editTextLoginEmail.setError("User doesn't exist! please sign up");
                        editTextLoginEmail.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        editTextLoginPassword.setError("Password is incorrect. kindly,try again");
                        editTextLoginPassword.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(LoginAvtivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void signin(View view) {
        List<AuthUI.IdpConfig> providers = Arrays.asList(

                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

// Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        signInLauncher.launch(signInIntent);

    }


    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else {

        }
    }
}