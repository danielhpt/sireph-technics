package com.sireph.technics.async.post.victim.procedure;

import com.sireph.technics.async.post._AsyncPost;
import com.sireph.technics.models.victim.procedures.ProcedureScale;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;

public class AsyncPostProcedureScale extends _AsyncPost<ProcedureScale> {
    public AsyncPostProcedureScale(Listener<ProcedureScale> listener) {
        super(listener);
    }

    @Override
    public void execute(String token, int victimId, ProcedureScale object) {
        executor.execute(() -> {
            try {
                ProcedureScale result = RestApi.postProcedureScale(token, victimId, object);
                handler.post(() -> listener.onResponse(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponse(null));
            }
        });
    }
}
