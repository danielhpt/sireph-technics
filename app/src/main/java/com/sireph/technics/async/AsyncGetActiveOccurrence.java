package com.sireph.technics.async;

import static com.sireph.technics.utils.RestApi.getActiveOccurrence;

import android.os.AsyncTask;

import com.sireph.technics.models.Occurrence;
import com.sireph.technics.models.Team;
import com.sireph.technics.models.Technician;

import org.json.JSONException;

import java.io.IOException;

public class AsyncGetActiveOccurrence extends AsyncTask<String, Void, Occurrence> {
    private final Technician technician;
    private final Team team;
    public AsyncResponse delegate;

    @SuppressWarnings("deprecation")
    public AsyncGetActiveOccurrence(Technician technician, Team team, AsyncResponse delegate) {
        this.delegate = delegate;
        this.technician = technician;
        this.team = team;
    }

    @Override
    protected Occurrence doInBackground(String... strings) {
        String token = strings[0];
        try {
            return getActiveOccurrence(token, technician, team);
        } catch (IOException | JSONException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Occurrence occurrence) {
        super.onPostExecute(occurrence);
        delegate.processFinish(occurrence);
    }
}
