package com.example.m3_tripplannerapp_highfidelityprototyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class bookmarksScreen5 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks_screen5);

        setupButtonListeners();
    }


    private void setupButtonListeners(){
        Button HomeButton = findViewById(R.id.button_home);
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bookmarksScreen5.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}