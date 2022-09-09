package com.sireph.technics.async;

import static com.sireph.technics.utils.RestApi.getHospitals;
import static com.sireph.technics.utils.RestApi.getTechnicians;

import android.os.AsyncTask;

import com.sireph.technics.models.Central;
import com.sireph.technics.models.Hospital;
import com.sireph.technics.models.Technician;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class AsyncGetHospitals extends AsyncTask<Void, Void, List<Hospital>> {
    private final String token;
    AsyncGetHospitalsListener listener;

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
