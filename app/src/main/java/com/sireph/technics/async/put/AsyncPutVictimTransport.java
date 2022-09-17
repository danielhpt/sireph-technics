package com.sireph.technics.async.put;

import com.sireph.technics.models.victim.Victim;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;

public class AsyncPutVictimTransport extends _AsyncPut<Victim> {
    public AsyncPutVictimTransport(Listener<Victim> listener) {
        super(listener);
    }

    @Override
    public void execute(String token, int ignored, Victim victim) {
        executor.execute(() -> {
            try {
                Victim result = RestApi.putTransport(token, victim);
                handler.post(() -> listener.onResponse(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponse(null));
            }
        });
    }
}
