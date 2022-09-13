package com.sireph.technics.async.post;

import android.os.Handler;
import android.os.Looper;

import com.sireph.technics.models.procedures.ProcedureProtocol;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncPostProcedureProtocol {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Listener listener;

    public AsyncPostProcedureProtocol(Listener listener) {
        this.listener = listener;
    }

    public void execute(String token, int victimId, ProcedureProtocol object) {
        executor.execute(() -> {
            try {
                ProcedureProtocol result = RestApi.postProcedureProtocol(token, victimId, object);
                handler.post(() -> listener.onResponseProcedureProtocol(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponseProcedureProtocol(null));
            }
        });
    }

    public interface Listener {
        void onResponseProcedureProtocol(ProcedureProtocol result);
    }
}
