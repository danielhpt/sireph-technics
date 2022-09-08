package com.sireph.technics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class VictimEvaluationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victim_evaluations);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}