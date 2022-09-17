package com.sireph.technics.async.post;

import com.sireph.technics.models.occurrence.OccurrenceState;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;

public class AsyncPostOccurrenceState extends _AsyncPost<OccurrenceState> {
    public AsyncPostOccurrenceState(Listener<OccurrenceState> listener) {
        super(listener);
    }

    @Override
    public void execute(String token, int occurrenceId, OccurrenceState object) {
        executor.execute(() -> {
            try {
                OccurrenceState result = RestApi.postOccurrenceState(token, occurrenceId, object);
                handler.post(() -> listener.onResponse(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponse(null));
            }
        });
    }
}
