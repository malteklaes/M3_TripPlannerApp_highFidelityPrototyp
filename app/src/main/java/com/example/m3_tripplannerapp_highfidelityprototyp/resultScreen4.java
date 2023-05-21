package com.example.m3_tripplannerapp_highfidelityprototyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class resultScreen4 extends AppCompatActivity {

    private List<DataConnection> inputDataConnections=new ArrayList<DataConnection>(); //data received from sortScreen3

    private RecyclerView recyclerView;   //recyclerview for input data
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Context context=this;

    DataConnection resultTo;
    DataConnection resultReturn;
    DataConnection originalIncomingData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen4);

        createRecyclerView();

        setupButtonListeners(); //Buttons Listener Method

        //retrieving chosen trip
        Intent intent = getIntent();
        resultTo = (DataConnection) intent.getSerializableExtra("firstResult");
        resultReturn = (DataConnection) intent.getSerializableExtra("secondResult");
        originalIncomingData = (DataConnection) intent.getSerializableExtra("originalIncomingData");
        // falls nur one way, dann ist resultReturn == NULL !

        Log.d("transaction2" , "4 firstResult: " + resultTo);
        Log.d("transaction2" , "4 secondResult: " + resultReturn);
        Log.d("transaction2" , "4 originalIncomingData: " + originalIncomingData);



        //if DataConnections are set, they are added to the received data list
        if(resultTo!=null) {
            inputDataConnections.add(resultTo);
            Log.d("transaction3", " incoming data im if: " + resultTo);
        }
        if(resultReturn!=null)
            inputDataConnections.add(resultReturn);
    }

    /**
     * method for creating RecyclerView for Trips in result screen
     */
    public void createRecyclerView(){
        recyclerView=findViewById(R.id.resultsRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        adapter=new ResultAdapter(inputDataConnections,context);
        recyclerView.setAdapter(adapter);
    }


    /**
     * method for setting button listeners for Home-, Search- and Sort-Button
     */
    private void setupButtonListeners(){
        Button HomeButton = findViewById(R.id.button_home);
        Button SearchBadge = findViewById(R.id.search_badge);
        Button SortBadge = findViewById(R.id.sort_badge);

        HomeButton.setOnClickListener(new View.OnClickListener() {
            /**
             * defines what happens when HomeButton is clicked
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resultScreen4.this, MainActivity.class);
                startActivity(intent);
            }
        });

        SearchBadge.setOnClickListener(new View.OnClickListener() {
            /**
             * defines what happens when SearchButton is clicked
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resultScreen4.this, mainScreen2.class);
                startActivity(intent);
            }
        });
        SortBadge.setOnClickListener(new View.OnClickListener() {
            /**
             * defines what happens when SortButton is clicked
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resultScreen4.this, sortScreen3.class);
                if(originalIncomingData != null) {
                    intent.putExtra("startCity", originalIncomingData.getStartCity());
                    intent.putExtra("destinationCity", originalIncomingData.getDestinationCity());
                    intent.putExtra("startDate", originalIncomingData.getStartDate());
                    intent.putExtra("startTime", originalIncomingData.getStartTime());
                    intent.putExtra("returnDate", originalIncomingData.getReturnDate());
                    intent.putExtra("returnTime", originalIncomingData.getReturnTime());
                    intent.putExtra("isOneWay", originalIncomingData.isReturn());
                }
                startActivity(intent);
            }
        });
    }
}