package com.sireph.technics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class VictimInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victim_information);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}