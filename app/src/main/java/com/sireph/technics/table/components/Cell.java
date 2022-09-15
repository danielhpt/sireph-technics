package com.sireph.technics.table.components;

import androidx.annotation.Nullable;

public class Cell {
    @Nullable
    private final String data;

    public Cell(@Nullable String data) {
        this.data = data;
    }

    @Nullable
    public String getData() {
        return data;
    }
}