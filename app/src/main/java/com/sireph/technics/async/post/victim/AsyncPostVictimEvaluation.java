package com.sireph.technics.async.post.victim;

import com.sireph.technics.async.post._AsyncPost;
import com.sireph.technics.models.victim.evaluation.Evaluation;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;

public class AsyncPostVictimEvaluation extends _AsyncPost<Evaluation> {
    public AsyncPostVictimEvaluation(Listener<Evaluation> listener) {
        super(listener);
    }

    @Override
    public void execute(String token, int victimId, Evaluation object) {
        executor.execute(() -> {
            try {
                Evaluation result = RestApi.postEvaluation(token, victimId, object);
                handler.post(() -> listener.onResponse(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponse(null));
            }
        });
    }
}
