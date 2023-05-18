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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataConnection mockedResult1=new DataConnection("Vienna","Hbf","München","Hbf",new DataDate(20,4,2004),new DataTime(14,13));
        DataConnection mockedResult1_1=new DataConnection("Vienna","Hbf","Linz","Hbf",new DataDate(20,4,2004),new DataTime(14,13));
        DataConnection mockedResult1_2=new DataConnection("Linz","Hbf","München","Hbf",new DataDate(20,4,2004),new DataTime(14,13));
        ArrayList<DataConnection> stops=new ArrayList<>();
        stops.add(mockedResult1_1);
        stops.add(mockedResult1_2);
        mockedResult1.setIntermediatStations(stops);
        inputDataConnections.add(mockedResult1);
        DataConnection mockedResult2=new DataConnection("Graz","Hbf","St. Pölten","Hbf",new DataDate(20,4,2004),new DataTime(14,13));
        inputDataConnections.add(mockedResult2);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen4);

        createRecyclerView();

        setupButtonListeners(); //Buttons Listener Method

        //retrieving chosen trip
        Intent intent = getIntent();
        //DataConnection resultTo = (DataConnection) intent.getSerializableExtra("selectedToTrip");
        DataConnection resultTo = (DataConnection) intent.getSerializableExtra("firstResult");
        Log.d("transaction3", " incoming data" + resultTo);
        DataConnection resultReturn = (DataConnection) intent.getSerializableExtra("firstResult");
        //DataConnection resultReturn = (DataConnection) intent.getSerializableExtra("selectedReturnTrip");



        //if DataConnections are set, they are added to the received data list
        if(resultTo!=null) {
            inputDataConnections.add(resultTo);
            Log.d("transaction3", " incoming data im if: " + resultTo);
        }
        if(resultReturn!=null)
            inputDataConnections.add(resultReturn);
    }

    public void createRecyclerView(){
        recyclerView=findViewById(R.id.resultsRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        adapter=new ResultAdapter(inputDataConnections,context);
        recyclerView.setAdapter(adapter);
    }

    private void setupButtonListeners(){
        Button HomeButton = findViewById(R.id.button_home);
        Button SearchBadge = findViewById(R.id.search_badge);
        Button SortBadge = findViewById(R.id.sort_badge);

        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resultScreen4.this, MainActivity.class);
                startActivity(intent);
            }
        });

        SearchBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resultScreen4.this, mainScreen2.class);
                startActivity(intent);
            }
        });
        SortBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resultScreen4.this, sortScreen3.class);
                startActivity(intent);
            }
        });
    }
}