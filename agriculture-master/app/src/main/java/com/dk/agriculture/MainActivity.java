package com.dk.agriculture;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SQLiteHelper db = new SQLiteHelper(this);
        final TextInputEditText phone = findViewById(R.id.loginPhone);
        final Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phon = phone.getText().toString();
                if(db.isUserExist(phon)){
                    Toast.makeText(MainActivity.this, "User Exists!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                    finish();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                    intent.putExtra("phone", phon);
                    Toast.makeText(MainActivity.this, "User Does Not Exist!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
