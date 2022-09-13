package com.sireph.technics;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sireph.technics.async.post.AsyncPostTeam;
import com.sireph.technics.async.post.AsyncPutOccurrence;
import com.sireph.technics.async.post.AsyncPutTeam;
import com.sireph.technics.dialogs.TeamDialogFragment;
import com.sireph.technics.home.TeamRecyclerViewAdapter;
import com.sireph.technics.home.history.HistoryAdapter;
import com.sireph.technics.models.Hospital;
import com.sireph.technics.models.Occurrence;
import com.sireph.technics.models.Team;
import com.sireph.technics.models.Technician;
import com.sireph.technics.utils.GPS;
import com.sireph.technics.utils.statics.Args;
import com.sireph.technics.utils.statics.Flag;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements TeamDialogFragment.TeamDialogFragmentListener {
    private String token;
    private Technician technician;
    private Team team;
    private Occurrence activeOccurrence;
    private final ActivityResultLauncher<Intent> startOccurrence = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    assert intent != null;
                    Occurrence occurrence = (Occurrence) intent.getSerializableExtra(Args.ARG_OCCURRENCE);
                    if (occurrence != null) {
                        List<Flag> flags = activeOccurrence.update(occurrence);
                        if (flags.contains(Flag.UPDATED_OCCURRENCE)) {
                            new AsyncPutOccurrence(o -> { }).execute(token, technician.getId(), occurrence);
                        }
                    }
                }
            });
    private ArrayList<Occurrence> technicianOccurrences, teamOccurrences;
    private ArrayList<Technician> allTechnicians;
    private ArrayList<Hospital> hospitals;
    private ArrayList<ArrayList<Occurrence>> history;
    private Button activeOccurrenceEnable, activeOccurrenceDisable, createTeam, endTeam;
    private RecyclerView teamList;
    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        }
        requestPermissions(permissions.toArray(new String[0]), PackageManager.PERMISSION_GRANTED);

        Intent intent = getIntent();
        this.token = intent.getStringExtra(Args.ARG_TOKEN);
        this.technician = (Technician) intent.getSerializableExtra(Args.ARG_TECHNICIAN);
        this.team = (Team) intent.getSerializableExtra(Args.ARG_TEAM);
        this.activeOccurrence = (Occurrence) intent.getSerializableExtra(Args.ARG_ACTIVE_OCCURRENCE);
        //noinspection unchecked
        this.technicianOccurrences = (ArrayList<Occurrence>) intent.getSerializableExtra(Args.ARG_TECHNICIAN_OCCURRENCES);
        //noinspection unchecked
        this.teamOccurrences = (ArrayList<Occurrence>) intent.getSerializableExtra(Args.ARG_TEAM_OCCURRENCES);
        //noinspection unchecked
        this.allTechnicians = (ArrayList<Technician>) intent.getSerializableExtra(Args.ARG_TECHNICIANS);
        //noinspection unchecked
        this.hospitals = (ArrayList<Hospital>) intent.getSerializableExtra(Args.ARG_HOSPITALS);

        activeOccurrenceEnable = findViewById(R.id.buttonActiveOccurrenceEnable);
        activeOccurrenceDisable = findViewById(R.id.buttonActiveOccurrenceDisable);

        createTeam = findViewById(R.id.buttonCreateTeam);
        endTeam = findViewById(R.id.buttonEndTeam);
        teamList = findViewById(R.id.teamList);
        history = new ArrayList<>();
        history.add(this.technicianOccurrences);

        if (this.team != null) {
            setupTeam(true);
        } else {
            eradicateTeam(true);
        }

        historyAdapter = new HistoryAdapter(getSupportFragmentManager(), getLifecycle(), history);
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

    @SuppressLint("NotifyDataSetChanged")
    private void setupTeam(boolean first) {
        history.add(this.teamOccurrences);

        teamList.setVisibility(View.VISIBLE);
        endTeam.setVisibility(View.VISIBLE);
        createTeam.setVisibility(View.GONE);

        teamList.setLayoutManager(new LinearLayoutManager(this));
        teamList.setAdapter(new TeamRecyclerViewAdapter(this.team.getTechnicians(), false, null));

        if (this.activeOccurrence != null) {
            activeOccurrenceDisable.setVisibility(View.GONE);
            activeOccurrenceEnable.setVisibility(View.VISIBLE);
        } else {
            activeOccurrenceDisable.setVisibility(View.VISIBLE);
            activeOccurrenceEnable.setVisibility(View.GONE);
        }

        if (!first) {
            historyAdapter.notifyDataSetChanged();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void eradicateTeam(boolean first) {
        teamList.setVisibility(View.GONE);
        endTeam.setVisibility(View.GONE);
        createTeam.setVisibility(View.VISIBLE);

        activeOccurrenceDisable.setVisibility(View.GONE);
        activeOccurrenceEnable.setVisibility(View.GONE);

        if (!first) {
            history.remove(1);
            historyAdapter.notifyDataSetChanged();
        }
    }

    public void createTeam(View view) {
        ArrayList<Technician> technicians = new ArrayList<>();
        technicians.add(this.technician);
        this.technician.setTeam_leader(true);
        new TeamDialogFragment(technicians, this.allTechnicians, this).show(getSupportFragmentManager(), "TeamDialogFragment");
    }

    @Override
    public void onTeamCreated(Team team) {
        for (Technician t : team.getTechnicians()) {
            t.setActive(true);
        }
        new AsyncPostTeam(result -> { }).execute(token, team);
        this.team = team;
        this.activeOccurrence = null;
        this.teamOccurrences = new ArrayList<>();
        setupTeam(false);
    }

    public void endTeam(View view) {
        team.setActive(false);
        for (Technician t : team.getTechnicians()) {
            t.setActive(false);
        }
        new AsyncPutTeam(result -> { }).execute(token, team);
        this.team = null;
        this.teamOccurrences = null;
        eradicateTeam(false);
    }

    public void openActiveOccurrence(View view) {
        Intent intent = new Intent(this, OccurrenceActivity.class);
        intent.putExtra(Args.ARG_TOKEN, this.token);
        intent.putExtra(Args.ARG_OCCURRENCE, this.activeOccurrence);
        intent.putExtra(Args.ARG_ACTIVE, true);
        intent.putExtra(Args.ARG_HOSPITALS, this.hospitals);
        this.startOccurrence.launch(intent);
    }
}