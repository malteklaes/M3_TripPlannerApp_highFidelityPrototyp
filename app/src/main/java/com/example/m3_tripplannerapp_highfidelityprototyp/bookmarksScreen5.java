package com.example.m3_tripplannerapp_highfidelityprototyp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class bookmarksScreen5 extends AppCompatActivity {

    private static List<DataConnection> bookmarks=new ArrayList<DataConnection>();  //all trips the user saved

    private RecyclerView recyclerView;   //recyclerview for input data
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks_screen5);

        createRecyclerView();

        setupButtonListeners();
    }


    private void setupButtonListeners(){
        Button HomeButton = findViewById(R.id.button_home);
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bookmarksScreen5.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void createRecyclerView(){
        recyclerView=findViewById(R.id.bookmarksRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        adapter=new BookmarksAdapter(bookmarks,context);
        recyclerView.setAdapter(adapter);
    }

    public static void addBookmark(DataConnection bookmark){
        System.out.println("Bookmark already set: "+bookmarks.contains(bookmark));
        if(!bookmarks.contains(bookmark))
            bookmarks.add(bookmark);
    }
    public static void removeBookmark(DataConnection bookmark){
        if(bookmarks.contains(bookmark))
            bookmarks.remove(bookmark);
    }
}