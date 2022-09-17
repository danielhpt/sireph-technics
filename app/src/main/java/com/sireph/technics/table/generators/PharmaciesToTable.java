package com.sireph.technics.table.generators;

import android.content.Context;

import androidx.annotation.NonNull;

import com.sireph.technics.R;
import com.sireph.technics.models.victim.Pharmacy;
import com.sireph.technics.table.components.ColumnHeader;

import java.util.Arrays;
import java.util.List;

public class PharmaciesToTable extends _ModelsToTable<Pharmacy> {
    @NonNull
    @Override
    protected List<ColumnHeader> getColumnHeaders(@NonNull Context context) {
        return Arrays.asList(new ColumnHeader(context.getString(R.string.hour)), new ColumnHeader(context.getString(R.string.pharmacy)),
                new ColumnHeader(context.getString(R.string.pharmacy_dose)), new ColumnHeader(context.getString(R.string.pharmacy_via)),
                new ColumnHeader(context.getString(R.string.pharmacy_adverse_effects)));
    }
}
