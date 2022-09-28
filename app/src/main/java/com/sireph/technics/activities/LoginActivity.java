package com.sireph.technics.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.BuildConfig;
import com.sireph.technics.R;
import com.sireph.technics.activities.home.HomeActivity;
import com.sireph.technics.async.AsyncGetTechnician;
import com.sireph.technics.async.AsyncLogin;
import com.sireph.technics.async.AsyncLogout;
import com.sireph.technics.databinding.ActivityLoginBinding;
import com.sireph.technics.models.Technician;
import com.sireph.technics.test.Action;
import com.sireph.technics.test.AsyncPostTest;
import com.sireph.technics.test.AsyncPutTest;
import com.sireph.technics.test.Test;
import com.sireph.technics.utils.statics.Args;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements AsyncLogin.Listener, AsyncGetTechnician.Listener {
    private ActivityLoginBinding binding;
    private String token;
    private Test test = null;

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

        if (BuildConfig.IS_TEST) {
            binding.buttonBeginTest.setVisibility(View.VISIBLE);
            binding.buttonEndTest.setVisibility(View.VISIBLE);

            binding.buttonBeginTest.setOnClickListener(v -> {
                showLoading();
                binding.buttonBeginTest.setEnabled(false);
                new AsyncPostTest(result -> {
                    test = (Test) result;
                    showInput();
                    binding.buttonEndTest.setEnabled(true);
                }).execute(new Test(), Action.START_TEST, null);
            });

            binding.buttonEndTest.setOnClickListener(v -> {
                showLoading();
                binding.buttonEndTest.setEnabled(false);
                new AsyncPutTest(result -> {
                    showInput();
                    binding.buttonBeginTest.setEnabled(true);
                }).execute(test, Action.END_TEST, null);
            });

            if (intent.hasExtra(Args.ARG_IS_LOGOUT)) {
                test = (Test) intent.getSerializableExtra(Args.ARG_TEST);
            }

            if (test == null) {
                binding.buttonBeginTest.setEnabled(true);
                binding.buttonEndTest.setEnabled(false);
            } else {
                binding.buttonBeginTest.setEnabled(false);
                binding.buttonEndTest.setEnabled(true);
            }
        } else {
            binding.buttonBeginTest.setVisibility(View.GONE);
            binding.buttonEndTest.setVisibility(View.GONE);
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
        new AsyncLogin(this).execute(this.binding.username.getText().toString().trim(), this.binding.password.getText().toString(), this);
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
        if (test != null) {
            intent.putExtra(Args.ARG_TEST, test);
        }
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