package com.sireph.technics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

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

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;
    private EditText username, password;
    private Button button;
    private ProgressBar loading;
    private Group login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        this.requestPermissions(permissions, PackageManager.PERMISSION_GRANTED);

        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.sharedPref_key_token), "");

        if (Objects.equals(token, "")) {
            loading = findViewById(R.id.progressBarMain);
            login = findViewById(R.id.groupLogin);
            loading.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
        } else {
            gotoHome(token);
        }
    }

    public void login(View view) {
        //todo
    }

    public void gotoHome(String token) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("TOKEN", token);
        startActivity(intent);
    }
}