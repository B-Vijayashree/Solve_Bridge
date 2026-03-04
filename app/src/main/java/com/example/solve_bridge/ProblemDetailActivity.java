package com.example.solve_bridge;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProblemDetailActivity extends AppCompatActivity {

    TextView tvTitle, tvDescription, tvCategory;
    Button btnAddSolution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        tvCategory = findViewById(R.id.tvCategory);
        btnAddSolution = findViewById(R.id.btnAddSolution); // change ID in XML also

        // Get data from intent
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String category = getIntent().getStringExtra("category");

        tvTitle.setText(title);
        tvDescription.setText(description);
        tvCategory.setText(category);

        // 🔥 OPEN AddSolutionActivity
        btnAddSolution.setOnClickListener(v -> {

            Intent intent = new Intent(ProblemDetailActivity.this, AddSolutionActivity.class);

            // Passing problem details to next page
            intent.putExtra("title", title);
            intent.putExtra("description", description);

            startActivity(intent);
        });

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}
