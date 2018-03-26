package com.dk.agriculture;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        if(intent.getStringExtra("phone") == null) {
            startActivity(new Intent(InfoActivity.this, MainActivity.class));
            finish();
        }
        final String phone = intent.getStringExtra("phone");

        final SQLiteHelper db = new SQLiteHelper(this);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference farmer = database.getReference("farmer");

        final TextInputEditText name = findViewById(R.id.input_name);
        final TextInputEditText location = findViewById(R.id.input_location);

        final Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteUsers();
                db.addUserInfo(location.getText().toString(), name.getText().toString(), phone);
                farmer.child(phone).setValue(new FarmerInfo(name.getText().toString(), location.getText().toString()))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    startActivity(new Intent(InfoActivity.this, DashboardActivity.class));
                                    finish();
                                }
                                else
                                    Toast.makeText(InfoActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
