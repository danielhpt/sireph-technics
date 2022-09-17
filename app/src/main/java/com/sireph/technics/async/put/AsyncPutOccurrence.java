package com.sireph.technics.async.put;

import com.sireph.technics.models.occurrence.Occurrence;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;

public class AsyncPutOccurrence extends _AsyncPut<Occurrence> {
    public AsyncPutOccurrence(Listener<Occurrence> listener) {
        super(listener);
    }

    @Override
    public void execute(String token, int technicianId, Occurrence Occurrence) {
        executor.execute(() -> {
            try {
                Occurrence result = RestApi.putOccurrence(token, technicianId, Occurrence);
                handler.post(() -> listener.onResponse(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponse(null));
            }
        });
    }
}
