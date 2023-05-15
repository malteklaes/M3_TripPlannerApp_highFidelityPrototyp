package com.example.m3_tripplannerapp_highfidelityprototyp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class sortScreen3 extends AppCompatActivity {

    // filter dropdown
    Spinner filterSpinner;
    String filterResult = "";
    final String[] selectedOption = {""};
    String[] options = {"eco-friendly", "fast", "cheapest", "least stops", "earliest"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_screen3);

        // [1] set up drop down filter menu and interaction
        filterSpinner = findViewById(R.id.filterSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
        filterSpinner.setAdapter(adapter);
        this.interactFilter(selectedOption, options);

        // result of filter
        Log.d("malte", "Ergebnis spinner: " + filterResult);

    }



    private void interactFilter(String[] selectedOption, String[] options){
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOption[0] = (String) options[position];
                // Hier kannst du den ausgewählten Eintrag verarbeiten
                Log.d("malte", "Ergebnis spinner: " + selectedOption[0]);
                filterResult = selectedOption[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Code hier ausführen, wenn nichts ausgewählt ist
            }
        });
    }
}