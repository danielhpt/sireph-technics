package com.sireph.technics.async.post;

import com.sireph.technics.models.Team;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;

public class AsyncPostTeam extends _AsyncPost<Team> {
    public AsyncPostTeam(Listener<Team> listener) {
        super(listener);
    }

    @Override
    public void execute(String token, int ignored, Team object) {
        executor.execute(() -> {
            try {
                Team result = RestApi.postTeam(token, object);
                handler.post(() -> listener.onResponse(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponse(null));
            }
        });
    }
}
