package com.sireph.technics.async;

import android.content.Context;
import android.content.SharedPreferences;

import com.sireph.technics.R;
import com.sireph.technics.models.Technician;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;

public class AsyncGetTechnician extends _Async {
    protected Listener listener = (Listener) super.listener;

    public AsyncGetTechnician(Listener listener) {
        super(listener);
    }

    public void execute(String token, Context context) {
        executor.execute(() -> {
            try {
                Technician technician = RestApi.getTechnician(token);
                handler.post(() -> listener.onResponseTechnicianOk(technician));
                SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit();
                editor.putString(context.getString(R.string.sharedPref_key_username), technician.getUser().getFullName());
                editor.apply();
            } catch (IOException | JSONException e) {
                handler.post(listener::onResponseTechnicianError);
            }
        });
    }

    public interface Listener extends _Async.Listener {
        void onResponseTechnicianOk(Technician technician);

        void onResponseTechnicianError();
    }
}
