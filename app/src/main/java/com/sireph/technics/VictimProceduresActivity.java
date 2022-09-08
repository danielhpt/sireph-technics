package com.sireph.technics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class VictimProceduresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victim_procedures);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}