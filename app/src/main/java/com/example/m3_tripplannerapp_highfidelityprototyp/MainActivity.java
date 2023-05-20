package com.example.m3_tripplannerapp_highfidelityprototyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataConnection dataConnection = new DataConnection("Vienna", "Hbf", "Köln", "Hbf", false);

        Log.d("logger", "Ergebnis: " + dataConnection.toString());
        Log.d("logger", "Test Martina");
        Log.d("logger", "Test Martina2");
        Log.d("logger", "Test Martina3");
        Log.d("logger", "Test Malte");
        Log.d("logger", "Test Milenko");
        Log.d("logger", "Test Felix");

        setupButtonListeners();

        sharedPreferences = getSharedPreferences("Inputs", Context.MODE_PRIVATE);
    }


    //Managing buttons
    // @Override
    private void setupButtonListeners(){
        Button NewSearchButton = findViewById(R.id.button_newSearch);
        Button MyBookmarksButton = findViewById(R.id.button_bookmarks);

        NewSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, mainScreen2.class);
                startActivity(intent);

                //Setting saved user inputs free
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
            }
        });

        MyBookmarksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, bookmarksScreen5.class);
                startActivity(intent);
            }
        });
    }
}