package com.sireph.technics;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class VictimProceduresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victim_procedures);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}