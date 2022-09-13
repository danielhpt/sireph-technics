package com.sireph.technics.async.post;

import android.os.Handler;
import android.os.Looper;

import com.sireph.technics.models.procedures.ProcedureScale;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncPostProcedureScale {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Listener listener;

    public AsyncPostProcedureScale(Listener listener) {
        this.listener = listener;
    }

    public void execute(String token, int victimId, ProcedureScale object) {
        executor.execute(() -> {
            try {
                ProcedureScale result = RestApi.postProcedureScale(token, victimId, object);
                handler.post(() -> listener.onResponseProcedureScale(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponseProcedureScale(null));
            }
        });
    }

    public interface Listener {
        void onResponseProcedureScale(ProcedureScale result);
    }
}
