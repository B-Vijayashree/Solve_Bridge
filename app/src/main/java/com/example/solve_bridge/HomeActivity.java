package com.example.solve_bridge;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    Button btnLogout;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView menuIcon = findViewById(R.id.menuIcon);

        if (menuIcon != null) {
            menuIcon.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(HomeActivity.this, view);

                popupMenu.getMenu().add("Post a Problem");
                popupMenu.getMenu().add("Profile");
                popupMenu.getMenu().add("Your Solutions");

                popupMenu.setOnMenuItemClickListener(item -> {
                    if (Objects.equals(item.getTitle(), "Post a Problem")) {
                        Toast.makeText(this, "Post Problem Clicked", Toast.LENGTH_SHORT).show();
                    } else if (Objects.equals(item.getTitle(), "Profile")) {
                        Toast.makeText(this, "Profile Clicked", Toast.LENGTH_SHORT).show();
                    } else if (Objects.requireNonNull(item.getTitle()).equals("Your Solutions")) {
                        Toast.makeText(this, "Your Solutions Clicked", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                });

                popupMenu.show();
            });
        }

        mAuth = FirebaseAuth.getInstance();
        btnLogout = findViewById(R.id.btnLogout);

        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                mAuth.signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }
    }
}
