package com.sireph.technics.async;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.sireph.technics.MainActivity;
import com.sireph.technics.R;
import com.sireph.technics.utils.RestApi;

import org.json.JSONObject;

import java.util.Objects;

public class AsyncLogin extends AsyncTask<Void, Void, String> {
    @SuppressLint("StaticFieldLeak")
    private final MainActivity mainActivity;

    public AsyncLogin(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mainActivity.getButton().setVisibility(View.GONE);
        mainActivity.getLoading().setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            JSONObject login = RestApi.login(mainActivity.getUsername().getText().toString(), mainActivity.getPassword().getText().toString());
            if (!login.getBoolean("is_technician")) {
                throw new Exception("Technician not found");
            }
            String new_token = login.getString("token");
            SharedPreferences.Editor editor = mainActivity.getSharedPref().edit();
            editor.putString(mainActivity.getString(R.string.sharedPref_key_token), new_token);
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
                mainActivity.getUsername().setText("");
                mainActivity.getPassword().setText("");
                show_button = true;
                Toast.makeText(mainActivity.getApplicationContext(), mainActivity.getString(R.string.technician_not_found), Toast.LENGTH_SHORT).show();
                break;
            case "error 2":
                mainActivity.getPassword().setText("");
                show_button = true;
                Toast.makeText(mainActivity.getApplicationContext(), mainActivity.getString(R.string.wrong_password), Toast.LENGTH_SHORT).show();
                break;
            case "error 3":
                mainActivity.getUsername().setText("");
                mainActivity.getPassword().setText("");
                show_button = true;
                break;
            default:
                mainActivity.gotoHome(new_token);
        }
        if (show_button) {
            mainActivity.getButton().setVisibility(View.VISIBLE);
            mainActivity.getLoading().setVisibility(View.GONE);
        }
    }
}
