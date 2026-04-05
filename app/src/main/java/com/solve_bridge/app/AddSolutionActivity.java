package com.solve_bridge.app;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddSolutionActivity extends AppCompatActivity {

    private EditText etSolution;
    private Button btnSubmit;
    private ImageView btnBack;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    String problemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_solution);

        etSolution = findViewById(R.id.etSolution);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnBack = findViewById(R.id.btnBack);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Receive problemId from ProblemDetailActivity
        problemId = getIntent().getStringExtra("problemId");

        btnBack.setOnClickListener(v -> finish());

        btnSubmit.setOnClickListener(v -> submitSolution());
    }

    private void submitSolution() {

        String solutionText = etSolution.getText().toString().trim();

        if (TextUtils.isEmpty(solutionText)) {
            etSolution.setError("Please enter your solution");
            return;
        }

        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "Please login to add a solution", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = mAuth.getCurrentUser().getUid();
        String userName = mAuth.getCurrentUser().getDisplayName();
        if (userName == null || userName.isEmpty()) userName = "User";

        btnSubmit.setEnabled(false);

        Map<String, Object> solution = new HashMap<>();

        solution.put("problemId", problemId);
        solution.put("solutionText", solutionText);
        solution.put("userId", userId);
        solution.put("userName", userName);
        solution.put("timestamp", System.currentTimeMillis());

        db.collection("solutions")
                .add(solution)
                .addOnSuccessListener(documentReference -> {
                    // Update user's solution list in their profile
                    db.collection("Users").document(userId)
                            .update("mySolutions", FieldValue.arrayUnion(solutionText))
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(AddSolutionActivity.this,
                                        "Solution Submitted Successfully!",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(AddSolutionActivity.this,
                                        "Solution submitted but profile update failed",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            });
                })
                .addOnFailureListener(e -> {
                    btnSubmit.setEnabled(true);
                    Toast.makeText(AddSolutionActivity.this,
                            "Failed to submit solution",
                            Toast.LENGTH_SHORT).show();
                });
    }
}