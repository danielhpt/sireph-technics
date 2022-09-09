package com.sireph.technics.async;

import static com.sireph.technics.utils.RestApi.getTechnicians;

import android.os.AsyncTask;

import com.sireph.technics.models.Central;
import com.sireph.technics.models.Technician;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class AsyncGetTechnicians extends AsyncTask<Void, Void, List<Technician>> {
    private final Technician technician;
    private final Central central;
    private final String token;
    AsyncGetTechniciansListener listener;

    public AsyncGetTechnicians(Technician technician, Central central, String token, AsyncGetTechniciansListener listener) {
        this.technician = technician;
        this.central = central;
        this.token = token;
        this.listener = listener;
    }

    @Override
    protected List<Technician> doInBackground(Void... voids) {
        try {
            return getTechnicians(token, technician, central);
        } catch (JSONException | IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Technician> technicians) {
        super.onPostExecute(technicians);
        listener.onResponseTechnicians(technicians);
    }

    public interface AsyncGetTechniciansListener {
        void onResponseTechnicians(List<Technician> technicians);
    }
}
