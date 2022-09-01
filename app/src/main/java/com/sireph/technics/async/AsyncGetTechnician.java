package com.sireph.technics.async;

import static com.sireph.technics.utils.RestApi.getTechnician;

import android.os.AsyncTask;

import com.sireph.technics.models.Technician;

import org.json.JSONException;

import java.io.IOException;

public class AsyncGetTechnician extends AsyncTask<String, Void, Technician> {
    public AsyncResponse delegate;

    public AsyncGetTechnician(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected Technician doInBackground(String... strings) {
        String token = strings[0];
        try {
            return getTechnician(token);
        } catch (IOException | JSONException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Technician technician) {
        super.onPostExecute(technician);
        delegate.processFinish(technician);
    }
}
