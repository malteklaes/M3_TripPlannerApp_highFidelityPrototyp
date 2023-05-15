package com.example.m3_tripplannerapp_highfidelityprototyp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.android.material.chip.Chip;

public class mainScreen2 extends AppCompatActivity {

    private static final String[] Cities = new String[]{"Wien", "Prag", "Paris", "Berlin", "Rom", "Warschau", "Budapest"}; //String[] Cities that are in AutoCompleteTextview startCity_input and destinationCity_input
    private AutoCompleteTextView editStart;
    private AutoCompleteTextView editDestination;

    private Chip oneWay;
    private Chip bothWay;
    private EditText date1;
    private EditText date2;
    private EditText time1;
    private EditText time2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen2);

        editStart = findViewById(R.id.startCity_input); //initializing editStart with AutoCompleteTextView startCity_input
        editDestination = findViewById(R.id.destinationCity_input); //initializing editStart with AutoCompleteTextView destinationCity_input
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Cities); //prepares to add String[] Cities to AutoCompleteTextView
        editStart.setAdapter(adapter); //adds String[] Citeies to AutoCompleteTextView startCity_input
        editDestination.setAdapter(adapter); //adds String[] Citeies to AutoCompleteTextView destinationCity_input

        oneWay = findViewById(R.id.oneWay_chip); //initializing oneWay with Chip oneWay_chip
        bothWay = findViewById(R.id.bothWays_chip); //initializing oneWay with Chip bothWays_chip
        oneWay.setChecked(true);

        date1 = findViewById(R.id.editTextDate1); //initializing date1 with EditText editTextDate1
        date2 = findViewById(R.id.editTextDate2); //initializing date1 with EditText editTextDate2
        date2.setVisibility(View.INVISIBLE);   //sets date2 invisible

        time1 = findViewById(R.id.editTextTime1); //initializing time1 with EditText editTextTime1
        time2 = findViewById(R.id.editTextTime2); //initializing time2 with EditText editTextTime2
        time2.setVisibility(View.INVISIBLE);  //sets time2 invisible





    }

    public void switch_CityInputs (View view) {
        String startText = editStart.getText().toString();
        String destinationText = editDestination.getText().toString();
        editStart.setText(destinationText);
        editDestination.setText(startText);
    }

    public void select_chip_oneWay (View view) {
        oneWay.setChecked(true);
        bothWay.setChecked(false);
        date2.setVisibility(View.INVISIBLE);
        time2.setVisibility(View.INVISIBLE);
    }

    public void select_chip_bothWays (View view) {
        oneWay.setChecked(false);
        bothWay.setChecked(true);
        date2.setVisibility(View.VISIBLE);
        time2.setVisibility(View.VISIBLE);
    }


}