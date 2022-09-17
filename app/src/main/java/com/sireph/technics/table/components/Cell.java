package com.sireph.technics.table.components;

import androidx.annotation.NonNull;

public class Cell {
    @NonNull
    private final String data;

    public Cell(@NonNull String data) {
        this.data = data;
    }

    @NonNull
    public String getData() {
        return data;
    }
}