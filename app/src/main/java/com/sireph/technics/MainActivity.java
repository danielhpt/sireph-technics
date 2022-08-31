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

import com.sireph.technics.models.Technician;
import com.sireph.technics.models.async.AsyncLogin;
import com.sireph.technics.models.async.AsyncGetTechnician;

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
        new AsyncGetTechnician(output -> {
            Intent intent = new Intent(mainActivity, HomeActivity.class);
            intent.putExtra("TOKEN", token);
            intent.putExtra("TECHNICIAN", (Technician) output[0]);
            startActivity(intent);
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