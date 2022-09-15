package com.sireph.technics.async.post;

import android.os.Handler;
import android.os.Looper;

import com.sireph.technics.models.Occurrence;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncPutOccurrence {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Listener listener;

    public AsyncPutOccurrence(Listener listener) {
        this.listener = listener;
    }

    public void execute(String token, int technicianId, Occurrence Occurrence) {
        executor.execute(() -> {
            try {
                Occurrence result = RestApi.putOccurrence(token, technicianId, Occurrence);
                handler.post(() -> listener.onResponseOccurrence(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponseOccurrence(null));
            }
        });
    }

    public interface Listener {
        void onResponseOccurrence(Occurrence Occurrence);
    }
}
