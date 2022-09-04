package com.sireph.technics.async;

import static com.sireph.technics.utils.RestApi.getTeamOccurrences;

import android.os.AsyncTask;

import com.sireph.technics.models.Occurrence;
import com.sireph.technics.models.Team;
import com.sireph.technics.models.Technician;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class AsyncGetTeamOccurrences extends AsyncTask<String, Void, List<Occurrence>> {
    private final Technician technician;
    private final Team team;
    public AsyncResponse delegate;

    @SuppressWarnings("deprecation")
    public AsyncGetTeamOccurrences(Technician technician, Team team, AsyncResponse delegate) {
        this.delegate = delegate;
        this.technician = technician;
        this.team = team;
    }

    @Override
    protected List<Occurrence> doInBackground(String... strings) {
        String token = strings[0];
        try {
            return getTeamOccurrences(token, technician, team);
        } catch (IOException | JSONException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Occurrence> occurrences) {
        super.onPostExecute(occurrences);
        delegate.processFinish(occurrences);
    }
}
