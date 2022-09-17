package com.sireph.technics.async.post.victim;

import com.sireph.technics.async.post._AsyncPost;
import com.sireph.technics.models.victim.Pharmacy;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;

public class AsyncPostVictimPharmacy extends _AsyncPost<Pharmacy> {
    public AsyncPostVictimPharmacy(Listener<Pharmacy> listener) {
        super(listener);
    }

    @Override
    public void execute(String token, int victimId, Pharmacy object) {
        executor.execute(() -> {
            try {
                Pharmacy result = RestApi.postPharmacy(token, victimId, object);
                handler.post(() -> listener.onResponse(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponse(null));
            }
        });
    }
}
