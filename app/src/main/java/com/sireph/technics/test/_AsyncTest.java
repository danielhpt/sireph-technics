package com.sireph.technics.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sireph.technics.BuildConfig;
import com.sireph.technics.async._Async;

import org.json.JSONException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public abstract class _AsyncTest extends _Async {
    //protected static final String server_address = "http://192.168.1.65:8000/tests/";
    protected static final String server_address = BuildConfig.API_SERVER + "tests/";
    protected final _AsyncTest.Listener listener;

    public _AsyncTest(_Async.Listener listener) {
        super(listener);
        this.listener = (_AsyncTest.Listener) super.listener;
    }

    @NonNull
    protected static HttpURLConnection getConnection(Test test, @Nullable Action action, @Nullable Integer occurrenceId, URL api, String method)
            throws IOException, JSONException {
        HttpURLConnection connection;
        connection = (HttpURLConnection) api.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        OutputStream os = connection.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
        osw.write(test.toJson(action, occurrenceId).toString());
        osw.flush();
        osw.close();
        os.close();

        connection.connect();
        return connection;
    }

    public interface Listener extends _Async.Listener {
        void onResponse(Object result);
    }
}
