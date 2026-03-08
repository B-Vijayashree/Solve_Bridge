package com.solve_bridge.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    ImageButton btnBack;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Back Button
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Logout Button
        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(v -> logoutUser());
    }

    private void logoutUser() {

        // Sign out from Firebase
        FirebaseAuth.getInstance().signOut();

        // Go to Login page
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);

        // Clear activity stack
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();
    }
}