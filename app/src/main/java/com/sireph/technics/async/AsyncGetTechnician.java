package com.sireph.technics.async;

import static com.sireph.technics.utils.RestApi.getTechnician;

import android.os.AsyncTask;

import com.sireph.technics.models.Technician;

import org.json.JSONException;

import java.io.IOException;

public class AsyncGetTechnician extends AsyncTask<Void, Void, Technician> {
    private final String token;
    AsyncGetTechnicianListener listener;

    @SuppressWarnings("deprecation")
    public AsyncGetTechnician(String token, AsyncGetTechnicianListener listener) {
        this.token = token;
        this.listener = listener;
    }

    @Override
    protected Technician doInBackground(Void... voids) {
        try {
            return getTechnician(token);
        } catch (IOException | JSONException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Technician technician) {
        super.onPostExecute(technician);
        listener.onResponseTechnician(technician);
    }

    public interface AsyncGetTechnicianListener {
        void onResponseTechnician(Technician technician);
    }
}
