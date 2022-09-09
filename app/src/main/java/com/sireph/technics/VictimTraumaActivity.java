package com.sireph.technics;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class VictimTraumaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victim_trauma);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}