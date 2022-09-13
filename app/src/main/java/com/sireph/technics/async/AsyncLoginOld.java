package com.sireph.technics.async;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.sireph.technics.R;
import com.sireph.technics.utils.RestApi;

import org.json.JSONObject;

import java.util.Objects;

public class AsyncLoginOld extends AsyncTask<Void, Void, String> {
    private final String username;
    private final String password;
    @SuppressLint("StaticFieldLeak")
    private final Context context;
    AsyncLoginListener listener;

    @SuppressWarnings("deprecation")
    public AsyncLoginOld(String username, String password, Context context) {
        this.username = username;
        this.password = password;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            JSONObject login = RestApi.login(this.username, this.password);
            if (!login.getBoolean("is_technician")) {
                throw new Exception("Technician not found");
            }
            String newToken = login.getString("token");
            SharedPreferences.Editor editor = this.context.getSharedPreferences(this.context.getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit();
            editor.putString(this.context.getString(R.string.sharedPref_key_token), newToken);
            editor.apply();
            return newToken;
        } catch (Exception e) {
            switch (Objects.requireNonNull(e.getMessage())) {
                case "Technician not found":
                    return "error 1";
                case "Password incorrect":
                    return "error 2";
                default:
                    return "error 3";
            }
        }
    }

    @Override
    protected void onPostExecute(String newToken) {
        super.onPostExecute(newToken);
        switch (newToken) {
            case "error 1":
                this.listener.onResponseLoginNotFound();
                break;
            case "error 2":
                this.listener.onResponseLoginWrongPassword();
                break;
            case "error 3":
                this.listener.onResponseLoginError();
                break;
            default:
                this.listener.onResponseLoginOk(newToken);
        }
    }

    public interface AsyncLoginListener {
        void onResponseLoginOk(String newToken);

        void onResponseLoginNotFound();

        void onResponseLoginWrongPassword();

        void onResponseLoginError();
    }
}
