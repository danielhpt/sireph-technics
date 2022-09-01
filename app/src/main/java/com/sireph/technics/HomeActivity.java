package com.sireph.technics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabItem;
import com.sireph.technics.models.Occurrence;
import com.sireph.technics.models.Team;
import com.sireph.technics.models.Technician;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private String token;
    private Technician technician;
    private Team team;
    private Occurrence activeOccurrence;
    private List<Occurrence> technicianOccurrences;
    private List<Occurrence> teamOccurrences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        this.token = intent.getStringExtra("TOKEN");
        this.technician = (Technician) intent.getSerializableExtra("TECHNICIAN");
        this.team = (Team) intent.getSerializableExtra("TEAM");
        this.activeOccurrence = (Occurrence) intent.getSerializableExtra("ACTIVE_OCCURRENCE");
        this.technicianOccurrences = (List<Occurrence>) intent.getSerializableExtra("TECHNICIAN_OCCURRENCES");
        this.teamOccurrences = (List<Occurrence>) intent.getSerializableExtra("TEAM_OCCURRENCES");

        Button activeOccurrenceEnable = findViewById(R.id.buttonActiveOccurrenceEnable);
        Button activeOccurrenceDisable = findViewById(R.id.buttonActiveOccurrenceDisable);

        if (this.activeOccurrence != null) {
            activeOccurrenceDisable.setVisibility(View.GONE);
            activeOccurrenceEnable.setVisibility(View.VISIBLE);
        } else {
            activeOccurrenceEnable.setVisibility(View.GONE);
            if (this.team == null) {
                activeOccurrenceDisable.setVisibility(View.GONE);
            } else {
                activeOccurrenceDisable.setVisibility(View.VISIBLE);
            }
        }
    }
}