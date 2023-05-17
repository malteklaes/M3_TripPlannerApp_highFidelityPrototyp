package com.example.m3_tripplannerapp_highfidelityprototyp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.view.View;


import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class mainScreen2 extends AppCompatActivity {

    private List<DataConnection> inputDataConnections=new ArrayList<DataConnection>();

    private DataConnection outwardConnection;
    private DataConnection inwardConnection;

    private static final String[] Cities = new String[]{"Wien", "Prag", "Paris", "Berlin", "Rom", "Warschau", "Budapest"}; //String[] Cities that are in AutoCompleteTextview startCity_input and destinationCity_input
    private AutoCompleteTextView editStart;
    private AutoCompleteTextView editDestination;
    private String startCity;
    private String destinationCity;
    private DataDate startDate;
    private DataTime startTime;
    private DataDate returnDate;
    private DataTime returnTime;

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

        setupButtonListeners(); //Buttons Listener Method

        editStart.setOnItemClickListener(new AdapterView.OnItemClickListener(){    //onItemClick of editStart String startCity gets overwritten with the text of editStart
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){     //overriding of startCity
                startCity = editStart.getText().toString();
            }
        });
        editDestination.setOnItemClickListener(new AdapterView.OnItemClickListener(){     //onItemClick of editDestination String destinationCity gets overwritten with the text of editDestination
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){    //overriding of destinationCity
                destinationCity = editDestination.getText().toString();
            }
        });

        oneWay = findViewById(R.id.oneWay_chip); //initializing oneWay with Chip oneWay_chip
        bothWay = findViewById(R.id.bothWays_chip); //initializing oneWay with Chip bothWays_chip
        oneWay.setChecked(true);

        date1 = findViewById(R.id.editTextDate1); //initializing date1 with EditText editTextDate1
        date2 = findViewById(R.id.editTextDate2); //initializing date2 with EditText editTextDate2
        date2.setVisibility(View.INVISIBLE);   //sets date2 invisible

        date1.setOnClickListener(new View.OnClickListener() {  //managing onClick from date1 to diplay Calendar and output selected date on date1
                                     @Override
                                     public void onClick(View view) {  //action that happens upon clicking on date1
                                         final Calendar calendar = Calendar.getInstance(); //get instance Calendar
                                         int year = calendar.get(Calendar.YEAR);  //get year
                                         int month = calendar.get(Calendar.MONTH);   //get month
                                         int day = calendar.get(Calendar.DAY_OF_MONTH);  //get day

                                         DatePickerDialog datePickerDialog = new DatePickerDialog( //constructing and initializing DatePickerDialog, which presents graphical calendar interface and manages displayed output
                                                 mainScreen2.this,
                                                 new DatePickerDialog.OnDateSetListener() {
                                                     @Override
                                                     public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) { //setting date to Edittext
                                                         date1.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                                                     }
                                                 },
                                                 year, month, day);  //recieved variables from calendar

                                         startDate = new DataDate(day, month, year); //initializing startDate

                                         datePickerDialog.show(); //displaying DatePickerDialog
                                     }
        });

        date2.setOnClickListener(new View.OnClickListener() {  //managing onClick from date2 to diplay Calendar and output selected date
            @Override
            public void onClick(View view) {  //action that happens upon clicking on date1
                final Calendar calendar = Calendar.getInstance(); //get instance Calendar
                int year = calendar.get(Calendar.YEAR);  //get year
                int month = calendar.get(Calendar.MONTH);   //get month
                int day = calendar.get(Calendar.DAY_OF_MONTH);  //get day



                DatePickerDialog datePickerDialog = new DatePickerDialog( //constructing and initializing DatePickerDialog, which presents graphical calendar interface and manages displayed output
                        mainScreen2.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) { //setting date to Edittext
                                date2.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                            }
                        },
                        year, month, day);  //recieved variables from calendar

                returnDate = new DataDate(day, month, year);  //initializing returnDate

                datePickerDialog.show(); //displaying DatePickerDialog
            }
        });


        time1 = findViewById(R.id.editTextTime1); //initializing time1 with EditText editTextTime1
        time2 = findViewById(R.id.editTextTime2); //initializing time2 with EditText editTextTime2
        time2.setVisibility(View.INVISIBLE);  //sets time2 invisible

        time1.setOnClickListener(new View.OnClickListener() {  //managing onClick from time1 to diplay TimeInput graphically and output selected time
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog( //constructing and initializing TimePickerDialog, which presents graphical Time interface and manages displayed output
                        mainScreen2.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                time1.setText(hourOfDay + " : " + minute);
                            }
                        },
                        hour, minute, true); //recieved variables from calendar and setting 24Hour Format

                startTime = new DataTime(minute, hour);  //initializing startTime

                timePickerDialog.show();
            }
        });

        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        mainScreen2.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                time2.setText(hourOfDay + " : " + minute);
                            }
                        },
                        hour, minute, true);

                returnTime = new DataTime(minute, hour);  //initializing returnTime

                timePickerDialog.show();
            }
        });

    }

    public void switch_CityInputs (View view) {
        String startText = editStart.getText().toString();
        String destinationText = editDestination.getText().toString();
        startCity = destinationText;
        destinationCity = startText;
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


    //Initiating the search - sending retrieved data
    private void performSearch(){
        //getting data
        String startCity = editStart.getText().toString();
        String destinationCity = editDestination.getText().toString();
        String startDate = date1.getText().toString();
        String startTime = time1.getText().toString();
        String returnDate = date2.getText().toString();
        String returnTime = time2.getText().toString();
        boolean isOneWay = oneWay.isChecked();

        //passing operation
        Intent intent = new Intent(mainScreen2.this, sortScreen3.class);
        intent.putExtra("startCity", startCity);
        intent.putExtra("destinationCity", destinationCity);
        intent.putExtra("startDate", startDate);
        intent.putExtra("startTime", startTime);
        intent.putExtra("returnDate", returnDate);
        intent.putExtra("returnTime", returnTime);
        intent.putExtra("isOneWay", isOneWay);

        startActivity(intent);
    }

    //Managing buttons
    // @Override
    private void setupButtonListeners(){
        Button HomeButton = findViewById(R.id.button_home);
        Button SearchButton = findViewById(R.id.button_search);
        Button SwitchButton = findViewById(R.id.switch_CityInputs_button);
        Button SortBadge = findViewById(R.id.sort_badge);
        Button ResultBadge = findViewById(R.id.result_badge);

        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainScreen2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        SwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch_CityInputs(v);
            }
        });

        SortBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainScreen2.this, sortScreen3.class);
                startActivity(intent);
            }
        });
        ResultBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainScreen2.this, resultScreen4.class);
                startActivity(intent);
            }
        });

    }
}