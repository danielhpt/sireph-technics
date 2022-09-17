package com.sireph.technics.async.post;

import com.sireph.technics.models.occurrence.Occurrence;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;

public class AsyncPostOccurrence extends _AsyncPost<Occurrence> {
    public AsyncPostOccurrence(Listener<Occurrence> listener) {
        super(listener);
    }

    @Override
    public void execute(String token, int ignored, Occurrence object) {
        executor.execute(() -> {
            try {
                Occurrence result = RestApi.postOccurrence(token, object);
                handler.post(() -> listener.onResponse(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponse(null));
            }
        });
    }
}
