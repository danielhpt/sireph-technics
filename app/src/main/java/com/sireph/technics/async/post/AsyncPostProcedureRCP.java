package com.sireph.technics.async.post;

import android.os.Handler;
import android.os.Looper;

import com.sireph.technics.models.procedures.ProcedureRCP;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncPostProcedureRCP {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Listener listener;

    public AsyncPostProcedureRCP(Listener listener) {
        this.listener = listener;
    }

    public void execute(String token, int victimId, ProcedureRCP object) {
        executor.execute(() -> {
            try {
                ProcedureRCP result = RestApi.postProcedureRCP(token, victimId, object);
                handler.post(() -> listener.onResponseProcedureRCP(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponseProcedureRCP(null));
            }
        });
    }

    public interface Listener {
        void onResponseProcedureRCP(ProcedureRCP result);
    }
}
