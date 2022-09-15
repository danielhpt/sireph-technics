package com.sireph.technics.table.generators;

import android.content.Context;

import androidx.annotation.NonNull;

import com.evrencoskun.tableview.TableView;
import com.sireph.technics.models.procedures._BaseTableModel;
import com.sireph.technics.table.TableViewAdapter;
import com.sireph.technics.table.components.Cell;
import com.sireph.technics.table.components.ColumnHeader;
import com.sireph.technics.table.components.RowHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("rawtypes")
public abstract class _ModelsToTable<T extends _BaseTableModel> {
    public void setupTable(@NonNull TableView contentContainer, @NonNull List<T> list, Context context) {
        contentContainer.setAdapter(new TableViewAdapter());
        //noinspection unchecked
        Objects.requireNonNull(contentContainer.getAdapter()).setAllItems(getColumnHeaders(context), getRowHeaders(list.size()), getCells(list));
        contentContainer.setIgnoreSelectionColors(true);
    }

    @NonNull
    protected abstract List<ColumnHeader> getColumnHeaders(@NonNull Context context);

    @NonNull
    public List<RowHeader> getRowHeaders(int len) {
        List<RowHeader> rowHeaders = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            rowHeaders.add(new RowHeader(Integer.toString(i + 1)));
        }
        return rowHeaders;
    }

    @NonNull
    public List<List<Cell>> getCells(@NonNull List<T> list) {
        List<List<Cell>> cells = new ArrayList<>();
        for (T t : list) {
            //noinspection unchecked
            cells.add(t.toCellList());
        }
        return cells;
    }
}
