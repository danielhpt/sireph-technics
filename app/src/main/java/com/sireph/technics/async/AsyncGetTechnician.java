package com.sireph.technics.async;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import com.sireph.technics.R;
import com.sireph.technics.models.Technician;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncGetTechnician {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Listener listener;

    public AsyncGetTechnician(Listener listener) {
        this.listener = listener;
    }

    public void execute(String token, Context context) {
        executor.execute(() -> {
            try {
                Technician technician = RestApi.getTechnician(token);
                handler.post(() -> listener.onResponseTechnician(technician));
                SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit();
                editor.putString(context.getString(R.string.sharedPref_key_username), technician.getUser().getFullName());
                editor.apply();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public interface Listener {
        void onResponseTechnician(Technician technician);
    }
}
