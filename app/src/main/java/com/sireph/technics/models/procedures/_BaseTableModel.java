package com.sireph.technics.models.procedures;

import androidx.annotation.NonNull;

import com.sireph.technics.models._BaseModel;
import com.sireph.technics.table.components.Cell;

import org.json.JSONObject;

import java.util.List;

public abstract class _BaseTableModel<T> extends _BaseModel<T> {
    protected _BaseTableModel() {
    }

    protected _BaseTableModel(@NonNull JSONObject json) {
        super(json);
    }

    @NonNull
    public abstract List<Cell> toCellList();
}
