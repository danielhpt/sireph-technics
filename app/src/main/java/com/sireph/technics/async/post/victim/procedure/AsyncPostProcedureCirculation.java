package com.sireph.technics.async.post.victim.procedure;

import com.sireph.technics.async.post._AsyncPost;
import com.sireph.technics.models.victim.procedures.ProcedureCirculation;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;

public class AsyncPostProcedureCirculation extends _AsyncPost<ProcedureCirculation> {
    public AsyncPostProcedureCirculation(Listener<ProcedureCirculation> listener) {
        super(listener);
    }

    @Override
    public void execute(String token, int victimId, ProcedureCirculation object) {
        executor.execute(() -> {
            try {
                ProcedureCirculation result = RestApi.postProcedureCirculation(token, victimId, object);
                handler.post(() -> listener.onResponse(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponse(null));
            }
        });
    }
}
