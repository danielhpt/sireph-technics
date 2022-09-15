package com.sireph.technics.table.generators;

import android.content.Context;

import androidx.annotation.NonNull;

import com.sireph.technics.R;
import com.sireph.technics.models.procedures.Trauma;
import com.sireph.technics.table.components.ColumnHeader;

import java.util.Arrays;
import java.util.List;

public class TraumasToTable extends _ModelsToTable<Trauma> {
//    @Override
//    public void setupTable(@NonNull TableView contentContainer, @NonNull List<Trauma> list, Context context) {
//        contentContainer.setAdapter(new TableViewAdapter());
//        //noinspection unchecked
//        Objects.requireNonNull(contentContainer.getAdapter()).setAllItems(getColumnHeaders(context), getRowHeaders(list), getCells(list));
//        contentContainer.setIgnoreSelectionColors(true);
//    }

    @NonNull
    @Override
    protected List<ColumnHeader> getColumnHeaders(@NonNull Context context) {
        return Arrays.asList(new ColumnHeader(context.getString(R.string.trauma_body_part)), new ColumnHeader(context.getString(R.string.trauma_type_f)),
                new ColumnHeader(context.getString(R.string.trauma_type_c)), new ColumnHeader(context.getString(R.string.trauma_type_w)),
                new ColumnHeader(context.getString(R.string.trauma_type_h)), new ColumnHeader(context.getString(R.string.trauma_type_b)),
                new ColumnHeader(context.getString(R.string.trauma_type_p)), new ColumnHeader("G"));
    }

//    @NonNull
//    public List<RowHeader> getRowHeaders(@NonNull List<Trauma> traumas) {
//        List<RowHeader> rowHeaders = new ArrayList<>();
//        for (Trauma t : traumas) {
//            rowHeaders.add(new RowHeader(t.getBody_part().toString()));
//        }
//        return rowHeaders;
//    }
}
