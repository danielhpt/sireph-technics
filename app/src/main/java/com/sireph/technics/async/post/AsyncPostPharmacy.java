package com.sireph.technics.async.post;

import android.os.Handler;
import android.os.Looper;

import com.sireph.technics.models.procedures.Pharmacy;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncPostPharmacy {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Listener listener;

    public AsyncPostPharmacy(Listener listener) {
        this.listener = listener;
    }

    public void execute(String token, int victimId, Pharmacy object) {
        executor.execute(() -> {
            try {
                Pharmacy result = RestApi.postPharmacy(token, victimId, object);
                handler.post(() -> listener.onResponsePharmacy(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponsePharmacy(null));
            }
        });
    }

    public interface Listener {
        void onResponsePharmacy(Pharmacy result);
    }
}
