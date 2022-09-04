package com.sireph.technics.async;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.sireph.technics.LoginActivity;
import com.sireph.technics.R;
import com.sireph.technics.utils.RestApi;

import org.json.JSONObject;

import java.util.Objects;

public class AsyncLogin extends AsyncTask<Void, Void, String> {
    @SuppressLint("StaticFieldLeak")
    private final LoginActivity loginActivity;

    @SuppressWarnings("deprecation")
    public AsyncLogin(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loginActivity.getButton().setVisibility(View.GONE);
        loginActivity.getLoading().setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            JSONObject login = RestApi.login(loginActivity.getUsername().getText().toString(), loginActivity.getPassword().getText().toString());
            if (!login.getBoolean("is_technician")) {
                throw new Exception("Technician not found");
            }
            String new_token = login.getString("token");
            SharedPreferences.Editor editor = loginActivity.getSharedPref().edit();
            editor.putString(loginActivity.getString(R.string.sharedPref_key_token), new_token);
            editor.apply();
            return new_token;
        } catch (Exception e) {
            switch (Objects.requireNonNull(e.getMessage())) {
                case "Technician not found":
                    return "error 1";
                case "Password incorrect":
                    return "error 2";
                default:
                    return e.getMessage();
                //return "error 3";
            }
        }
    }

    @Override
    protected void onPostExecute(String new_token) {
        super.onPostExecute(new_token);
        boolean show_button = false;
        switch (new_token) {
            case "error 1":
                loginActivity.getUsername().setText("");
                loginActivity.getPassword().setText("");
                show_button = true;
                Toast.makeText(loginActivity.getApplicationContext(), loginActivity.getString(R.string.technician_not_found), Toast.LENGTH_SHORT).show();
                break;
            case "error 2":
                loginActivity.getPassword().setText("");
                show_button = true;
                Toast.makeText(loginActivity.getApplicationContext(), loginActivity.getString(R.string.wrong_password), Toast.LENGTH_SHORT).show();
                break;
            case "error 3":
                loginActivity.getUsername().setText("");
                loginActivity.getPassword().setText("");
                show_button = true;
                break;
            default:
                loginActivity.gotoHome(new_token);
        }
        if (show_button) {
            loginActivity.getButton().setVisibility(View.VISIBLE);
            loginActivity.getLoading().setVisibility(View.GONE);
        }
    }
}
