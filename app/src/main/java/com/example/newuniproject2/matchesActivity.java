package com.example.newuniproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;

public class matchesActivity extends AppCompatActivity {
    ListView l;
    String tutorials[]
            = { "Algorithms", "Data Structures",
            "Languages", "Interview Corner",
            "GATE", "ISRO CS",
            "UGC NET CS", "CS Subjects",
            "Web Technologies" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Intent i = getIntent();
        //List matches = (List) i.getSerializableExtra("MainActivity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        l = findViewById(R.id.list);
        ArrayAdapter<String> arr;
        arr
                = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                tutorials);
        l.setAdapter(arr);
    }
}