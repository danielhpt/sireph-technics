package com.sireph.technics.async.post.victim;

import com.sireph.technics.async.post._AsyncPost;
import com.sireph.technics.models.victim.Victim;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;

public class AsyncPostVictim extends _AsyncPost<Victim> {
    public AsyncPostVictim(Listener<Victim> listener) {
        super(listener);
    }

    @Override
    public void execute(String token, int occurrenceId, Victim object) {
        executor.execute(() -> {
            try {
                Victim result = RestApi.postVictim(token, occurrenceId, object);
                handler.post(() -> listener.onResponse(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponse(null));
            }
        });
    }
}
