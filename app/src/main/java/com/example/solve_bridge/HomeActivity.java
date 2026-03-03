package com.example.solve_bridge;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Post> list;          // original data
    ArrayList<Post> filteredList;  // search data
    PostAdapter adapter;

    SearchView searchView;
    ImageButton btnSearch, btnMenu, btnBack;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        btnSearch = findViewById(R.id.btnSearch);
        btnMenu = findViewById(R.id.btnMenu);
        btnBack = findViewById(R.id.btnBack);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        filteredList = new ArrayList<>();

        adapter = new PostAdapter(this, filteredList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        loadPosts();

        // SEARCH LOGIC
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {

                filteredList.clear();

                if (newText.isEmpty()) {
                    filteredList.addAll(list);
                } else {
                    for (Post p : list) {
                        if (p.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                            filteredList.add(p);
                        }
                    }
                }

                adapter.notifyDataSetChanged();
                return true;
            }
        });

        // SEARCH ICON
        btnSearch.setOnClickListener(v ->
                searchView.setVisibility(
                        searchView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE
                ));

        // BACK BUTTON
        btnBack.setOnClickListener(v -> finish());

        // MENU BUTTON
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

                    list.clear();
                    filteredList.clear();

                    for (QueryDocumentSnapshot doc : snap) {
                        Post p = doc.toObject(Post.class);
                        list.add(p);
                    }

                    filteredList.addAll(list);
                    adapter.notifyDataSetChanged();
                });
    }
}