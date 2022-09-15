package com.sireph.technics.async.post;

import android.os.Handler;
import android.os.Looper;

import com.sireph.technics.models.OccurrenceState;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncPostOccurrenceState {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Listener listener;

    public AsyncPostOccurrenceState(Listener listener) {
        this.listener = listener;
    }

    public void execute(String token, int occurrenceId, OccurrenceState object) {
        executor.execute(() -> {
            try {
                OccurrenceState result = RestApi.postOccurrenceState(token, occurrenceId, object);
                handler.post(() -> listener.onResponseOccurrenceState(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponseOccurrenceState(null));
            }
        });
    }

    public interface Listener {
        void onResponseOccurrenceState(OccurrenceState state);
    }
}
