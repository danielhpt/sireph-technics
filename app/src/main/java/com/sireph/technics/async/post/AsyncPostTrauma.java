package com.sireph.technics.async.post;

import android.os.Handler;
import android.os.Looper;

import com.sireph.technics.models.procedures.Trauma;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncPostTrauma {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Listener listener;

    public AsyncPostTrauma(Listener listener) {
        this.listener = listener;
    }

    public void execute(String token, int victimId, Trauma object) {
        executor.execute(() -> {
            try {
                Trauma result = RestApi.postTrauma(token, victimId, object);
                handler.post(() -> listener.onResponseTrauma(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponseTrauma(null));
            }
        });
    }

    public interface Listener {
        void onResponseTrauma(Trauma trauma);
    }
}
