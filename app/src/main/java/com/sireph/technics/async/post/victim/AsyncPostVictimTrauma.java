package com.sireph.technics.async.post.victim;

import com.sireph.technics.async.post._AsyncPost;
import com.sireph.technics.models.victim.symptom.Trauma;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;

public class AsyncPostVictimTrauma extends _AsyncPost<Trauma> {
    public AsyncPostVictimTrauma(Listener<Trauma> listener) {
        super(listener);
    }

    @Override
    public void execute(String token, int victimId, Trauma object) {
        executor.execute(() -> {
            try {
                Trauma result = RestApi.postTrauma(token, victimId, object);
                handler.post(() -> listener.onResponse(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponse(null));
            }
        });
    }
}
