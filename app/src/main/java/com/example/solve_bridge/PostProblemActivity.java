package com.example.solve_bridge;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PostProblemActivity extends AppCompatActivity {

    EditText etTitle, etDescription, etCategory;
    Button btnSubmit;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_problem);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("");   // remove default title
        }

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etCategory = findViewById(R.id.etCategory);
        btnSubmit = findViewById(R.id.btnSubmit);
        ImageButton btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        db = FirebaseFirestore.getInstance();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etTitle.getText().toString().trim();
                String desc = etDescription.getText().toString().trim();
                String category = etCategory.getText().toString().trim();

                if(title.isEmpty() || desc.isEmpty() || category.isEmpty()){
                    Toast.makeText(PostProblemActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> post = new HashMap<>();
                post.put("user", "Anonymous"); // change if using auth
                post.put("title", title);
                post.put("desc", desc);
                post.put("category", category);

                db.collection("posts")
                        .add(post)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(PostProblemActivity.this, "Problem Posted Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(PostProblemActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                        );
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
