package com.dk.agriculture;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SoilActivity extends AppCompatActivity {

    private double currentLat = 20.606,currentLon = 75.58;
    private String type ;
    private ListView cropListView;
    private ArrayAdapter<String> mAdapter;

    private ArrayList<String> black = new ArrayList<String>();
    private ArrayList<String> forest = new ArrayList<String>();
    private ArrayList<String> red = new ArrayList<String>();
    private ArrayList<String> alluvial = new ArrayList<String>();
    private ArrayList<String> arid = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil);
        cropListView = findViewById(R.id.cropList);

        black.add("Jowar");
        black.add("Sugarcane");
        black.add("Rice");

        red.add("Cotton");
        red.add("Wheat");
        red.add("Pulses");

        arid.add("Maze");
        arid.add("Millets");
        arid.add("Pulses");

        alluvial.add("Jute");
        alluvial.add("Wheat");
        alluvial.add("Rabi and Kharif Crops");


        forest.add("Maize");
        forest.add("Barley");

        String cropType = checkSoilType(currentLat,currentLon);

        switch(cropType){

            case "Forest & Mountains" :
                mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,forest);
                cropListView.setAdapter(mAdapter);
                break;
            case "Alluvial" :
                mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,alluvial);
                cropListView.setAdapter(mAdapter);
                break;
            case "Red and Yellow" :
                mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,red);
                cropListView.setAdapter(mAdapter);
                break;
            case "Black" :
                mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,black);
                cropListView.setAdapter(mAdapter);
                break;
            case "Arid" :
                mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arid);
                cropListView.setAdapter(mAdapter);
                break;
        }



        cropListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String cropSelect = (String) adapterView.getItemAtPosition(i);

                Intent videoIntent = new Intent(SoilActivity.this,VideoActivity.class);
                videoIntent.putExtra("crop",cropSelect);
                startActivity(videoIntent);

            }
        });



    }

    private String checkSoilType(double lat, double lon) {

        //Forest & Mountains (36.53,73.57) (35.03,80.19) (32.33,75.321) (32.083,74.78)
        //Alluvial    (31.88,75.15) (25.96,87.54) (29.53,73.677) (28.9,74.13)
        // Red and Yellow (24.88,81.11) (25.22,87.84) (17.18,83.62) (21.08,79.92)
        //Black (23.56,74.42) (24.18,82.836) (18.89,79.887) (18.15,73.11)
        //Arid (29.06,72.57) (28.514,74.987) (24.79,71.09) (26.86,69.75)

        if ((lat >= 29.0 && lat <= 36.0) && (lon >= 74.0 && lon <= 80.0) ){
            return type = "Forest & Mountains";

        }else if ((lat >= 26.0 && lat <= 32.0) && (lon >= 74.0 && lon <= 88.0) ){
            return type= "Alluvial ";

        }else if ((lat >= 8.0 && lat <= 16.0 ) && (lon >= 74.0 && lon <= 80.0)){
            return type = "Red and Yellow ";

        }else if ((lat >= 15.0 && lat <= 23.0) && (lon >= 73.0 && lon <= 78.0)){
            return type = "Black";

        }else if ((lat >= 24.0 && lat <= 30.0)&& (lon >= 61.0 && lon <= 71.0)){
            return type = "Arid";

        }else{
            return type = "Red and Yellow ";

        }


    }
}
