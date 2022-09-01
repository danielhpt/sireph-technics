package com.sireph.technics.async;

import static com.sireph.technics.utils.RestApi.logout;

import android.os.AsyncTask;

import java.io.IOException;

public class AsyncLogout extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... strings) {
        try {
            logout(strings[0]);
        } catch (IOException ignored) {
        }
        return null;
    }
}
