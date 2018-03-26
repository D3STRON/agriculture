package com.dk.agriculture;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class FetchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch);

        final Spinner stableSpinner = findViewById(R.id.stableSpinner);
        ArrayAdapter<String> stableAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.stables));
        stableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stableSpinner.setAdapter(stableAdapter);

        final Button fetch = findViewById(R.id.fetch);
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(FetchActivity.this);
                dialog.setCancelable(true);
                if(stableSpinner.getSelectedItem().toString().equals("Stable A"))
                    dialog.setContentView(R.layout.stable_row);
                else if(stableSpinner.getSelectedItem().toString().equals("Stable B"))
                    dialog.setContentView(R.layout.row_st2);
                dialog.show();
            }
        });
    }
}
