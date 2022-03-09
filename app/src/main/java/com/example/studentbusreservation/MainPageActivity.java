package com.example.studentbusreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPageActivity extends AppCompatActivity {

    private Button fromResToCampus,fromCampusToCampus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        fromResToCampus = findViewById(R.id.fromResToCampus);
        fromCampusToCampus = findViewById(R.id.fromCampusToCampus);

        fromResToCampus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),ResToCampus.class);
                startActivity(intent);
            }
        });
        fromCampusToCampus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CampusToCampus.class));
            }
        });
    }
}