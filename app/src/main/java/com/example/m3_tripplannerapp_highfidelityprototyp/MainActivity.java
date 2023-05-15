package com.example.m3_tripplannerapp_highfidelityprototyp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataConnection dataConnection = new DataConnection("Vienna", "Hbf", "Köln", "Hbf");

        Log.d("logger", "Ergebnis: " + dataConnection.toString());
        Log.d("logger", "Test Martina");
        Log.d("logger", "Test Martina2");
        Log.d("logger", "Test Martina3");
    }
}