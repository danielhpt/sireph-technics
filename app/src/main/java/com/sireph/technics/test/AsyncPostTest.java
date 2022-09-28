package com.sireph.technics.test;

import androidx.annotation.Nullable;

import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncPostTest extends _AsyncTest {
    public AsyncPostTest(Listener listener) {
        super(listener);
    }

    public void execute(Test test, @Nullable Action action, @Nullable Integer occurrenceId) {
        executor.execute(() -> {
            try {
                URL api = new URL(server_address);

                HttpURLConnection connection = getConnection(test, action, occurrenceId, api, "POST");

                int code = connection.getResponseCode();

                if (code == HttpURLConnection.HTTP_CREATED) {
                    test.id = RestApi.readResponse(connection).optInt("test_id", -1);
                }

                handler.post(() -> listener.onResponse(test));
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
    }
}
