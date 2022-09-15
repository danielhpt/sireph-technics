package com.sireph.technics.async.post;

import android.os.Handler;
import android.os.Looper;

import com.sireph.technics.models.procedures.Symptom;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncPostSymptom {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Listener listener;

    public AsyncPostSymptom(Listener listener) {
        this.listener = listener;
    }

    public void execute(String token, int victimId, Symptom object) {
        executor.execute(() -> {
            try {
                Symptom result = RestApi.postSymptom(token, victimId, object);
                handler.post(() -> listener.onResponseSymptom(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponseSymptom(null));
            }
        });
    }

    public interface Listener {
        void onResponseSymptom(Symptom result);
    }
}
