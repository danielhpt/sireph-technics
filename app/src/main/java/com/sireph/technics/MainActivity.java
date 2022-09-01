package com.sireph.technics;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.models.Occurrence;
import com.sireph.technics.models.Team;
import com.sireph.technics.models.Technician;
import com.sireph.technics.async.AsyncGetActiveOccurrence;
import com.sireph.technics.async.AsyncGetTeam;
import com.sireph.technics.async.AsyncGetTechnicianOccurrences;
import com.sireph.technics.async.AsyncLogin;
import com.sireph.technics.async.AsyncGetTechnician;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;
    private EditText username, password;
    private Button button;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        this.requestPermissions(permissions, PackageManager.PERMISSION_GRANTED);

        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.sharedPref_key_token), "");

        if (Objects.equals(token, "")) {
            username = findViewById(R.id.editTextUsername);
            password = findViewById(R.id.editTextPassword);
            button = findViewById(R.id.buttonLogin);
            loading = findViewById(R.id.progressBarMain);

            loading.setVisibility(View.GONE);
            username.setVisibility(View.VISIBLE);
            password.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        } else {
            gotoHome(token);
        }
    }

    public void login(View view) {
        AsyncLogin login = new AsyncLogin(this);
        login.execute();
    }

    public void gotoHome(String token) {
        MainActivity mainActivity = this;
        Intent intent = new Intent(mainActivity, HomeActivity.class);
        intent.putExtra("TOKEN", token);
        new AsyncGetTechnician(output1 -> {
            Technician technician = (Technician) output1[0];
            intent.putExtra("TECHNICIAN", technician);
            new AsyncGetTeam(technician, output2 -> {
                Team team = (Team) output2[0];
                intent.putExtra("TEAM", team);
                new AsyncGetActiveOccurrence(technician, team, output3 -> {
                    Occurrence occurrence = (Occurrence) output3[0];
                    intent.putExtra("ACTIVE_OCCURRENCE", occurrence);
                    new AsyncGetTechnicianOccurrences(technician, team, occurrence, output4 -> {
                        List<Occurrence> occurrences1 = (List<Occurrence>) output4[0];
                        if (occurrences1 == null) {
                            occurrences1 = new ArrayList<>();
                        }
                        intent.putExtra("TECHNICIAN_OCCURRENCES", (Serializable) occurrences1);
                        List<Occurrence> occurrences2 = new ArrayList<>();
                        if (team != null) {
                            for (int i = 0; i < occurrences1.size(); i++) {
                                if (occurrences1.get(i).getTeam() == team) {
                                    occurrences2.add(occurrences1.get(i));
                                }
                            }
                        }
                        intent.putExtra("TEAM_OCCURRENCES", (Serializable) occurrences2);
                        startActivity(intent);
                    }).execute(token);
                }).execute(token);
            }).execute(token);
        }).execute(token);
    }

    public SharedPreferences getSharedPref() {
        return sharedPref;
    }

    public EditText getUsername() {
        return username;
    }

    public EditText getPassword() {
        return password;
    }

    public Button getButton() {
        return button;
    }

    public ProgressBar getLoading() {
        return loading;
    }
}