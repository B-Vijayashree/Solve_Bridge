package com.example.solve_bridge;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddSolutionActivity extends AppCompatActivity {

    private EditText etSolution;
    private Button btnSubmit;
    private ImageView btnBack;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_solution);

        // Initialize Views
        etSolution = findViewById(R.id.etSolution);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnBack = findViewById(R.id.btnBack);

        db = FirebaseFirestore.getInstance();

        // Back Button
        btnBack.setOnClickListener(v -> finish());

        // Submit Button
        btnSubmit.setOnClickListener(v -> submitSolution());
    }

    private void submitSolution() {

        String solutionText = etSolution.getText().toString().trim();

        if (TextUtils.isEmpty(solutionText)) {
            etSolution.setError("Please enter your solution");
            return;
        }

        btnSubmit.setEnabled(false); // Prevent double click

        Map<String, Object> solution = new HashMap<>();
        solution.put("solutionText", solutionText);
        solution.put("timestamp", System.currentTimeMillis());

        db.collection("Solutions")
                .add(solution)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this,
                            "Solution Submitted Successfully!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    btnSubmit.setEnabled(true);
                    Toast.makeText(this,
                            "Failed to submit solution",
                            Toast.LENGTH_SHORT).show();
                });
    }
}
