package com.sireph.technics.models.async;

import static com.sireph.technics.utils.RestApi.getTechnicianTeam;

import android.os.AsyncTask;

import com.sireph.technics.models.Team;
import com.sireph.technics.models.Technician;

import org.json.JSONException;

import java.io.IOException;

public class AsyncGetTeam extends AsyncTask<String, Void, Team> {
    public AsyncResponse delegate;
    private final Technician technician;

    public AsyncGetTeam(AsyncResponse delegate, Technician technician) {
        this.delegate = delegate;
        this.technician = technician;
    }

    @Override
    protected Team doInBackground(String... strings) {
        String token = strings[0];
        try {
            return getTechnicianTeam(token, technician);
        } catch (IOException | JSONException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Team team) {
        super.onPostExecute(team);
        delegate.processFinish(team);
    }
}
