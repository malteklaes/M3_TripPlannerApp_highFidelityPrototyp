package com.example.m3_tripplannerapp_highfidelityprototyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class resultScreen4 extends AppCompatActivity {

    private List<DataConnection> inputDataConnections=new ArrayList<DataConnection>();

    private RecyclerView recyclerView;
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
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}