package com.sireph.technics.async;

import android.content.Context;
import android.content.SharedPreferences;

import com.sireph.technics.R;
import com.sireph.technics.utils.RestApi;

import java.io.IOException;

public class AsyncLogout extends _Async {
    protected Listener listener = (Listener) super.listener;

    public AsyncLogout(Listener listener) {
        super(listener);
    }

    public void execute(String token, Context context) {
        executor.execute(() -> {
            try {
                RestApi.logout(token);
            } catch (IOException ignored) {
            }
            SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit();
            editor.remove(context.getString(R.string.sharedPref_key_username));
            editor.remove(context.getString(R.string.sharedPref_key_token));
            editor.apply();
            handler.post(listener::onResponseLogout);
        });
    }

    public interface Listener extends _Async.Listener {
        void onResponseLogout();
    }
}
