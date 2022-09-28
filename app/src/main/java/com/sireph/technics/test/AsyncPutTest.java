package com.sireph.technics.test;

import androidx.annotation.Nullable;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncPutTest extends _AsyncTest {
    public AsyncPutTest(Listener listener) {
        super(listener);
    }

    public void execute(Test test, @Nullable Action action, @Nullable Integer occurrenceId) {
        executor.execute(() -> {
            try {
                URL api = new URL(server_address + test.id + "/");

                HttpURLConnection connection = getConnection(test, action, occurrenceId, api, "PUT");

                int code = connection.getResponseCode();

                handler.post(() -> listener.onResponse(code == HttpURLConnection.HTTP_OK));
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
    }
}
