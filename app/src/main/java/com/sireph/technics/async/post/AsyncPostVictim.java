package com.sireph.technics.async.post;

import android.os.Handler;
import android.os.Looper;

import com.sireph.technics.models.Victim;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncPostVictim {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Listener listener;

    public AsyncPostVictim(Listener listener) {
        this.listener = listener;
    }

    public void execute(String token, int occurrenceId, Victim object) {
        executor.execute(() -> {
            try {
                Victim result = RestApi.postVictim(token, occurrenceId, object);
                handler.post(() -> listener.onResponseVictim(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponseVictim(null));
            }
        });
    }

    public interface Listener {
        void onResponseVictim(Victim result);
    }
}
