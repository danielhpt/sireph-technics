package com.sireph.technics.async.post;

import android.os.Handler;
import android.os.Looper;

import com.sireph.technics.models.procedures.ProcedureVentilation;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncPostProcedureVentilation {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Listener listener;

    public AsyncPostProcedureVentilation(Listener listener) {
        this.listener = listener;
    }

    public void execute(String token, int victimId, ProcedureVentilation object) {
        executor.execute(() -> {
            try {
                ProcedureVentilation result = RestApi.postProcedureVentilation(token, victimId, object);
                handler.post(() -> listener.onResponseProcedureVentilation(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponseProcedureVentilation(null));
            }
        });
    }

    public interface Listener {
        void onResponseProcedureVentilation(ProcedureVentilation result);
    }
}
