package com.example.solve_bridge;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MySolutionsActivity extends AppCompatActivity {

    RecyclerView recyclerSolutions;
    SolutionAdapter adapter;
    List<String> solutionList;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_solutions);

        recyclerSolutions = findViewById(R.id.recyclerSolutions);
        ImageView btnBack = findViewById(R.id.btnBack);

        solutionList = new ArrayList<>();
        adapter = new SolutionAdapter(solutionList);

        recyclerSolutions.setLayoutManager(new LinearLayoutManager(this));
        recyclerSolutions.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        loadSolutions();

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadSolutions() {

        String userId = "user123"; // Later use FirebaseAuth

        db.collection("users")
                .document(userId)
                .collection("mySolutions")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    solutionList.clear();

                    queryDocumentSnapshots.forEach(doc -> {
                        String text = doc.getString("solutionText");
                        solutionList.add(text);
                    });

                    adapter.notifyDataSetChanged();
                });
    }
}