package com.sireph.technics.async.post.victim.procedure;

import com.sireph.technics.async.post._AsyncPost;
import com.sireph.technics.models.victim.procedures.ProcedureRCP;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;

public class AsyncPostProcedureRCP extends _AsyncPost<ProcedureRCP> {
    public AsyncPostProcedureRCP(Listener<ProcedureRCP> listener) {
        super(listener);
    }

    @Override
    public void execute(String token, int victimId, ProcedureRCP object) {
        executor.execute(() -> {
            try {
                ProcedureRCP result = RestApi.postProcedureRCP(token, victimId, object);
                handler.post(() -> listener.onResponse(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponse(null));
            }
        });
    }
}
