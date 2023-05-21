package com.example.m3_tripplannerapp_highfidelityprototyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * represents the landing page with logo and give way to general search or bookmarks
 */
public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    /**
     * managing general action
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupButtonListeners();

        sharedPreferences = getSharedPreferences("Inputs", Context.MODE_PRIVATE);
    }


    /**
     * managing buttons
     */
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