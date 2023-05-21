package com.example.m3_tripplannerapp_highfidelityprototyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class sortScreen3 extends AppCompatActivity {

    // mocked dataBase
    List<DataConnection> dataBaseMock = new ArrayList<>();
    DataBaseMockUp dataBaseMockUp = new DataBaseMockUp();
    List<DataConnection> filteredDataConnection1 = new ArrayList<>();
    List<DataConnection> filteredDataConnection2 = new ArrayList<>();
    List<DataEnumTransportProperties> properties = new ArrayList<>();

    List<DataConnection> incomingData = new ArrayList<>();

    // filter dropdown
    Spinner filterSpinner;
    String filterResult = "";
    DataEnumTransportProperties filterResultProperty = DataEnumTransportProperties.Nothing;
    final String[] selectedOption = {""};
    String[] options = {"eco-friendly", "fast", "reliable", "comfortable", "cheap", "few stops"};

    // tablayout
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapterView;
    private static DataConnection resultFrag1Data = new DataConnection("","","","", false);
    private static DataConnection resultFrag2Data = new DataConnection("","","","", true);

    private static List<DataConnection> dtoResultTo = new ArrayList<>();
    private static List<DataConnection> dtoResultReturn = new ArrayList<>();

    //User input data retrieval
    private String startCity;
    private String destinationCity;
    private DataDate startDate;
    private DataTime startTime;
    private DataDate returnDate;
    private DataTime returnTime;
    private boolean isOneWay = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // [0] general set up
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_screen3);

        // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // [1] retrieving data inputs from search fields
        Intent intent = getIntent();
        startCity = intent.getStringExtra("startCity");
        destinationCity = intent.getStringExtra("destinationCity");
        startDate = (DataDate) intent.getSerializableExtra("startDate");
        Log.d("transaction", " startDate: " + startDate);
        startTime = (DataTime) intent.getSerializableExtra("startTime");
        returnDate = (DataDate) intent.getSerializableExtra("returnDate");
        returnTime = (DataTime) intent.getSerializableExtra("returnTime");
        isOneWay = intent.getBooleanExtra("isOneWay", false);

        //getting chosen trip
        incomingData.clear();
        if(!isOneWay) {
            incomingData.add(new DataConnection(startCity, "", destinationCity, "", startDate, startTime, false));
            incomingData.add(new DataConnection(destinationCity, "", startCity, "", returnDate, returnTime, true));
        } else {
            incomingData.add(new DataConnection(startCity, "", destinationCity, "", startDate, startTime, false));
            incomingData.add(new DataConnection("", "", "", "", new DataDate(0,0,0), new DataTime(0,0), true));
        }



        // ------------------------------------------------------------------------------------------------------------------
        // [1] example mock database-data with incoming data
        dataBaseMockUp = new DataBaseMockUp(startCity, "Hbf", destinationCity, "Hbf", startDate,  DataEnumTransport.Car_Sharing, false);
        dataBaseMockUp.calculateRandomDataEntries(startCity, "Hbf", destinationCity, "Hbf", startDate, false, DataEnumTransport.Car_Sharing, DataEnumTransport.Bus, DataEnumTransport.Train);

        if(!isOneWay){
            dataBaseMockUp.calculateRandomDataEntries(destinationCity, "Hbf", startCity, "Hbf", returnDate, true, DataEnumTransport.Car_Sharing, DataEnumTransport.Bus);
        }


        // ------------------------------------------------------------------------------------------------------------------
        // [1] send initially data to tab1 and tab2
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.toReturnTabLayout);

        adapterView = new ViewPagerAdapter(getSupportFragmentManager());
        adapterView.addFragment(new SortScreen3fragment1(), "To");
        adapterView.addFragment(new SortScreen3fragment2(), "Return");
        viewPager.setAdapter(adapterView);

        tabLayout.setupWithViewPager(viewPager);
        SortScreen3fragment1 neu = SortScreen3fragment1.newInstance("hallo");

        // ------------------------------------------------------------------------------------------------------------------
        // [2] set up drop down filter menu and interaction
        filterSpinner = findViewById(R.id.filterSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
        filterSpinner.setAdapter(adapter);
        this.interactWithFilter();

        // result of filter
        Log.d("malte", "Ergebnis spinner: " + filterResult);

        // DATABASE example
        List<DataEnumTransportProperties> properties = new ArrayList<>();
        properties.add(DataEnumTransportProperties.Nothing);

        setupButtonListeners(); //Buttons Listener Method
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

                // [1] database related
                filterResultProperty = convertStringToTransportProperty(filterResult);
                properties.clear();
                properties.add(filterResultProperty);
                // [2] refill data base
                dataBaseMockUp = new DataBaseMockUp(startCity, "Hbf", destinationCity, "Hbf", startDate,  DataEnumTransport.Car_Sharing, false);
                dataBaseMockUp.calculateRandomDataEntries(startCity, "Hbf", destinationCity, "Hbf", startDate, false, DataEnumTransport.Car_Sharing, DataEnumTransport.Bus, DataEnumTransport.Train);
                if(!isOneWay){
                    dataBaseMockUp.calculateRandomDataEntries(destinationCity, "Hbf", startCity, "Hbf", returnDate, true, DataEnumTransport.Car_Sharing, DataEnumTransport.Bus);
                }

                // [3] filter database
                filteredDataConnection1 = dataBaseMockUp.filterByParameters(dataBaseMockUp.getDataBaseMockUp(), properties, incomingData.get(0).getStartDate(), incomingData.get(0).getStartTime(), incomingData.get(0).getStartCity(), incomingData.get(0).getDestinationCity());
                filteredDataConnection2 = dataBaseMockUp.filterByParameters(dataBaseMockUp.getDataBaseMockUp(), properties, incomingData.get(1).getStartDate(), incomingData.get(1).getStartTime(), incomingData.get(1).getStartCity(), incomingData.get(1).getDestinationCity());
                Log.d("check", "properties: " + properties);
                Log.d("check", "filteredDataConnection1: " + filteredDataConnection1);

                // [4] send fresh data to tab1 and tab2
                SortScreen3fragment1 newFrag1 = SortScreen3fragment1.newInstance(filterResult);
                newFrag1.setDATACONNECTION(filteredDataConnection1);
                SortScreen3fragment2 newFrag2 = SortScreen3fragment2.newInstance(filterResult);
                newFrag2.setIsOneWay(isOneWay);
                newFrag2.setDATACONNECTION(filteredDataConnection2);
                adapterView = new ViewPagerAdapter(getSupportFragmentManager());
                adapterView.addFragment(newFrag1, "To");
                adapterView.addFragment(newFrag2, "Return");
                viewPager.setAdapter(adapterView);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filterResult = selectedOption[0];
                Log.d("malte", "Ergebnis, dass nichts gemacht wurde: " + filterResult);
            }
        });
    }

    private static DataEnumTransportProperties convertStringToTransportProperty(String property){
        switch (property) {
            case "eco-friendly":
                return DataEnumTransportProperties.Eco_friendly;
            case "fast":
                return DataEnumTransportProperties.Fast;
            case "reliable":
                return DataEnumTransportProperties.Reliable;
            case "comfortable":
                return DataEnumTransportProperties.Comfortable;
            case "cheap":
                return DataEnumTransportProperties.Cheap;
            case "few stops":
                return DataEnumTransportProperties.Few_stops;
            default:
                return DataEnumTransportProperties.Nothing;
        }
    }

    public void setResultFrag1Data(DataConnection resultFrag1Data) {
        this.resultFrag1Data = resultFrag1Data;
        dtoResultTo.clear();
        dtoResultTo.add(this.resultFrag1Data);
    }

    public void setResultFrag2Data(DataConnection resultFrag2Data) {
        this.resultFrag2Data = resultFrag2Data;
        dtoResultReturn.clear();
        dtoResultReturn.add(this.resultFrag2Data);
    }
    private void setupButtonListeners(){
        Button HomeButton = findViewById(R.id.button_home);
        Button LetsGo = findViewById(R.id.button_letsgo);
        Button SearchBadge = findViewById(R.id.search_badge);
        Button ResultBadge = findViewById(R.id.result_badge);

        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sortScreen3.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /**
         * sends data from this screen3 to screen4
         */
        LetsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sortScreen3.this, resultScreen4.class);
                if(isOneWay){
                    intent.putExtra("firstResult", dtoResultTo.get(0));
                } else {
                    intent.putExtra("firstResult", dtoResultTo.get(0));
                    intent.putExtra("secondResult", dtoResultReturn.get(0));
                }
                startActivity(intent);
            }
        });

        SearchBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sortScreen3.this, mainScreen2.class);
                startActivity(intent);
            }
        });
        ResultBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(sortScreen3.this, "Nothing chosen, showing only suggested trips", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(sortScreen3.this, resultScreen4.class);
                startActivity(intent);
            }
        });

    }

}