package com.example.solve_bridge;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProblemDetailActivity extends AppCompatActivity {

    TextView tvTitle, tvDescription, tvCategory;
    EditText etSolution;
    Button btnSubmitSolution;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        tvCategory = findViewById(R.id.tvCategory);
        etSolution = findViewById(R.id.etSolution);
        btnSubmitSolution = findViewById(R.id.btnSubmitSolution);

        db = FirebaseFirestore.getInstance();

        // Get data from intent
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String category = getIntent().getStringExtra("category");

        tvTitle.setText(title);
        tvDescription.setText(description);
        tvCategory.setText(category);

        btnSubmitSolution.setOnClickListener(v -> {

            String solution = etSolution.getText().toString().trim();

            if (!solution.isEmpty()) {

                Map<String, Object> solutionData = new HashMap<>();
                solutionData.put("solution", solution);
                solutionData.put("problemTitle", title);

                db.collection("solutions")
                        .add(solutionData);

                etSolution.setText("");
                Toast.makeText(this, "Solution Added", Toast.LENGTH_SHORT).show();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}
