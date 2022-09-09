package com.sireph.technics.async;

import static com.sireph.technics.utils.RestApi.getActiveOccurrence;

import android.os.AsyncTask;

import com.sireph.technics.models.Occurrence;
import com.sireph.technics.models.Team;
import com.sireph.technics.models.Technician;

import org.json.JSONException;

import java.io.IOException;

public class AsyncGetActiveOccurrence extends AsyncTask<Void, Void, Occurrence> {
    private final Technician technician;
    private final Team team;
    private final String token;
    AsyncGetActiveOccurrenceListener listener;

    @SuppressWarnings("deprecation")
    public AsyncGetActiveOccurrence(Technician technician, Team team, String token, AsyncGetActiveOccurrenceListener listener) {
        this.token = token;
        this.listener = listener;
        this.technician = technician;
        this.team = team;
    }

    @Override
    protected Occurrence doInBackground(Void... voids) {
        try {
            return getActiveOccurrence(token, technician, team);
        } catch (IOException | JSONException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Occurrence occurrence) {
        super.onPostExecute(occurrence);
        listener.onResponseActiveOccurrence(occurrence);
    }

    public interface AsyncGetActiveOccurrenceListener {
        void onResponseActiveOccurrence(Occurrence occurrence);
    }
}
