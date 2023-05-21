package com.example.m3_tripplannerapp_highfidelityprototyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * this class ....
 */
public class mainScreen2 extends AppCompatActivity {


    private static final String[] Cities = new String[]{"Hamburg", "Cologne", "Graz", "Vienna", "Innsbruck", "Prague", "Paris", "Berlin", "Rome", "Warsaw", "Budapest"}; //String[] Cities that are in AutoCompleteTextview startCity_input and destinationCity_input
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

    private SharedPreferences sharedPreferences;


    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
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

        //Saving user input
        sharedPreferences = getSharedPreferences("Inputs", Context.MODE_PRIVATE);

        editStart.setOnItemClickListener(new AdapterView.OnItemClickListener(){    //onItemClick of editStart String startCity gets overwritten with the text of editStart
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){     //overriding of startCity
                startCity = editStart.getText().toString();

                //saving user input
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("startCity", startCity);
                editor.apply();
            }
        });
        editDestination.setOnItemClickListener(new AdapterView.OnItemClickListener(){     //onItemClick of editDestination String destinationCity gets overwritten with the text of editDestination
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){    //overriding of destinationCity
                destinationCity = editDestination.getText().toString();

                //saving user input
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("destinationCity", destinationCity);
                editor.apply();
            }
        });

        oneWay = findViewById(R.id.oneWay_chip); //initializing oneWay with Chip oneWay_chip
        bothWay = findViewById(R.id.bothWays_chip); //initializing oneWay with Chip bothWays_chip
        oneWay.setChecked(true);
        oneWay.setChipBackgroundColorResource(R.color.black);

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
                                                         startDate = new DataDate(dayOfMonth, month+1, year); //initializing startDate

                                                         //saving User Input
                                                         SharedPreferences.Editor editor = sharedPreferences.edit();
                                                         editor.putInt("startDay", startDate.getDay());
                                                         editor.putInt("startMonth", startDate.getMonth());
                                                         editor.putInt("startYear", startDate.getYear());
                                                         editor.apply();
                                                     }
                                                 },
                                                 year, month, day);  //recieved variables from calendar



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
                                returnDate = new DataDate(dayOfMonth, month + 1, year);  //initializing returnDate

                                //saving User Input
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("returnDay", returnDate.getDay());
                                editor.putInt("returnMonth", returnDate.getMonth());
                                editor.putInt("returnYear", returnDate.getYear());
                                editor.apply();
                            }
                        },
                        year, month, day);  //recieved variables from calendar



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
                                startTime = new DataTime(minute, hourOfDay);  //initializing startTime

                                //saving User Input
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("startMinute", startTime.getMinute());
                                editor.putInt("startHour", startTime.getHour());
                                editor.apply();
                            }
                        },
                        hour, minute, true); //recieved variables from calendar and setting 24Hour Format



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
                                returnTime = new DataTime(minute, hourOfDay);  //initializing returnTime

                                //saving User Input
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("returnMinute", returnTime.getMinute());
                                editor.putInt("returnHour", returnTime.getHour());
                                editor.apply();
                            }
                        },
                        hour, minute, true);



                timePickerDialog.show();
            }
        });

    }


    /**
     * Saving User Input
     */
    @Override
    public void onResume() {
        super.onResume();

        String savedStart = sharedPreferences.getString("startCity", "");
        String savedDest = sharedPreferences.getString("destinationCity", "");

        int savedStartDay = sharedPreferences.getInt("startDay", -1);
        int savedStartMonth = sharedPreferences.getInt("startMonth", -1);
        int savedStartYear = sharedPreferences.getInt("startYear", -1);

        int savedReturnDay = sharedPreferences.getInt("returnDay", -1);
        int savedReturnMonth = sharedPreferences.getInt("returnMonth", -1);
        int savedReturnYear = sharedPreferences.getInt("returnYear", -1);

        int savedStartMinute = sharedPreferences.getInt("startMinute", -1);
        int savedStartHour = sharedPreferences.getInt("startHour", -1);

        int savedReturnMinute = sharedPreferences.getInt("returnMinute", -1);
        int savedReturnHour = sharedPreferences.getInt("returnHour", -1);

        boolean isOneWayChecked = sharedPreferences.getBoolean("isOneWayChecked", true);

        editStart.setText(savedStart);
        editDestination.setText(savedDest);

        if (isOneWayChecked) {
            oneWay.setChecked(true);
            bothWay.setChecked(false);
        } else {
            oneWay.setChecked(false);
            bothWay.setChecked(true);
            date2.setVisibility(View.VISIBLE);
            time2.setVisibility(View.VISIBLE);
        }


        if (savedStartDay != -1 && savedStartMonth != -1 && savedStartYear != -1) {
            startDate = new DataDate(savedStartDay, savedStartMonth, savedStartYear);
            date1.setText(startDate.toString());
        }

        if (savedReturnDay != -1 && savedReturnMonth != -1 && savedReturnYear != -1){
            returnDate = new DataDate(savedReturnDay, savedReturnMonth, savedReturnYear);
            date2.setText(returnDate.toString());
        }

        if(savedStartMinute != -1 && savedStartHour != 1) {
            startTime = new DataTime(savedStartMinute, savedStartHour);
            time1.setText(startTime.toString());
        }
        if(savedReturnMinute != -1 && savedReturnHour != 1) {
            returnTime = new DataTime(savedReturnMinute, savedReturnHour);
            time2.setText(returnTime.toString());
        }
    }

    /**
     *
     * @param view
     */
    public void switch_CityInputs (View view) {
        String startText = editStart.getText().toString();
        String destinationText = editDestination.getText().toString();
        startCity = destinationText;
        destinationCity = startText;
        editStart.setText(destinationText);
        editDestination.setText(startText);

        //saving user input
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("startCity", startCity);
        editor.putString("destinationCity", destinationCity);
        editor.apply();

    }

    /**
     *
     * @param view
     */
    public void select_chip_oneWay (View view) {
        oneWay.setChecked(true);
        bothWay.setChecked(false);
        oneWay.setChipBackgroundColorResource(R.color.black);
        bothWay.setChipBackgroundColorResource(R.color.white);
        date2.setVisibility(View.INVISIBLE);
        time2.setVisibility(View.INVISIBLE);

        //saving the User Input State
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isOneWayChecked", true);
        editor.apply();
    }

    /**
     *
     * @param view
     */
    public void select_chip_bothWays (View view) {
        oneWay.setChecked(false);
        bothWay.setChecked(true);
        date2.setVisibility(View.VISIBLE);
        time2.setVisibility(View.VISIBLE);
        oneWay.setChipBackgroundColorResource(R.color.white);
        bothWay.setChipBackgroundColorResource(R.color.black);

        //saving the User Input State
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isOneWayChecked", false);
        editor.apply();
    }

    /**
     *  Initiating the search - sending retrieved data
     */
    private void performSearch(){
        //getting data
        Set<String> CitiesSet = new HashSet<>(Arrays.asList(Cities));
        String startCity = editStart.getText().toString();
        String destinationCity = editDestination.getText().toString();
        DataDate startDate = this.startDate;
        DataTime startTime = this.startTime;
        DataDate returnDate = this.returnDate;
        DataTime returnTime = this.returnTime;
        boolean isOneWay = oneWay.isChecked();

        //Dialog if City not found
        if(startCity==null || !CitiesSet.contains(startCity) || destinationCity==null || !CitiesSet.contains(destinationCity)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("City not found!");
            builder.setMessage("Please select city from list!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

        //Dialog if for return Date incorrect
        else if(startDate==null || !(isOneWay) && (returnDate==null || startDate.compareThisDateToThatDate(returnDate)==DataEnumTimeComparison.Later)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Date incorrect!");
            builder.setMessage("Please check your Dates")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

        //Dialog if return Date same and time incorrect
        else if(!isOneWay && startDate.compareThisDateToThatDate(returnDate)==DataEnumTimeComparison.Equal && startTime.compareThisTimeToThatTime(returnTime)==DataEnumTimeComparison.Later) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Time incorrect!");
            builder.setMessage("Please check your Time")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

        else {
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
    }


    /**
     * Managing buttons
     */
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
                Toast.makeText(mainScreen2.this, "Showing only suggested trips", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mainScreen2.this, sortScreen3.class);
                startActivity(intent);
            }
        });
        ResultBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mainScreen2.this, "Nothing chosen, showing only suggested trips", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mainScreen2.this, resultScreen4.class);
                startActivity(intent);
            }
        });

    }
}