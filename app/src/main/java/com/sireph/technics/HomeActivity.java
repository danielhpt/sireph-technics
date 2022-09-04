package com.sireph.technics;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sireph.technics.history.HistoryAdapter;
import com.sireph.technics.models.Occurrence;
import com.sireph.technics.models.Team;
import com.sireph.technics.models.Technician;
import com.sireph.technics.utils.GPS;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private String token;
    private Technician technician;
    private Team team;
    private Occurrence activeOccurrence;
    private ArrayList<Occurrence> technicianOccurrences;
    private ArrayList<Occurrence> teamOccurrences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        this.requestPermissions(permissions, PackageManager.PERMISSION_GRANTED);

        Intent intent = getIntent();
        this.token = intent.getStringExtra("TOKEN");
        this.technician = (Technician) intent.getSerializableExtra("TECHNICIAN");
        this.team = (Team) intent.getSerializableExtra("TEAM");
        this.activeOccurrence = (Occurrence) intent.getSerializableExtra("ACTIVE_OCCURRENCE");
        //noinspection unchecked
        this.technicianOccurrences = (ArrayList<Occurrence>) intent.getSerializableExtra("TECHNICIAN_OCCURRENCES");
        //noinspection unchecked
        this.teamOccurrences = (ArrayList<Occurrence>) intent.getSerializableExtra("TEAM_OCCURRENCES");

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

        List<ArrayList<Occurrence>> history = new ArrayList<>();
        history.add(this.technicianOccurrences);
        if (this.team != null) {
            history.add(this.teamOccurrences);
        }

        HistoryAdapter historyAdapter = new HistoryAdapter(getSupportFragmentManager(), getLifecycle(), history);
        ViewPager2 historyViewPager = findViewById(R.id.historyViewPager);
        historyViewPager.setAdapter(historyAdapter);

        TabLayout historyTabs = findViewById(R.id.historyTabs);
        new TabLayoutMediator(historyTabs, historyViewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText(R.string.technician);
            } else if (this.team != null) {
                tab.setText(R.string.team);
            }
        }).attach();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i : grantResults) {
            if (i == requestCode) {
                new GPS(this).isEnable(true);
                return;
            }
        }
    }
}