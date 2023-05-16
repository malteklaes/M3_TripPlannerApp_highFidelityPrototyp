package com.example.m3_tripplannerapp_highfidelityprototyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class sortScreen3 extends AppCompatActivity {

    // mocked dataBase
    List<DataConnection> dataBaseMock = new ArrayList<>();
    DataBaseMockUp dataBaseMockUp = new DataBaseMockUp();
    List<DataConnection> special = new ArrayList<>();
    List<DataEnumTransportProperties> properties = new ArrayList<>();

    List<DataConnection> incomingData = new ArrayList<>();

    // filter dropdown
    Spinner filterSpinner;
    String filterResult = "";
    DataEnumTransportProperties filterResultProperty = DataEnumTransportProperties.Nothing;
    final String[] selectedOption = {""};
    String[] options = {"eco-friendly", "fast", "reliable", "comfortable", "cheap", "few stops", "nothing"};

    // tablayout
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // incoming data
        incomingData.add(new DataConnection("vienna", "hbf", "innsbruck", "hbf",new DataDate(15,5,2023), new DataTime(42,12)));
        incomingData.add(new DataConnection("innsbruck", "hbf", "vienna", "hbf",new DataDate(15,5,2023), new DataTime(42,18)));

        // [0] general set up
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_screen3);

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.toReturnTabLayout);

        adapterView = new ViewPagerAdapter(getSupportFragmentManager());
        adapterView.addFragment(new SortScreen3fragment1(), "To");
        adapterView.addFragment(new SortScreen3fragment2(), "Return");
        viewPager.setAdapter(adapterView);

        tabLayout.setupWithViewPager(viewPager);
        SortScreen3fragment1 neu = SortScreen3fragment1.newInstance("hallo");

        // klappt nicht mehr
        //adapterView.addFragment(neu, "To");
        //adapterView.addFragment(SortScreen3fragment1.newInstance("hallo"), "To");
        //adapterView.addFragment(SortScreen3fragment2.newInstance("hallo", "ulli"), "Return");
        /*SortScreen3fragment1 fragment1 = new SortScreen3fragment1();
        Log.d("malte1" , "ergebnis: " + fragment1.toString() + " und ists null: " + (fragment1==null)) ;
        getSupportFragmentManager().beginTransaction().replace(R.id.containterScreen3Frag1, fragment1).commit();
        Log.d("malte1" , "ergebnis2: " + fragment1.toString() + " und ists null: " + (fragment1==null)) ;*/


        // ------------------------------------------------------------------------------------------------------------------
        // [1] example mock data: scenario back and forth (vienna->frankfurt, frankfurt->vienna)
        dataBaseMockUp = new DataBaseMockUp("vienna", "hbf", "innsbruck", "hbf", new DataDate(15,5,2023), DataEnumTransport.Train);
        dataBaseMockUp.addAnotherDay("vienna", "hbf", "innsbruck", "hbf",new DataDate(15,5,2023), DataEnumTransport.Car);
        dataBaseMockUp.addAnotherDay("vienna", "hbf", "innsbruck", "hbf",new DataDate(15,5,2023), DataEnumTransport.Bus);

        Log.d("malte", "Ergbnis mocked db: " + dataBaseMockUp.toString());



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

        // ------------------------------------------------------------------------------------------------------------------
        // [3] show data to the tablayouts

        /*
        => diese Stelle in externe Methode auslagern, weil sie sich mit dem DropDown-Menu ändern muss
        an dieser Stelle 3 Daten weitergeben:
        - filterResultProperty
        - database
        - dataConnection aus der incomingData (0 für hin und 1 für zurück)
        => Daten filtern und dann als Ergebnis List<DataConnection> weiterschicken

        [schließlich wird in jedem Fragment der tabLayout eigentlich gefiltert
         */
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
                // send data to tab1 "to"
                SortScreen3fragment1 neuFrag1 = SortScreen3fragment1.newInstance(filterResult);
                neuFrag1.setArgParam1(filterResult);
                SortScreen3fragment2 neuFrag2 = SortScreen3fragment2.newInstance(filterResult);
                neuFrag2.setArgParam1(filterResult+" for return");
                Log.d("malte2", "neu2 Instanz" + neuFrag1.getArguments());
                adapterView = new ViewPagerAdapter(getSupportFragmentManager());
                adapterView.addFragment(neuFrag1, "To");
                adapterView.addFragment(neuFrag2, "Return");
                viewPager.setAdapter(adapterView);
                // database related
                filterResultProperty = convertStringToTransportProperty(filterResult);
                properties.clear();
                properties.add(filterResultProperty);
                // refill data base
                dataBaseMockUp = new DataBaseMockUp("vienna", "hbf", "innsbruck", "hbf", new DataDate(15,5,2023), DataEnumTransport.Train);
                dataBaseMockUp.addAnotherDay("vienna", "hbf", "innsbruck", "hbf",new DataDate(15,5,2023), DataEnumTransport.Car);
                dataBaseMockUp.addAnotherDay("vienna", "hbf", "innsbruck", "hbf",new DataDate(15,5,2023), DataEnumTransport.Bus);
                dataBaseMockUp.addAnotherDay("vienna", "hbf", "innsbruck", "hbf",new DataDate(16,5,2023), DataEnumTransport.Bus);
                dataBaseMockUp.addAnotherDay("vienna", "hbf", "frankfurt", "hbf",new DataDate(15,5,2023), DataEnumTransport.Train);
                Log.d("malte", "Ergebnis database size: " + dataBaseMockUp.getDataBaseMockUp().size());
                // filter database
                special = dataBaseMockUp.retrieveDataByTransportProperties(dataBaseMockUp.getDataBaseMockUp(), properties);
                Log.d("malte", "special details: "  + special.toString());

                // actual filter due to incoming data
                special = dataBaseMockUp.retrieveDataByStartDateEqual(special, incomingData.get(0).getStartDate());
                special = dataBaseMockUp.retrieveDataByStartTimeLater(special, incomingData.get(0).getStartTime());
                //special = dataBaseMockUp.retrieveDataByStartTimeLater(special, new DataTime(40,12));
                special = dataBaseMockUp.retrieveDataByStartCityAndLocation(special, incomingData.get(0).getStartCity(), incomingData.get(0).getStartLocation());

                String loggerResult = "Result { ";
                for (DataConnection c: special) {
                    loggerResult += c.toStringShort();
                }
                loggerResult += " }";
                Log.d("malte", "Ergebnis Suche nach property: " + properties.toString() + " ist: " + loggerResult);
                Log.d("malte", "Ergebnis Suche nach property: " + properties.toString() + " ist: " + special.size());




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

    private void setupButtonListeners(){
        Button HomeButton = findViewById(R.id.button_home);
        Button LetsGo = findViewById(R.id.button_letsgo);
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        LetsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}