package com.sireph.technics.async.put;

import com.sireph.technics.models.Team;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;

public class AsyncPutTeam extends _AsyncPut<Team> {
    public AsyncPutTeam(Listener<Team> listener) {
        super(listener);
    }

    @Override
    public void execute(String token, int ignored, Team Team) {
        executor.execute(() -> {
            try {
                Team result = RestApi.putTeam(token, Team);
                handler.post(() -> listener.onResponse(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponse(null));
            }
        });
    }
}
