package com.sireph.technics.async.post;

import android.os.Handler;
import android.os.Looper;

import com.sireph.technics.models.procedures.Evaluation;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncPostEvaluation {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Listener listener;

    public AsyncPostEvaluation(Listener listener) {
        this.listener = listener;
    }

    public void execute(String token, int victimId, Evaluation object) {
        executor.execute(() -> {
            try {
                Evaluation result = RestApi.postEvaluation(token, victimId, object);
                handler.post(() -> listener.onResponseEvaluation(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponseEvaluation(null));
            }
        });
    }

    public interface Listener {
        void onResponseEvaluation(Evaluation result);
    }
}
