package com.sireph.technics.async;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class _Async {
    protected final Executor executor = Executors.newSingleThreadExecutor();
    protected final Handler handler = new Handler(Looper.getMainLooper());
    protected final Listener listener;

    public _Async(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
    }
}
