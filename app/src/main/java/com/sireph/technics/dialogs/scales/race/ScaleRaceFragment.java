package com.sireph.technics.dialogs.scales.race;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sireph.technics.R;

public class ScaleRaceFragment extends Fragment {
    public static String ARG_IS_LEFT = "isLeft", ARG_SCALE = "scale";
    private boolean isLeft;
    private ScaleRaceDialog.RACEScale scale;

    @SuppressWarnings("unused")
    public static ScaleRaceFragment newInstance(boolean isLeft, ScaleRaceDialog.RACEScale scale) {
        ScaleRaceFragment fragment = new ScaleRaceFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_IS_LEFT, isLeft);
        args.putSerializable(ARG_SCALE, scale);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.isLeft = getArguments().getBoolean(ARG_IS_LEFT);
            this.scale = (ScaleRaceDialog.RACEScale) getArguments().getSerializable(ARG_SCALE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_scale_race_fragment, container, false);
        if (this.isLeft) {
            view.findViewById(R.id.linearLayoutLeft).setVisibility(View.VISIBLE);
            view.findViewById(R.id.linearLayoutRight).setVisibility(View.GONE);
            view.findViewById(R.id.linearLayoutAgnosia).setVisibility(View.VISIBLE);
            view.findViewById(R.id.linearLayoutAfasia).setVisibility(View.GONE);

            if (scale.facialIdL != -1) ((RadioButton) view.findViewById(scale.facialIdL)).setChecked(true);
            if (scale.msIdL != -1) ((RadioButton) view.findViewById(scale.msIdL)).setChecked(true);
            if (scale.deviationIdL != -1) ((RadioButton) view.findViewById(scale.deviationIdL)).setChecked(true);
            if (scale.miIdL != -1) ((RadioButton) view.findViewById(scale.miIdL)).setChecked(true);
            if (scale.agnosiaId != -1) ((RadioButton) view.findViewById(scale.agnosiaId)).setChecked(true);

            ((RadioGroup) view.findViewById(R.id.radioGroupRaceFacial)).setOnCheckedChangeListener((group, checkedId) -> {
                scale.facialIdL = checkedId;
                ((RadioButton) view.findViewById(R.id.buttonRaceFacialModerate)).setError(null);
            });
            ((RadioGroup) view.findViewById(R.id.radioGroupRaceMS)).setOnCheckedChangeListener((group, checkedId) -> {
                scale.msIdL = checkedId;
                ((RadioButton) view.findViewById(R.id.buttonRaceMSSevere)).setError(null);
            });
            ((RadioGroup) view.findViewById(R.id.radioGroupRaceDeviationL)).setOnCheckedChangeListener((group, checkedId) -> {
                scale.deviationIdL = checkedId;
                ((RadioButton) view.findViewById(R.id.buttonRaceLeftDPresent)).setError(null);
            });
            ((RadioGroup) view.findViewById(R.id.radioGroupRaceMI)).setOnCheckedChangeListener((group, checkedId) -> {
                scale.miIdL = checkedId;
                ((RadioButton) view.findViewById(R.id.buttonRaceMISevere)).setError(null);
            });
            ((RadioGroup) view.findViewById(R.id.radioGroupRaceAgnosia)).setOnCheckedChangeListener((group, checkedId) -> {
                scale.agnosiaId = checkedId;
                ((RadioButton) view.findViewById(R.id.buttonRaceLeftANeither)).setError(null);
            });
        } else {
            view.findViewById(R.id.linearLayoutLeft).setVisibility(View.GONE);
            view.findViewById(R.id.linearLayoutRight).setVisibility(View.VISIBLE);
            view.findViewById(R.id.linearLayoutAgnosia).setVisibility(View.GONE);
            view.findViewById(R.id.linearLayoutAfasia).setVisibility(View.VISIBLE);

            if (scale.facialIdR != -1) ((RadioButton) view.findViewById(scale.facialIdR)).setChecked(true);
            if (scale.msIdR != -1) ((RadioButton) view.findViewById(scale.msIdR)).setChecked(true);
            if (scale.deviationIdR != -1) ((RadioButton) view.findViewById(scale.deviationIdR)).setChecked(true);
            if (scale.miIdR != -1) ((RadioButton) view.findViewById(scale.miIdR)).setChecked(true);
            if (scale.afasiaId != -1) ((RadioButton) view.findViewById(scale.afasiaId)).setChecked(true);

            ((RadioGroup) view.findViewById(R.id.radioGroupRaceFacial)).setOnCheckedChangeListener((group, checkedId) -> {
                scale.facialIdR = checkedId;
                ((RadioButton) view.findViewById(R.id.buttonRaceFacialModerate)).setError(null);
            });
            ((RadioGroup) view.findViewById(R.id.radioGroupRaceMS)).setOnCheckedChangeListener((group, checkedId) -> {
                scale.msIdR = checkedId;
                ((RadioButton) view.findViewById(R.id.buttonRaceMSSevere)).setError(null);
            });
            ((RadioGroup) view.findViewById(R.id.radioGroupRaceDeviationR)).setOnCheckedChangeListener((group, checkedId) -> {
                scale.deviationIdR = checkedId;
                ((RadioButton) view.findViewById(R.id.buttonRaceRightDPresent)).setError(null);
            });
            ((RadioGroup) view.findViewById(R.id.radioGroupRaceMI)).setOnCheckedChangeListener((group, checkedId) -> {
                scale.miIdR = checkedId;
                ((RadioButton) view.findViewById(R.id.buttonRaceMISevere)).setError(null);
            });
            ((RadioGroup) view.findViewById(R.id.radioGroupRaceAfasia)).setOnCheckedChangeListener((group, checkedId) -> {
                scale.afasiaId = checkedId;
                ((RadioButton) view.findViewById(R.id.buttonRaceRightANo)).setError(null);
            });
        }
        return view;
    }
}
