package com.sireph.technics.async.post.victim;

import com.sireph.technics.async.post._AsyncPost;
import com.sireph.technics.models.victim.symptom.Symptom;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;

public class AsyncPostVictimSymptom extends _AsyncPost<Symptom> {
    public AsyncPostVictimSymptom(Listener<Symptom> listener) {
        super(listener);
    }

    @Override
    public void execute(String token, int victimId, Symptom object) {
        executor.execute(() -> {
            try {
                Symptom result = RestApi.postSymptom(token, victimId, object);
                handler.post(() -> listener.onResponse(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponse(null));
            }
        });
    }
}
