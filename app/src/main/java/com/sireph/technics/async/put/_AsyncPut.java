package com.sireph.technics.async.put;

import com.sireph.technics.async._Async;

public abstract class _AsyncPut<T> extends _Async {
    protected Listener<T> listener;

    public _AsyncPut(Listener<T> listener) {
        super(listener);
        //noinspection unchecked
        this.listener = (Listener<T>) super.listener;
    }

    public abstract void execute(String token, int id, T t);

    public interface Listener<T> extends _Async.Listener {
        void onResponse(T t);
    }
}
