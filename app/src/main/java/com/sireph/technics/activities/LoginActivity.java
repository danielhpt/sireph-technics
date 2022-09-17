package com.sireph.technics.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.R;
import com.sireph.technics.activities.home.HomeActivity;
import com.sireph.technics.async.AsyncGetTechnician;
import com.sireph.technics.async.AsyncLogin;
import com.sireph.technics.async.AsyncLogout;
import com.sireph.technics.databinding.ActivityLoginBinding;
import com.sireph.technics.models.Technician;
import com.sireph.technics.utils.statics.Args;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements AsyncLogin.Listener, AsyncGetTechnician.Listener {
    private ActivityLoginBinding binding;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        Intent intent = getIntent();
        if (intent.hasExtra(Args.ARG_IS_LOGOUT)) {
            showLoading();
            new AsyncLogout(this::showInput).execute(intent.getStringExtra(Args.ARG_TOKEN), this);
        } else {
            token = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE).getString(getString(R.string.sharedPref_key_token), "");

            if (Objects.equals(token, "")) {
                showInput();
            } else {
                showLoading();
                new AsyncGetTechnician(this).execute(token, this);
            }
        }
    }

    private void showLoading() {
        this.binding.username.setVisibility(View.GONE);
        this.binding.password.setVisibility(View.GONE);
        this.binding.loginButton.setVisibility(View.GONE);
        this.binding.loading.setVisibility(View.VISIBLE);
    }

    private void showInput() {
        this.binding.username.setVisibility(View.VISIBLE);
        this.binding.password.setVisibility(View.VISIBLE);
        this.binding.loginButton.setVisibility(View.VISIBLE);
        this.binding.loading.setVisibility(View.GONE);
    }

    public void login(View view) {
        this.binding.loginButton.setVisibility(View.GONE);
        this.binding.loading.setVisibility(View.VISIBLE);
        new AsyncLogin(this).execute(this.binding.username.getText().toString(), this.binding.password.getText().toString(), this);
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
        Toast.makeText(this, R.string.login_error_try_again, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseTechnicianOk(Technician technician) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(Args.ARG_TOKEN, token);
        intent.putExtra(Args.ARG_TECHNICIAN, technician);
        startActivity(intent);
    }

    @Override
    public void onResponseTechnicianError() {
        onResponseLoginError();
    }

    private void showButton() {
        this.binding.loginButton.setVisibility(View.VISIBLE);
        this.binding.loading.setVisibility(View.GONE);
    }
}