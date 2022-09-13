package com.sireph.technics.async;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import com.sireph.technics.R;
import com.sireph.technics.models.procedures.Evaluation;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncLogin {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Listener listener;

    public AsyncLogin(Listener listener) {
        this.listener = listener;
    }

    public void execute(String username, String password, Context context) {
        executor.execute(() -> {
            String token;
            try {
                JSONObject result = RestApi.login(username, password);
                if (!result.getBoolean("is_technician")) {
                    throw new Exception("Technician not found");
                }
                token = result.getString("token");
                handler.post(() -> listener.onResponseLoginOk(token));
            } catch (Exception e) {
                switch (Objects.requireNonNull(e.getMessage())) {
                    case "Technician not found":
                        handler.post(this.listener::onResponseLoginNotFound);
                        break;
                    case "Password incorrect":
                        handler.post(this.listener::onResponseLoginWrongPassword);
                        break;
                    default:
                        handler.post(this.listener::onResponseLoginError);
                }
                return;
            }
            SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit();
            editor.putString(context.getString(R.string.sharedPref_key_token), token);
            editor.apply();
        });
    }

    public interface Listener {
        void onResponseLoginOk(String newToken);

        void onResponseLoginNotFound();

        void onResponseLoginWrongPassword();

        void onResponseLoginError();
    }
}
