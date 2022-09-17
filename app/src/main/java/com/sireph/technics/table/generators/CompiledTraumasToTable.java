package com.sireph.technics.table.generators;

import android.content.Context;

import androidx.annotation.NonNull;

import com.sireph.technics.R;
import com.sireph.technics.models.victim.symptom.CompiledTrauma;
import com.sireph.technics.table.components.ColumnHeader;

import java.util.Arrays;
import java.util.List;

public class CompiledTraumasToTable extends _ModelsToTable<CompiledTrauma> {
    @NonNull
    @Override
    protected List<ColumnHeader> getColumnHeaders(@NonNull Context context) {
        return Arrays.asList(new ColumnHeader(context.getString(R.string.trauma_body_part)), new ColumnHeader(context.getString(R.string.trauma_type_f)),
                new ColumnHeader(context.getString(R.string.trauma_type_c)), new ColumnHeader(context.getString(R.string.trauma_type_w)),
                new ColumnHeader(context.getString(R.string.trauma_type_h)), new ColumnHeader(context.getString(R.string.trauma_type_b)),
                new ColumnHeader(context.getString(R.string.trauma_type_p)));
    }
}
