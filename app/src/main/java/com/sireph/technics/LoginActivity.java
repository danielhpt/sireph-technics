package com.sireph.technics;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.async.AsyncGetTechnician;
import com.sireph.technics.async.AsyncLogin;
import com.sireph.technics.databinding.ActivityLoginBinding;
import com.sireph.technics.models.Technician;
import com.sireph.technics.utils.statics.Args;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements AsyncLogin.Listener, AsyncGetTechnician.Listener {
    private SharedPreferences sharedPref;
    private ActivityLoginBinding binding;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        token = this.sharedPref.getString(getString(R.string.sharedPref_key_token), "");

        if (Objects.equals(token, "")) {
            this.binding.loading.setVisibility(View.GONE);
            this.binding.username.setVisibility(View.VISIBLE);
            this.binding.password.setVisibility(View.VISIBLE);
            this.binding.loginButton.setVisibility(View.VISIBLE);
        } else {
            new AsyncGetTechnician(this).execute(token, this);
        }
    }

    public void login(View view) {
        this.binding.loginButton.setVisibility(View.GONE);
        this.binding.loading.setVisibility(View.VISIBLE);
        new AsyncLogin(this).execute(this.binding.username.getText().toString(), this.binding.password.getText().toString(), this);
    }

    private void gotoHome(Technician technician) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(Args.ARG_TOKEN, token);
        intent.putExtra(Args.ARG_TECHNICIAN, technician);
        startActivity(intent);
    }

    @Override
    public void onResponseLoginOk(String newToken) {
        this.token = newToken;
        new AsyncGetTechnician(this).execute(newToken, this);
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

    @Override
    public void onResponseTechnician(Technician technician) {
        gotoHome(technician);
    }

    private void showButton() {
        this.binding.loginButton.setVisibility(View.VISIBLE);
        this.binding.loading.setVisibility(View.GONE);
    }
}