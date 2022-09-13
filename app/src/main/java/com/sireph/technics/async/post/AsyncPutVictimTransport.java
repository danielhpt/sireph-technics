package com.sireph.technics.async.post;

import android.os.Handler;
import android.os.Looper;

import com.sireph.technics.models.Victim;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncPutVictimTransport {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Listener listener;

    public AsyncPutVictimTransport(Listener listener) {
        this.listener = listener;
    }

    public void execute(String token, Victim victim) {
        executor.execute(() -> {
            try {
                Victim result = RestApi.putTransport(token, victim);
                handler.post(() -> listener.onResponseVictimTransport(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponseVictimTransport(null));
            }
        });
    }

    public interface Listener {
        void onResponseVictimTransport(Victim victim);
    }
}
