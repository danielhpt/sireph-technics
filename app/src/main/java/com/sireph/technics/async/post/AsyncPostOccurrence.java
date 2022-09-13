package com.sireph.technics.async.post;

import android.os.Handler;
import android.os.Looper;

import com.sireph.technics.models.Occurrence;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncPostOccurrence {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Listener listener;

    public AsyncPostOccurrence(Listener listener) {
        this.listener = listener;
    }

    public void execute(String token, Occurrence object) {
        executor.execute(() -> {
            try {
                Occurrence result = RestApi.postOccurrence(token, object);
                handler.post(() -> listener.onResponseOccurrence(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponseOccurrence(null));
            }
        });
    }

    public interface Listener {
        void onResponseOccurrence(Occurrence result);
    }
}
