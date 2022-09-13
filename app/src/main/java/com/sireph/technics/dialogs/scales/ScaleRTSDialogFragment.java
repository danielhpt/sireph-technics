package com.sireph.technics.dialogs.scales;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sireph.technics.R;
import com.sireph.technics.databinding.DialogScaleRtsBinding;

import java.io.Serializable;

public class ScaleRTSDialogFragment extends DialogFragment {
    private final RTSScale scale;
    private final ScaleRTSDialogListener listener;
    private DialogScaleRtsBinding binding;

    public ScaleRTSDialogFragment(RTSScale scale, ScaleRTSDialogListener listener) {
        this.scale = scale;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        this.binding = DialogScaleRtsBinding.inflate(requireActivity().getLayoutInflater());

        builder.setView(this.binding.getRoot())
                .setTitle(R.string.rts_scale)
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

        if (this.scale != null) {
            ((RadioButton) this.binding.getRoot().findViewById(this.scale.gcsId)).setChecked(true);
            ((RadioButton) this.binding.getRoot().findViewById(this.scale.frId)).setChecked(true);
            ((RadioButton) this.binding.getRoot().findViewById(this.scale.pasId)).setChecked(true);
        }

        this.binding.groupRtsGcs.setOnCheckedChangeListener((group, checkedId) -> this.binding.buttonRtsGcs3.setError(null));
        this.binding.groupRtsFr.setOnCheckedChangeListener((group, checkedId) -> this.binding.buttonRtsFr0.setError(null));
        this.binding.groupRtsPas.setOnCheckedChangeListener((group, checkedId) -> this.binding.buttonRtsPas0.setError(null));
        assert dialog != null;
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            if (this.binding.groupRtsGcs.getCheckedRadioButtonId() != -1 && this.binding.groupRtsFr.getCheckedRadioButtonId() != -1 &&
                    this.binding.groupRtsPas.getCheckedRadioButtonId() != -1) {
                this.listener.onScaleRTSDialogOk(new RTSScale(this.binding.groupRtsGcs.getCheckedRadioButtonId(),
                        this.binding.groupRtsFr.getCheckedRadioButtonId(), this.binding.groupRtsPas.getCheckedRadioButtonId()));
                dialog.dismiss();
            } else {
                if (this.binding.groupRtsGcs.getCheckedRadioButtonId() == -1) {
                    this.binding.buttonRtsGcs3.setError(getString(R.string.required_field));
                }
                if (this.binding.groupRtsFr.getCheckedRadioButtonId() == -1) {
                    this.binding.buttonRtsFr0.setError(getString(R.string.required_field));
                }
                if (this.binding.groupRtsPas.getCheckedRadioButtonId() == -1) {
                    this.binding.buttonRtsPas0.setError(getString(R.string.required_field));
                }
            }
        });
    }

    public interface ScaleRTSDialogListener {
        void onScaleRTSDialogOk(RTSScale scale);
    }

    public static class RTSScale implements Serializable {
        int gcsId;
        int frId;
        int pasId;
        int value;

        public RTSScale(int gcsId, int frId, int pasId) {
            this.gcsId = gcsId;
            this.frId = frId;
            this.pasId = pasId;
            calcValue();
        }

        @SuppressLint("NonConstantResourceId")
        private void calcValue() {
            this.value = 0;
            switch (gcsId) {
                case R.id.buttonRtsGcs13:
                    value += 4;
                    break;
                case R.id.buttonRtsGcs9:
                    value += 3;
                    break;
                case R.id.buttonRtsGcs6:
                    value += 2;
                    break;
                case R.id.buttonRtsGcs4:
                    value += 1;
                    break;
            }
            switch (frId) {
                case R.id.buttonRtsFr29:
                    value += 3;
                    break;
                case R.id.buttonRtsFr10:
                    value += 4;
                    break;
                case R.id.buttonRtsFr6:
                    value += 2;
                    break;
                case R.id.buttonRtsFr1:
                    value += 1;
                    break;
            }
            switch (pasId) {
                case R.id.buttonRtsPas89:
                    value += 4;
                    break;
                case R.id.buttonRtsPas76:
                    value += 3;
                    break;
                case R.id.buttonRtsPas50:
                    value += 2;
                    break;
                case R.id.buttonRtsPas1:
                    value += 1;
                    break;
            }
        }

        public int getValue() {
            return value;
        }
    }
}
