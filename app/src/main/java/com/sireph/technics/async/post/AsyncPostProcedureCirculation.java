package com.sireph.technics.async.post;

import android.os.Handler;
import android.os.Looper;

import com.sireph.technics.models.procedures.ProcedureCirculation;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncPostProcedureCirculation {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Listener listener;

    public AsyncPostProcedureCirculation(Listener listener) {
        this.listener = listener;
    }

    public void execute(String token, int victimId, ProcedureCirculation object) {
        executor.execute(() -> {
            try {
                ProcedureCirculation result = RestApi.postProcedureCirculation(token, victimId, object);
                handler.post(() -> listener.onResponseProcedureCirculation(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponseProcedureCirculation(null));
            }
        });
    }

    public interface Listener {
        void onResponseProcedureCirculation(ProcedureCirculation result);
    }
}
