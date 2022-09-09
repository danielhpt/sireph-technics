package com.sireph.technics;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class VictimEvaluationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victim_evaluations);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}