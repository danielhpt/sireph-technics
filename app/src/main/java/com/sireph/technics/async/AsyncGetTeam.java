package com.sireph.technics.async;

import static com.sireph.technics.utils.RestApi.getTechnicianTeam;

import android.os.AsyncTask;

import com.sireph.technics.models.Team;
import com.sireph.technics.models.Technician;

import org.json.JSONException;

import java.io.IOException;

public class AsyncGetTeam extends AsyncTask<String, Void, Team> {
    private final Technician technician;
    public AsyncResponse delegate;

    @SuppressWarnings("deprecation")
    public AsyncGetTeam(Technician technician, AsyncResponse delegate) {
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
