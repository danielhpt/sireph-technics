package com.sireph.technics.async;

import static com.sireph.technics.utils.RestApi.getTeam;

import android.os.AsyncTask;

import com.sireph.technics.models.Team;
import com.sireph.technics.models.Technician;

import org.json.JSONException;

import java.io.IOException;

public class AsyncGetTeam extends AsyncTask<Void, Void, Team> {
    private final Technician technician;
    private final String token;
    AsyncGetTeamListener listener;

    @SuppressWarnings("deprecation")
    public AsyncGetTeam(Technician technician, String token, AsyncGetTeamListener listener) {
        this.token = token;
        this.listener = listener;
        this.technician = technician;
    }

    @Override
    protected Team doInBackground(Void... voids) {
        try {
            return getTeam(token, technician);
        } catch (IOException | JSONException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Team team) {
        super.onPostExecute(team);
        listener.onResponseTeam(team);
    }

    public interface AsyncGetTeamListener {
        void onResponseTeam(Team team);
    }
}
