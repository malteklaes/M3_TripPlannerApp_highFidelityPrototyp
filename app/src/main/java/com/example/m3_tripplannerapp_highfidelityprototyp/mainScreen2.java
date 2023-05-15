package com.example.m3_tripplannerapp_highfidelityprototyp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class mainScreen2 extends AppCompatActivity {

    private static final String[] Cities = new String[]{"Wien", "Prag", "Paris", "Berlin", "Rom", "Warschau", "Budapest"}; //String[] Cities that are in AutoCompleteTextview startCity_input and destinationCity_input

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen2);

        AutoCompleteTextView editStart = findViewById(R.id.startCity_input);
        AutoCompleteTextView editDestination = findViewById(R.id.destinationCity_input);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Cities); //prepares to add String[] Cities to AutoCompleteTextView
        editStart.setAdapter(adapter); //adds String[] Citeies to AutoCompleteTextView startCity_input
        editDestination.setAdapter(adapter); //adds String[] Citeies to AutoCompleteTextView destinationCity_input
    }

    //public void (View view)
}