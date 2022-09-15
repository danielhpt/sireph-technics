package com.sireph.technics.dialogs.scales.race;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sireph.technics.R;

import java.io.Serializable;
import java.util.Objects;

public class ScaleRaceDialogFragment extends DialogFragment {
    private final ScaleRaceDialogFragment.RACEScale scale;
    private final ScaleRACEDialogListener listener;

    public ScaleRaceDialogFragment(RACEScale scale, ScaleRACEDialogListener listener) {
        this.scale = scale;
        this.listener = listener;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_scale_race, null);

        ScaleRaceAdapter raceAdapter = new ScaleRaceAdapter(getParentFragmentManager(), getLifecycle(), this.scale);
        ViewPager2 raceViewPager = view.findViewById(R.id.raceViewPager);
        raceViewPager.setAdapter(raceAdapter);

        TabLayout raceTabs = view.findViewById(R.id.raceTabs);
        new TabLayoutMediator(raceTabs, raceViewPager, ((tab, position) -> {
            if (position == 0) {
                tab.setText(R.string.race_left);
            } else {
                tab.setText(R.string.race_right);
            }
        })).attach();

        builder.setView(view)
                .setTitle(R.string.race_scale)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                });
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        AlertDialog dialog = (AlertDialog) getDialog();

        assert dialog != null;
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            if (scale.facialIdL != -1 && scale.msIdL != -1 && scale.deviationIdL != -1 && scale.miIdL != -1 && scale.agnosiaId != -1 &&
                    scale.facialIdR != -1 && scale.msIdR != -1 && scale.deviationIdR != -1 && scale.miIdR != -1 && scale.afasiaId != -1) {
                scale.calcValue();
                listener.onScaleRACEDialogOk(scale);
                dialog.dismiss();
            } else {
                if (getParentFragmentManager().findFragmentByTag("f0") != null) {
                    View left = Objects.requireNonNull(getParentFragmentManager().findFragmentByTag("f0")).getView();
                    assert left != null;
                    if (scale.facialIdL == -1) {
                        ((RadioButton) left.findViewById(R.id.buttonRaceFacialModerate)).setError(getString(R.string.required_field));
                    }
                    if (scale.msIdL == -1) {
                        ((RadioButton) left.findViewById(R.id.buttonRaceMSSevere)).setError(getString(R.string.required_field));
                    }
                    if (scale.deviationIdL == -1) {
                        ((RadioButton) left.findViewById(R.id.buttonRaceLeftDPresent)).setError(getString(R.string.required_field));
                    }
                    if (scale.miIdL == -1) {
                        ((RadioButton) left.findViewById(R.id.buttonRaceMISevere)).setError(getString(R.string.required_field));
                    }
                    if (scale.agnosiaId == -1) {
                        ((RadioButton) left.findViewById(R.id.buttonRaceLeftANeither)).setError(getString(R.string.required_field));
                    }
                }
                if (getParentFragmentManager().findFragmentByTag("f1") != null) {
                    View right = Objects.requireNonNull(getParentFragmentManager().findFragmentByTag("f1")).getView();
                    assert right != null;
                    if (scale.facialIdR == -1) {
                        ((RadioButton) right.findViewById(R.id.buttonRaceFacialModerate)).setError(getString(R.string.required_field));
                    }
                    if (scale.msIdR == -1) {
                        ((RadioButton) right.findViewById(R.id.buttonRaceMSSevere)).setError(getString(R.string.required_field));
                    }
                    if (scale.deviationIdR == -1) {
                        ((RadioButton) right.findViewById(R.id.buttonRaceRightDPresent)).setError(getString(R.string.required_field));
                    }
                    if (scale.miIdR == -1) {
                        ((RadioButton) right.findViewById(R.id.buttonRaceMISevere)).setError(getString(R.string.required_field));
                    }
                    if (scale.afasiaId == -1) {
                        ((RadioButton) right.findViewById(R.id.buttonRaceRightANo)).setError(getString(R.string.required_field));
                    }
                }
            }
        });
    }

    public interface ScaleRACEDialogListener {
        void onScaleRACEDialogOk(RACEScale scale);
    }

    public static class RACEScale implements Serializable {
        public int facialIdL, msIdL, deviationIdL, miIdL, agnosiaId, facialIdR, msIdR, deviationIdR, miIdR, afasiaId, value;

        public RACEScale() {
            this.facialIdL = -1;
            this.msIdL = -1;
            this.deviationIdL = -1;
            this.miIdL = -1;
            this.agnosiaId = -1;
            this.facialIdR = -1;
            this.msIdR = -1;
            this.deviationIdR = -1;
            this.miIdR = -1;
            this.afasiaId = -1;
        }

        @SuppressLint("NonConstantResourceId")
        protected void calcValue() {
            value = 0;
            switch (facialIdL) {
                case R.id.buttonRaceFacialModerate:
                    value++;
                case R.id.buttonRaceFacialLight:
                    value++;
            }
            switch (facialIdR) {
                case R.id.buttonRaceFacialModerate:
                    value++;
                case R.id.buttonRaceFacialLight:
                    value++;
            }
            switch (msIdL) {
                case R.id.buttonRaceMSSevere:
                    value++;
                case R.id.buttonRaceMSModerate:
                    value++;
            }
            switch (msIdR) {
                case R.id.buttonRaceMSSevere:
                    value++;
                case R.id.buttonRaceMSModerate:
                    value++;
            }
            switch (miIdL) {
                case R.id.buttonRaceMISevere:
                    value++;
                case R.id.buttonRaceMIModerate:
                    value++;
            }
            switch (miIdR) {
                case R.id.buttonRaceMISevere:
                    value++;
                case R.id.buttonRaceMIModerate:
                    value++;
            }
            switch (agnosiaId) {
                case R.id.buttonRaceLeftANeither:
                    value++;
                case R.id.buttonRaceLeftAOr:
                    value++;
            }
            switch (afasiaId) {
                case R.id.buttonRaceRightANo:
                    value++;
                case R.id.buttonRaceRightA1:
                    value++;
            }
            if (deviationIdL == R.id.buttonRaceLeftDPresent) value++;
            if (deviationIdR == R.id.buttonRaceRightDPresent) value++;
        }
    }
}
