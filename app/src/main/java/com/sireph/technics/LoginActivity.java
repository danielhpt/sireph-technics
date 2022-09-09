package com.sireph.technics;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.async.AsyncGetActiveOccurrence;
import com.sireph.technics.async.AsyncGetHospitals;
import com.sireph.technics.async.AsyncGetTeam;
import com.sireph.technics.async.AsyncGetTechnician;
import com.sireph.technics.async.AsyncGetTechnicianOccurrences;
import com.sireph.technics.async.AsyncGetTechnicians;
import com.sireph.technics.async.AsyncLogin;
import com.sireph.technics.models.Occurrence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements AsyncLogin.AsyncLoginListener {
    private SharedPreferences sharedPref;
    private EditText username, password;
    private Button button;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        this.button.setVisibility(View.GONE);
        this.loading.setVisibility(View.VISIBLE);
        AsyncLogin login = new AsyncLogin(this.username.getText().toString(), this.password.getText().toString(), this);
        login.execute();
    }

    public void gotoHome(String token) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(HomeActivity.ARG_TOKEN, token);
        new AsyncGetTechnician(token, technician -> {
            intent.putExtra(HomeActivity.ARG_TECHNICIAN, technician);
            new AsyncGetTeam(technician, token, team -> {
                intent.putExtra(HomeActivity.ARG_TEAM, team);
                new AsyncGetTechnicians(technician, technician.getCentral(), token, technicians -> {
                    if (technicians == null) {
                        technicians = new ArrayList<>();
                    }
                    intent.putExtra(HomeActivity.ARG_TECHNICIANS, (Serializable) technicians);
                    new AsyncGetHospitals(token, hospitals -> {
                        if (hospitals == null) {
                            hospitals = new ArrayList<>();
                        }
                        intent.putExtra(HomeActivity.ARG_HOSPITALS, (Serializable) hospitals);
                        if (team != null) {
                            new AsyncGetActiveOccurrence(technician, team, token, occurrence -> {
                                intent.putExtra(HomeActivity.ARG_ACTIVE_OCCURRENCE, occurrence);
                                new AsyncGetTechnicianOccurrences(technician, team, occurrence, token, occurrences -> {
                                    if (occurrences == null) {
                                        occurrences = new ArrayList<>();
                                    }
                                    intent.putExtra(HomeActivity.ARG_TECHNICIAN_OCCURRENCES, (Serializable) occurrences);
                                    ArrayList<Occurrence> occurrences2 = new ArrayList<>();
                                    for (int i = 0; i < occurrences.size(); i++) {
                                        if (occurrences.get(i).getTeam() == team) {
                                            occurrences2.add(occurrences.get(i));
                                        }
                                    }
                                    intent.putExtra(HomeActivity.ARG_TEAM_OCCURRENCES, occurrences2);
                                    startActivity(intent);
                                }).execute();
                            }).execute();
                        } else {
                            new AsyncGetTechnicianOccurrences(technician, null, null, token, occurrences -> {
                                if (occurrences == null) {
                                    occurrences = new ArrayList<>();
                                }
                                intent.putExtra(HomeActivity.ARG_TECHNICIAN_OCCURRENCES, (Serializable) occurrences);
                                intent.putExtra(HomeActivity.ARG_TEAM_OCCURRENCES, new ArrayList<Occurrence>());
                                startActivity(intent);
                            }).execute();
                        }
                    }).execute();
                }).execute();
            }).execute();
        }).execute();
    }

    @Override
    public void onResponseLoginOk(String newToken) {
        gotoHome(newToken);
    }

    @Override
    public void onResponseLoginNotFound() {
        this.username.setText("");
        this.password.setText("");
        showButton();
        Toast.makeText(this, getString(R.string.technician_not_found), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseLoginWrongPassword() {
        this.password.setText("");
        showButton();
        Toast.makeText(this, getString(R.string.wrong_password), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseLoginError() {
        this.username.setText("");
        this.password.setText("");
        showButton();
    }

    private void showButton() {
        this.button.setVisibility(View.VISIBLE);
        this.loading.setVisibility(View.GONE);
    }
}