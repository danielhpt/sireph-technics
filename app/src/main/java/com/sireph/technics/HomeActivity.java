package com.sireph.technics;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sireph.technics.dialogs.StateDialogFragment;
import com.sireph.technics.dialogs.TeamDialogFragment;
import com.sireph.technics.dialogs.scales.race.ScaleRaceDialogFragment;
import com.sireph.technics.home.TeamRecyclerViewAdapter;
import com.sireph.technics.home.history.HistoryAdapter;
import com.sireph.technics.home.history.HistoryRecyclerViewAdapter;
import com.sireph.technics.models.Occurrence;
import com.sireph.technics.models.OccurrenceState;
import com.sireph.technics.models.Team;
import com.sireph.technics.models.Technician;
import com.sireph.technics.utils.GPS;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements HistoryRecyclerViewAdapter.OnHistoryClickListener, StateDialogFragment.StateDialogListener {
    public static String ARG_TOKEN = "1", ARG_TECHNICIAN = "2", ARG_TEAM = "3", ARG_ACTIVE_OCCURRENCE = "4", ARG_TECHNICIAN_OCCURRENCES = "5",
            ARG_TEAM_OCCURRENCES = "6";
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

        List<String> permissions = new ArrayList<String>();
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        }
        this.requestPermissions(permissions.toArray(new String[0]), PackageManager.PERMISSION_GRANTED);

        Intent intent = getIntent();
        this.token = intent.getStringExtra(ARG_TOKEN);
        this.technician = (Technician) intent.getSerializableExtra(ARG_TECHNICIAN);
        this.team = (Team) intent.getSerializableExtra(ARG_TEAM);
        this.activeOccurrence = (Occurrence) intent.getSerializableExtra(ARG_ACTIVE_OCCURRENCE);
        //noinspection unchecked
        this.technicianOccurrences = (ArrayList<Occurrence>) intent.getSerializableExtra(ARG_TECHNICIAN_OCCURRENCES);
        //noinspection unchecked
        this.teamOccurrences = (ArrayList<Occurrence>) intent.getSerializableExtra(ARG_TEAM_OCCURRENCES);

        Button activeOccurrenceEnable = findViewById(R.id.buttonActiveOccurrenceEnable);
        Button activeOccurrenceDisable = findViewById(R.id.buttonActiveOccurrenceDisable);

        Button createTeam = findViewById(R.id.buttonCreateTeam);
        Button endTeam = findViewById(R.id.buttonEndTeam);
        RecyclerView teamList = findViewById(R.id.teamList);

        List<ArrayList<Occurrence>> history = new ArrayList<>();
        history.add(this.technicianOccurrences);

        if (this.team != null) {
            history.add(this.teamOccurrences);

            teamList.setVisibility(View.VISIBLE);
            endTeam.setVisibility(View.VISIBLE);
            createTeam.setVisibility(View.GONE);

            teamList.setLayoutManager(new LinearLayoutManager(this));
            teamList.setAdapter(new TeamRecyclerViewAdapter(this.team.getTechnicians(), false));

            if (this.activeOccurrence != null) {
                activeOccurrenceDisable.setVisibility(View.GONE);
                activeOccurrenceEnable.setVisibility(View.VISIBLE);
            } else {
                activeOccurrenceDisable.setVisibility(View.VISIBLE);
                activeOccurrenceEnable.setVisibility(View.GONE);
            }
        } else {
            teamList.setVisibility(View.GONE);
            endTeam.setVisibility(View.GONE);
            createTeam.setVisibility(View.VISIBLE);

            activeOccurrenceDisable.setVisibility(View.GONE);
            activeOccurrenceEnable.setVisibility(View.GONE);
        }

        HistoryAdapter historyAdapter = new HistoryAdapter(getSupportFragmentManager(), getLifecycle(), history, this);
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

        StateDialogFragment fragment = new StateDialogFragment();
        fragment.show(getSupportFragmentManager(), "StateDialogFragment");
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

    @Override
    public void onHistoryClick(Occurrence occurrence) {
        openOccurrence(occurrence, false);
    }

    public void createTeam(View view) {
        // todo
    }

    public void endTeam(View view) {
        // todo
    }

    private void openOccurrence(Occurrence occurrence, boolean isActive) {
        Intent intent = new Intent(this, OccurrenceActivity.class);
        intent.putExtra(OccurrenceActivity.ARG_TOKEN, this.token);
        intent.putExtra(OccurrenceActivity.ARG_TECHNICIAN, this.technician);
        intent.putExtra(OccurrenceActivity.ARG_OCCURRENCE, occurrence);
        intent.putExtra(OccurrenceActivity.ARG_ACTIVE, isActive);
        startActivity(intent);
    }

    public void openActiveOccurrence(View view) {
        openOccurrence(this.activeOccurrence, true);
    }

    @Override
    public void onStateDialogOk(DialogFragment dialog, OccurrenceState state) {

    }

    @Override
    public void onStateDialogCancel(DialogFragment dialog) {

    }
}