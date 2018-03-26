package com.dk.agriculture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdEquipmentActivity extends AppCompatActivity {
    Button postad;
    EditText maxdays, priceperday, equipmentdesc;
    Spinner equipments;
    Double latitude, longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_equipment);
        postad= findViewById(R.id.postad);

        final SQLiteHelper db = new SQLiteHelper(this);
        final DatabaseReference equipmentbase= FirebaseDatabase.getInstance().getReference("farmer").child(db.getAllValues().get("phone")).child("myAds");
        final DatabaseReference marketbase = FirebaseDatabase.getInstance().getReference("market");
        maxdays= findViewById(R.id.maxdays);
        priceperday= findViewById(R.id.priceperday);
        equipmentdesc= findViewById(R.id.equipmentdesc);
        equipments= findViewById(R.id.equipments);

        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("lat", 0.0);
        longitude = intent.getDoubleExtra("lang", 0.0);

        ArrayAdapter<String> mAdapterComponent= new ArrayAdapter<String>(AdEquipmentActivity.this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.equipments));
        mAdapterComponent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipments.setAdapter(mAdapterComponent);
        equipments.setSelection(1);

        postad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = equipmentbase.push().getKey();
                AddModel addModel = new AddModel(equipments.getSelectedItem().toString(),equipmentdesc.getText().toString(),priceperday.getText().toString(),maxdays.getText().toString(),key);
                Toast.makeText(AdEquipmentActivity.this, FirebaseDatabase.getInstance().toString(), Toast.LENGTH_SHORT).show();
                equipmentbase.child(key).setValue(addModel);
                MarketAddModel marketAddModel = new MarketAddModel(equipments.getSelectedItem().toString(),db.getAllValues().get("name"),equipmentdesc.getText().toString()
                        ,priceperday.getText().toString(),maxdays.getText().toString(),db.getAllValues().get("location"),db.getAllValues().get("phone"),latitude.toString() ,longitude.toString(),"0",key);
                marketbase.child(equipments.getSelectedItem().toString()).child(key).setValue(marketAddModel);
                finish();
            }
        });
    }
}