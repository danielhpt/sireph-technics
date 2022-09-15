package com.sireph.technics.table.generators;

import android.content.Context;

import androidx.annotation.NonNull;

import com.sireph.technics.R;
import com.sireph.technics.models.procedures.Evaluation;
import com.sireph.technics.table.components.ColumnHeader;

import java.util.Arrays;
import java.util.List;

public class EvaluationsToTable extends _ModelsToTable<Evaluation> {
    @NonNull
    @Override
    public List<ColumnHeader> getColumnHeaders(@NonNull Context context) {
        return Arrays.asList(new ColumnHeader(context.getString(R.string.hour)), new ColumnHeader(context.getString(R.string.avds)),
                new ColumnHeader(context.getString(R.string.mgap_gcs)), new ColumnHeader(context.getString(R.string.vent_cpm)),
                new ColumnHeader(context.getString(R.string.spo2_per)), new ColumnHeader(context.getString(R.string.o2_sup_lmin)),
                new ColumnHeader(context.getString(R.string.pulse_bpm)), new ColumnHeader(context.getString(R.string.skin)),
                new ColumnHeader(context.getString(R.string.temperature_c)), new ColumnHeader(context.getString(R.string.systolic_b_p)),
                new ColumnHeader(context.getString(R.string.diastolic_b_p)), new ColumnHeader(context.getString(R.string.pupils)),
                new ColumnHeader(context.getString(R.string.pain_0_10)), new ColumnHeader(context.getString(R.string.glycemia_mgdl)),
                new ColumnHeader(context.getString(R.string.ecg)), new ColumnHeader(context.getString(R.string.etco2_mmhg)),
                new ColumnHeader(context.getString(R.string.news)));
    }
}
