package com.sireph.technics.async.post.victim.procedure;

import com.sireph.technics.async.post._AsyncPost;
import com.sireph.technics.models.victim.procedures.ProcedureProtocol;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;

public class AsyncPostProcedureProtocol extends _AsyncPost<ProcedureProtocol> {
    public AsyncPostProcedureProtocol(Listener<ProcedureProtocol> listener) {
        super(listener);
    }

    @Override
    public void execute(String token, int victimId, ProcedureProtocol object) {
        executor.execute(() -> {
            try {
                ProcedureProtocol result = RestApi.postProcedureProtocol(token, victimId, object);
                handler.post(() -> listener.onResponse(result));
            } catch (JSONException | IOException e) {
                handler.post(() -> listener.onResponse(null));
            }
        });
    }
}
