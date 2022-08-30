package com.sireph.technics;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.utils.RestApi;

import org.json.JSONObject;

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
        Login login = new Login();
        login.execute();
    }

    public void gotoHome(String token) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("TOKEN", token);
        startActivity(intent);
    }

    @SuppressLint("StaticFieldLeak")
    private class Login extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            button.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                JSONObject login = RestApi.login(username.getText().toString(), password.getText().toString());
                if (!login.getBoolean("is_technician")) {
                    throw new Exception("Technician not found");
                }
                String new_token = login.getString("token");
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.sharedPref_key_token), new_token);
                editor.apply();
                return new_token;
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String new_token) {
            super.onPostExecute(new_token);
            if (!Objects.equals(new_token, "")) {
                gotoHome(new_token);
            } else {
                button.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
            }
        }
    }
}