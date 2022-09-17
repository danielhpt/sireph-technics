package com.sireph.technics.async;

import android.content.Context;
import android.content.SharedPreferences;

import com.sireph.technics.R;
import com.sireph.technics.utils.RestApi;

import org.json.JSONObject;

import java.util.Objects;

public class AsyncLogin extends _Async {
    protected Listener listener = (Listener) super.listener;

    public AsyncLogin(Listener listener) {
        super(listener);
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
                        handler.post(listener::onResponseLoginNotFound);
                        break;
                    case "Password incorrect":
                        handler.post(listener::onResponseLoginWrongPassword);
                        break;
                    default:
                        handler.post(listener::onResponseLoginError);
                }
                return;
            }
            SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit();
            editor.putString(context.getString(R.string.sharedPref_key_token), token);
            editor.apply();
        });
    }

    public interface Listener extends _Async.Listener {
        void onResponseLoginOk(String newToken);

        void onResponseLoginNotFound();

        void onResponseLoginWrongPassword();

        void onResponseLoginError();
    }
}
