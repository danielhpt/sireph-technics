package com.sireph.technics.async.post.victim.procedure;

import com.sireph.technics.async.post._AsyncPost;
import com.sireph.technics.models.victim.procedures.ProcedureVentilation;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;

public class AsyncPostProcedureVentilation extends _AsyncPost<ProcedureVentilation> {
    public AsyncPostProcedureVentilation(Listener<ProcedureVentilation> listener) {
        super(listener);
    }

    @Override
    public void execute(String token, int victimId, ProcedureVentilation object) {
        executor.execute(() -> {
            try {
                ProcedureVentilation result = RestApi.postProcedureVentilation(token, victimId, object);
                handler.post(() -> listener.onResponse(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponse(null));
            }
        });
    }
}
