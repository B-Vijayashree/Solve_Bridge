package com.example.solve_bridge;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Post> list;
    PostAdapter adapter;

    SearchView searchView;
    ImageButton btnSearch, btnMenu, btnBack;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toast.makeText(this, "HomeActivity Started", Toast.LENGTH_LONG).show();

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        btnSearch = findViewById(R.id.btnSearch);
        btnMenu = findViewById(R.id.btnMenu);
        btnBack = findViewById(R.id.btnBack);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new PostAdapter(this, list);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        loadPosts();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Post> filtered = new ArrayList<>();
                for (Post p : list) {
                    if (p.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                        filtered.add(p);
                    }
                }
                adapter.updateList(filtered);
                return true;
            }
        });

        btnSearch.setOnClickListener(v ->
                searchView.setVisibility(
                        searchView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE
                ));

        btnBack.setOnClickListener(v -> finish());

        btnMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, btnMenu);
            popup.getMenu().add("My Profile");
            popup.getMenu().add("My Problems");
            popup.getMenu().add("Posted Solutions");
            popup.getMenu().add("Logout");
            popup.show();
        });
    }

    private void loadPosts() {
        db.collection("posts")
                .get()
                .addOnSuccessListener(snap -> {
                    Toast.makeText(this,
                            "Snapshot empty? " + snap.isEmpty(),
                            Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this,
                                "REAL ERROR: " + e.getMessage(),
                                Toast.LENGTH_LONG).show());
    }
}