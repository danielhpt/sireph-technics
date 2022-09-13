package com.sireph.technics.async.post;

import android.os.Handler;
import android.os.Looper;

import com.sireph.technics.models.Team;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncPutTeam {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Listener listener;

    public AsyncPutTeam(Listener listener) {
        this.listener = listener;
    }

    public void execute(String token, Team Team) {
        executor.execute(() -> {
            try {
                Team result = RestApi.putTeam(token , Team);
                handler.post(() -> listener.onResponseTeam(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponseTeam(null));
            }
        });
    }

    public interface Listener {
        void onResponseTeam(Team Team);
    }
}
