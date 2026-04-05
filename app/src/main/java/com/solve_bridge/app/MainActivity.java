package com.solve_bridge.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvRegister, forgotPassword;
    ImageView showPassword;
    boolean isPasswordVisible = false;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        // Connect XML with Java
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        forgotPassword = findViewById(R.id.forgotPassword);
        showPassword = findViewById(R.id.showPassword);

        // Set initial icon
        showPassword.setImageResource(R.drawable.ic_eye_off);

        // Show/Hide Password Toggle
        showPassword.setOnClickListener(v -> {
            if (isPasswordVisible) {
                // Hide Password
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                showPassword.setImageResource(R.drawable.ic_eye_off);
                isPasswordVisible = false;
            } else {
                // Show Password
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                showPassword.setImageResource(R.drawable.ic_eye);
                isPasswordVisible = true;
            }
            // Move cursor to the end of the text
            etPassword.setSelection(etPassword.getText().length());
        });

        // Forgot Password Logic
        forgotPassword.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(MainActivity.this, "Please enter your email in the email field to reset password", Toast.LENGTH_LONG).show();
                return;
            }

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Reset Password")
                    .setMessage("Send password reset link to " + email + "?")
                    .setPositiveButton("Yes", (d, which) -> {
                        mAuth.sendPasswordResetEmail(email)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                    })
                    .setNegativeButton("No", null)
                    .create();

            dialog.show();
            // Change button colors after showing the dialog
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.blue1));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.blue1));
        });

        // Login Button Click
        btnLogin.setOnClickListener(view -> {

            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Email is required");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                etPassword.setError("Password is required");
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            Toast.makeText(MainActivity.this,
                                    "Login Successful",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "Login Failed: " + Objects.requireNonNull(task.getException()).getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }


                    });

        });

        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}