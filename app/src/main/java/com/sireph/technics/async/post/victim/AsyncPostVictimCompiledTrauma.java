package com.sireph.technics.async.post.victim;

import android.os.Handler;
import android.os.Looper;

import com.sireph.technics.async.post._AsyncPost;
import com.sireph.technics.models.victim.symptom.CompiledTrauma;
import com.sireph.technics.models.victim.symptom.Trauma;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncPostVictimCompiledTrauma {
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final _AsyncPost.Listener<Trauma> listener;

    public AsyncPostVictimCompiledTrauma(_AsyncPost.Listener<Trauma> listener) {
        this.listener = listener;
    }

    public void execute(String token, int victimId, CompiledTrauma compiledTrauma) {
        for (Trauma t : compiledTrauma.genComponents()) {
            executor.execute(() -> {
                try {
                    Trauma result = RestApi.postTrauma(token, victimId, t);
                    handler.post(() -> listener.onResponse(result));
                } catch (JSONException | IOException e) {
                    handler.post(() -> listener.onResponse(null));
                }
            });
        }
    }
}
