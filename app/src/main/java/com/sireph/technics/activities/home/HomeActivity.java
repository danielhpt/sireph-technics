package com.sireph.technics.activities.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.tabs.TabLayoutMediator;
import com.sireph.technics.R;
import com.sireph.technics.activities.LoginActivity;
import com.sireph.technics.activities.home.history.HistoryAdapter;
import com.sireph.technics.activities.occurrence.OccurrenceActivity;
import com.sireph.technics.async.AsyncInitializeHome;
import com.sireph.technics.async.post.AsyncPostTeam;
import com.sireph.technics.async.put.AsyncPutOccurrence;
import com.sireph.technics.async.put.AsyncPutTeam;
import com.sireph.technics.databinding.ActivityHomeBinding;
import com.sireph.technics.dialogs.TeamDialog;
import com.sireph.technics.models.Hospital;
import com.sireph.technics.models.Team;
import com.sireph.technics.models.Technician;
import com.sireph.technics.models.occurrence.Occurrence;
import com.sireph.technics.utils.GPS;
import com.sireph.technics.utils.statics.Args;
import com.sireph.technics.utils.statics.Flag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements TeamDialog.TeamDialogFragmentListener, AsyncInitializeHome.Listener {
    private String token;
    private Technician technician;
    private Team team;
    private Occurrence activeOccurrence;
    private List<Occurrence> teamOccurrences;
    private List<Technician> allTechnicians;
    private List<Hospital> hospitals;
    private List<List<Occurrence>> history;
    private HistoryAdapter historyAdapter;
    private ActivityHomeBinding binding;
    private final ActivityResultLauncher<Intent> startOccurrence = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    assert intent != null;
                    Occurrence occurrence = (Occurrence) intent.getSerializableExtra(Args.ARG_OCCURRENCE);
                    if (occurrence != null) {
                        List<Flag> flags = activeOccurrence.update(occurrence);
                        if (flags.contains(Flag.UPDATED_OCCURRENCE)) {
                            if (!activeOccurrence.isActive()) {
                                beginRefresh();
                                binding.pullToRefresh.setRefreshing(true);
                                new AsyncPutOccurrence(o -> new AsyncInitializeHome(this).execute(token, technician)).execute(token, technician.getId(), occurrence);
                            } else {
                                new AsyncPutOccurrence(o -> {
                                }).execute(token, technician.getId(), occurrence);
                            }
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.included.toolbar);

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

        binding.pullToRefresh.setOnRefreshListener(() -> {
            beginRefresh();

            new AsyncInitializeHome(this).execute(token, technician);
        });
        binding.pullToRefresh.setRefreshing(true);

        history = new ArrayList<>();
        historyAdapter = new HistoryAdapter(getSupportFragmentManager(), getLifecycle(), history);
        binding.historyViewPager.setAdapter(historyAdapter);
        new TabLayoutMediator(binding.historyTabs, binding.historyViewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText(R.string.technician);
            } else if (this.team != null) {
                tab.setText(R.string.team);
            }
        }).attach();

        new AsyncInitializeHome(this).execute(token, technician);
    }

    private void beginRefresh() {
        binding.teamList.setVisibility(View.GONE);
        binding.buttonEndTeam.setVisibility(View.GONE);
        binding.buttonCreateTeam.setVisibility(View.GONE);
        binding.buttonActiveOccurrenceEnable.setVisibility(View.GONE);
        binding.buttonActiveOccurrenceDisable.setVisibility(View.GONE);
        binding.historyViewPager.setVisibility(View.GONE);
        binding.historyTabs.setVisibility(View.GONE);
        binding.loadingHistory.setVisibility(View.VISIBLE);
        binding.loadingOccurrence.setVisibility(View.VISIBLE);
        binding.loadingTeam.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResponseTechnicians(List<Technician> technicians) {
        this.allTechnicians = technicians;
    }

    @Override
    public void onResponseHospitals(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

    @Override
    public void onResponseTeam(Team team) {
        this.team = team;
        binding.teamList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResponseTeamTechniciansDone() {
        binding.loadingTeam.setVisibility(View.GONE);

        if (team == null) {
            binding.buttonCreateTeam.setVisibility(View.VISIBLE);
        } else {
            binding.buttonEndTeam.setVisibility(View.VISIBLE);
            binding.buttonEndTeam.setEnabled(false);
        }
    }

    @Override
    public void onResponseOccurrences(List<Occurrence> technicianOccurrences, List<Occurrence> teamOccurrences) {
        this.teamOccurrences = teamOccurrences;
        if (!history.isEmpty()) {
            history.remove(0);
        }
        history.add(0, technicianOccurrences);

        if (this.team != null) {
            setupTeam();
        } else {
            eradicateTeam();
        }
        binding.loadingHistory.setVisibility(View.GONE);
        binding.historyViewPager.setVisibility(View.VISIBLE);
        binding.historyTabs.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResponseActiveOccurrence(Occurrence activeOccurrence) {
        this.activeOccurrence = activeOccurrence;

        binding.loadingOccurrence.setVisibility(View.GONE);
        if (this.activeOccurrence != null) {
            binding.buttonEndTeam.setEnabled(false);
            binding.buttonActiveOccurrenceDisable.setVisibility(View.GONE);
            binding.buttonActiveOccurrenceEnable.setVisibility(View.VISIBLE);
        } else {
            binding.buttonEndTeam.setEnabled(true);
            if (team != null) {
                binding.buttonActiveOccurrenceDisable.setVisibility(View.VISIBLE);
            }
            binding.buttonActiveOccurrenceEnable.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefreshDone() {
        binding.pullToRefresh.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.menuUsername).setTitle(technician.getUser().getFullName());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuUsername) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.logout)
                    .setMessage(R.string.confirm_logout)
                    .setPositiveButton(R.string.yes, (dialog, id) -> {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.putExtra(Args.ARG_TOKEN, token);
                        intent.putExtra(Args.ARG_IS_LOGOUT, true);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    })
                    .setNegativeButton(R.string.no, null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
    private void setupTeam() {
        if (history.size() == 2) {
            history.remove(1);
        }
        history.add(1, this.teamOccurrences);
        historyAdapter.notifyDataSetChanged();

        binding.teamList.setVisibility(View.VISIBLE);
        binding.buttonEndTeam.setVisibility(View.VISIBLE);
        binding.buttonCreateTeam.setVisibility(View.GONE);

        binding.teamList.setLayoutManager(new LinearLayoutManager(this));
        binding.teamList.setAdapter(new TeamRecyclerViewAdapter(this.team.getTechnicians(), false, null));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void eradicateTeam() {
        binding.teamList.setVisibility(View.GONE);
        binding.buttonEndTeam.setVisibility(View.GONE);
        binding.buttonCreateTeam.setVisibility(View.VISIBLE);

        binding.buttonActiveOccurrenceDisable.setVisibility(View.GONE);
        binding.buttonActiveOccurrenceEnable.setVisibility(View.GONE);

        if (history.size() == 2) {
            history.remove(1);
        }
        historyAdapter.notifyDataSetChanged();
    }

    public void createTeam(View view) {
        ArrayList<Technician> technicians = new ArrayList<>();
        technicians.add(this.technician);
        this.technician.setTeam_leader(true);
        new TeamDialog(technicians, this.allTechnicians, this).show(getSupportFragmentManager(), "TeamDialogFragment");
    }

    @Override
    public void onTeamCreated(@NonNull Team team) {
        for (Technician t : team.getTechnicians()) {
            t.setActive(true);
        }
        new AsyncPostTeam(result -> {
        }).execute(token, 0, team);
        this.team = team;
        this.activeOccurrence = null;
        this.teamOccurrences = new ArrayList<>();
        binding.buttonActiveOccurrenceDisable.setVisibility(View.VISIBLE);
        setupTeam();
    }

    public void endTeam(View view) {
        team.setActive(false);
        for (Technician t : team.getTechnicians()) {
            t.setActive(false);
        }
        new AsyncPutTeam(result -> {
        }).execute(token, 0, team);
        this.team = null;
        this.teamOccurrences = null;
        eradicateTeam();
    }

    public void openActiveOccurrence(View view) {
        Intent intent = new Intent(this, OccurrenceActivity.class);
        intent.putExtra(Args.ARG_TOKEN, this.token);
        intent.putExtra(Args.ARG_OCCURRENCE, this.activeOccurrence);
        intent.putExtra(Args.ARG_IS_ACTIVE, true);
        intent.putExtra(Args.ARG_HOSPITALS, (Serializable) this.hospitals);
        this.startOccurrence.launch(intent);
    }
}