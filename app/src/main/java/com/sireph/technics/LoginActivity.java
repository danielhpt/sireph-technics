package com.sireph.technics;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.async.AsyncGetActiveOccurrence;
import com.sireph.technics.async.AsyncGetHospitals;
import com.sireph.technics.async.AsyncGetTeam;
import com.sireph.technics.async.AsyncGetTechnician;
import com.sireph.technics.async.AsyncGetTechnicianOccurrences;
import com.sireph.technics.async.AsyncGetTechnicians;
import com.sireph.technics.async.AsyncLogin;
import com.sireph.technics.databinding.ActivityLoginBinding;
import com.sireph.technics.models.Occurrence;
import com.sireph.technics.utils.statics.Args;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements AsyncLogin.Listener {
    private SharedPreferences sharedPref;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = this.sharedPref.getString(getString(R.string.sharedPref_key_token), "");

        if (Objects.equals(token, "")) {
            this.binding.loading.setVisibility(View.GONE);
            this.binding.username.setVisibility(View.VISIBLE);
            this.binding.password.setVisibility(View.VISIBLE);
            this.binding.loginButton.setVisibility(View.VISIBLE);
        } else {
            gotoHome(token);
        }
    }

    public void login(View view) {
        this.binding.loginButton.setVisibility(View.GONE);
        this.binding.loading.setVisibility(View.VISIBLE);
        new AsyncLogin(this).execute(this.binding.username.getText().toString(), this.binding.password.getText().toString(), this);
    }

    public void gotoHome(String token) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(Args.ARG_TOKEN, token);
        new AsyncGetTechnician(token, technician -> {
            intent.putExtra(Args.ARG_TECHNICIAN, technician);
            new AsyncGetTeam(technician, token, team -> {
                intent.putExtra(Args.ARG_TEAM, team);
                new AsyncGetTechnicians(technician.getId(), technician.getCentral(), token, technicians -> {
                    if (technicians == null) {
                        technicians = new ArrayList<>();
                    }
                    intent.putExtra(Args.ARG_TECHNICIANS, (Serializable) technicians);
                    new AsyncGetHospitals(token, hospitals -> {
                        if (hospitals == null) {
                            hospitals = new ArrayList<>();
                        }
                        intent.putExtra(Args.ARG_HOSPITALS, (Serializable) hospitals);
                        if (team != null) {
                            new AsyncGetActiveOccurrence(technician, team, token, occurrence -> {
                                intent.putExtra(Args.ARG_ACTIVE_OCCURRENCE, occurrence);
                                new AsyncGetTechnicianOccurrences(technician, team, occurrence, token, occurrences -> {
                                    if (occurrences == null) {
                                        occurrences = new ArrayList<>();
                                    }
                                    intent.putExtra(Args.ARG_TECHNICIAN_OCCURRENCES, (Serializable) occurrences);
                                    ArrayList<Occurrence> occurrences2 = new ArrayList<>();
                                    for (int i = 0; i < occurrences.size(); i++) {
                                        if (occurrences.get(i).getTeam() == team) {
                                            occurrences2.add(occurrences.get(i));
                                        }
                                    }
                                    intent.putExtra(Args.ARG_TEAM_OCCURRENCES, occurrences2);
                                    startActivity(intent);
                                }).execute();
                            }).execute();
                        } else {
                            new AsyncGetTechnicianOccurrences(technician, null, null, token, occurrences -> {
                                if (occurrences == null) {
                                    occurrences = new ArrayList<>();
                                }
                                intent.putExtra(Args.ARG_TECHNICIAN_OCCURRENCES, (Serializable) occurrences);
                                intent.putExtra(Args.ARG_TEAM_OCCURRENCES, new ArrayList<Occurrence>());
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
        this.binding.username.setText("");
        this.binding.password.setText("");
        showButton();
        Toast.makeText(this, getString(R.string.technician_not_found), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseLoginWrongPassword() {
        this.binding.password.setText("");
        showButton();
        Toast.makeText(this, getString(R.string.wrong_password), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseLoginError() {
        this.binding.username.setText("");
        this.binding.password.setText("");
        showButton();
    }

    private void showButton() {
        this.binding.loginButton.setVisibility(View.VISIBLE);
        this.binding.loading.setVisibility(View.GONE);
    }
}