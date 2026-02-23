package com.example.solve_bridge; // change if needed

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import android.widget.ImageView;
public class RegisterActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword;
    RadioButton rbPoster, rbDeveloper;
    Button btnRegister;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        rbPoster = findViewById(R.id.rbPoster);
        rbDeveloper = findViewById(R.id.rbDeveloper);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(view -> {

            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            final String role;
            if (rbPoster.isChecked()) {
                role = "Problem Poster";
            } else if (rbDeveloper.isChecked()) {
                role = "Developer";
            } else {
                role = "";
            }

            if (TextUtils.isEmpty(name)) {
                etName.setError("Name required");
                return;
            }

            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Email required");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                etPassword.setError("Password required");
                return;
            }

            if (role.isEmpty()) {
                Toast.makeText(this, "Select a role", Toast.LENGTH_SHORT).show();
                return;
            }

            // 🔥 Create user in Firebase Authentication
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            String userId = mAuth.getCurrentUser().getUid();

                            // 🔥 Store extra data in Firestore
                            Map<String, Object> user = new HashMap<>();
                            user.put("name", name);
                            user.put("email", email);
                            user.put("role", role);

                            db.collection("Users").document(userId)
                                    .set(user)
                                    .addOnSuccessListener(unused -> {
                                        Toast.makeText(RegisterActivity.this,
                                                "Registration Successful",
                                                Toast.LENGTH_SHORT).show();
                                        finish();
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(RegisterActivity.this,
                                            "Firestore Error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show());

                        } else {
                            Toast.makeText(RegisterActivity.this,
                                    "Auth Error: " + Objects.requireNonNull(task.getException()).getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    });

        });
    }
}