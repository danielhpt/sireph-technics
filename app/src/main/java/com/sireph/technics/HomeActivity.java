package com.sireph.technics;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.models.Team;
import com.sireph.technics.models.Technician;

public class HomeActivity extends AppCompatActivity {
    private String token;
    private Technician technician;
    private Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}