package com.example.m3_tripplannerapp_highfidelityprototyp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class sortScreen3 extends AppCompatActivity {

    // mocked dataBase
    List<DataConnection> dataBaseMock = new ArrayList<>();
    DataBaseMockUp dataBaseMockUp = new DataBaseMockUp();

    // filter dropdown
    Spinner filterSpinner;
    String filterResult = "";
    final String[] selectedOption = {""};
    String[] options = {"eco-friendly", "fast", "reliable", "comfortable", "cheap", "few stops"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // [0] example mock data: scenario back and forth (vienna->frankfurt, frankfurt->vienna)
        DataConnection incomingDataMock = new DataConnection("vienna", "hbf", "frankfurt", "hbf", new DataDate(15,5,2023), new DataTime(30,15), new DataDate(16,5,2023), new DataTime(30,18));
        dataBaseMockUp = new DataBaseMockUp("vienna", "hbf", "frankfurt", "hbf", new DataDate(15,5,2023), DataEnumTransport.Train);
        Log.d("malte", "Ergbnis mocked db: " + dataBaseMockUp.toString());


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_screen3);

        // [1] set up drop down filter menu and interaction
        filterSpinner = findViewById(R.id.filterSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
        filterSpinner.setAdapter(adapter);
        this.interactWithFilter();

        // result of filter
        Log.d("malte", "Ergebnis spinner: " + filterResult);

    }


    /**
     * interacts with filter drop down and updates variable resultFilter
     */
    private void interactWithFilter(){
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOption[0] = (String) options[position];
                filterResult = selectedOption[0];
                Log.d("malte", "Ergebnis filter drop down: " + selectedOption[0]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filterResult = selectedOption[0];
                Log.d("malte", "Ergebnis, dass nichts gemacht wurde: " + filterResult);
            }
        });
    }


}