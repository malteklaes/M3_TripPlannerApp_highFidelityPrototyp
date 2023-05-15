package com.example.m3_tripplannerapp_highfidelityprototyp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.material.chip.Chip;

import java.util.Calendar;

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

        date1.setOnClickListener(new View.OnClickListener() {  //manages onClick from date1 to diplay Calendar and output selected date on date1
                                     @Override
                                     public void onClick(View view) {  //actionthat happens upon clicking on date1
                                         final Calendar calendar = Calendar.getInstance(); //get instance Calendar
                                         int year = calendar.get(Calendar.YEAR);  //get year
                                         int month = calendar.get(Calendar.MONTH);   //get month
                                         int day = calendar.get(Calendar.DAY_OF_MONTH);  //get day

                                         DatePickerDialog datePickerDialog = new DatePickerDialog( //constructs and initializes DatePickerDialog, which presents graphical calendar interface and manages displayed output
                                                 mainScreen2.this,
                                                 new DatePickerDialog.OnDateSetListener() {
                                                     @Override
                                                     public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) { //setting date to Edittext
                                                         date1.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                                                     }
                                                 },
                                                 year, month, day);  //recieved variables from Calendar initialized

                                         datePickerDialog.show(); //displaying DatePickerDialog
                                     }
        });

        date2.setOnClickListener(new View.OnClickListener() {  //manages onClick from date2 to diplay Calendar and output selected date on date2
            @Override
            public void onClick(View view) {  //actionthat happens upon clicking on date1
                final Calendar calendar = Calendar.getInstance(); //get instance Calendar
                int year = calendar.get(Calendar.YEAR);  //get year
                int month = calendar.get(Calendar.MONTH);   //get month
                int day = calendar.get(Calendar.DAY_OF_MONTH);  //get day

                DatePickerDialog datePickerDialog = new DatePickerDialog( //constructs and initializes DatePickerDialog, which presents graphical calendar interface and manages displayed output
                        mainScreen2.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) { //setting date to Edittext
                                date2.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                            }
                        },
                        year, month, day);  //recieved variables from Calendar initialized

                datePickerDialog.show(); //displaying DatePickerDialog
            }
        });


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