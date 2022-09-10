package com.sireph.technics.async;

import static com.sireph.technics.utils.RestApi.getHospitals;

import android.os.AsyncTask;

import com.sireph.technics.models.Hospital;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class AsyncGetHospitals extends AsyncTask<Void, Void, List<Hospital>> {
    private final String token;
    AsyncGetHospitalsListener listener;

    @SuppressWarnings("deprecation")
    public AsyncGetHospitals(String token, AsyncGetHospitalsListener listener) {
        this.token = token;
        this.listener = listener;
    }

    @Override
    protected List<Hospital> doInBackground(Void... voids) {
        try {
            return getHospitals(token);
        } catch (JSONException | IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Hospital> hospitals) {
        super.onPostExecute(hospitals);
        listener.onResponseHospitals(hospitals);
    }

    public interface AsyncGetHospitalsListener {
        void onResponseHospitals(List<Hospital> hospitals);
    }
}
