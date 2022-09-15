package com.sireph.technics.async;

import static com.sireph.technics.utils.RestApi.getTechnicianOccurrences;

import android.os.AsyncTask;

import com.sireph.technics.models.Occurrence;
import com.sireph.technics.models.Team;
import com.sireph.technics.models.Technician;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class AsyncGetTechnicianOccurrences extends AsyncTask<Void, Void, List<Occurrence>> {
    private final Technician technician;
    private final Team team;
    private final Occurrence activeOccurrence;
    private final String token;
    AsyncGetTechnicianOccurrencesListener listener;

    @SuppressWarnings("deprecation")
    public AsyncGetTechnicianOccurrences(Technician technician, Team team, Occurrence activeOccurrence, String token, AsyncGetTechnicianOccurrencesListener listener) {
        this.token = token;
        this.listener = listener;
        this.technician = technician;
        this.team = team;
        this.activeOccurrence = activeOccurrence;
    }

    @Override
    protected List<Occurrence> doInBackground(Void... voids) {
        try {
            return getTechnicianOccurrences(token, this.technician, this.team, this.activeOccurrence);
        } catch (IOException | JSONException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Occurrence> occurrences) {
        super.onPostExecute(occurrences);
        listener.onResponseTechnicianOccurrences(occurrences);
    }

    public interface AsyncGetTechnicianOccurrencesListener {
        void onResponseTechnicianOccurrences(List<Occurrence> occurrences);
    }
}
