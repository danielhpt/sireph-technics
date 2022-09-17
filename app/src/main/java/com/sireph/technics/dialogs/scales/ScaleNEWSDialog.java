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
import com.sireph.technics.databinding.DialogScaleNewsBinding;

import java.io.Serializable;

public class ScaleNEWSDialog extends DialogFragment {
    private final ScaleNEWSDialogListener listener;
    private final NEWSScale scale;
    private DialogScaleNewsBinding binding;

    public ScaleNEWSDialog(NEWSScale scale, ScaleNEWSDialogListener listener) {
        this.listener = listener;
        this.scale = scale;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        this.binding = DialogScaleNewsBinding.inflate(requireActivity().getLayoutInflater());

        builder.setView(this.binding.getRoot())
                .setTitle(R.string.news_scale)
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
            ((RadioButton) this.binding.getRoot().findViewById(this.scale.respiratoryId)).setChecked(true);
            ((RadioButton) this.binding.getRoot().findViewById(this.scale.spo2Id)).setChecked(true);
            ((RadioButton) this.binding.getRoot().findViewById(this.scale.conscienceId)).setChecked(true);
            ((RadioButton) this.binding.getRoot().findViewById(this.scale.heartId)).setChecked(true);
            ((RadioButton) this.binding.getRoot().findViewById(this.scale.systolicId)).setChecked(true);
            ((RadioButton) this.binding.getRoot().findViewById(this.scale.tempId)).setChecked(true);
            this.binding.checkBoxO2Supplement.setChecked(this.scale.o2supChecked);
        }

        this.binding.radioGroupC.setOnCheckedChangeListener((group, checkedId) -> this.binding.radioButtonCN.setError(null));
        this.binding.radioGroupH.setOnCheckedChangeListener((group, checkedId) -> this.binding.radioButtonH40.setError(null));
        this.binding.radioGroupR.setOnCheckedChangeListener((group, checkedId) -> this.binding.radioButtonR8.setError(null));
        this.binding.radioGroupSBP.setOnCheckedChangeListener((group, checkedId) -> this.binding.radioButtonSBP90.setError(null));
        this.binding.radioGroupSO.setOnCheckedChangeListener((group, checkedId) -> this.binding.radioButtonSO91.setError(null));
        this.binding.radioGroupT.setOnCheckedChangeListener((group, checkedId) -> this.binding.radioButtonT35.setError(null));
        assert dialog != null;
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            if (this.binding.radioGroupC.getCheckedRadioButtonId() != -1 && this.binding.radioGroupH.getCheckedRadioButtonId() != -1 &&
                    this.binding.radioGroupR.getCheckedRadioButtonId() != -1 && this.binding.radioGroupSBP.getCheckedRadioButtonId() != -1 &&
                    this.binding.radioGroupSO.getCheckedRadioButtonId() != -1 && this.binding.radioGroupT.getCheckedRadioButtonId() != -1) {
                this.listener.onScaleNEWSDialogOk(new NEWSScale(this.binding.radioGroupR.getCheckedRadioButtonId(),
                        this.binding.checkBoxO2Supplement.isChecked(), this.binding.radioGroupSBP.getCheckedRadioButtonId(),
                        this.binding.radioGroupC.getCheckedRadioButtonId(), this.binding.radioGroupSO.getCheckedRadioButtonId(),
                        this.binding.radioGroupT.getCheckedRadioButtonId(), this.binding.radioGroupH.getCheckedRadioButtonId()));
                dialog.dismiss();
            } else {
                if (this.binding.radioGroupC.getCheckedRadioButtonId() == -1) {
                    this.binding.radioButtonCN.setError(getString(R.string.required_field));
                }
                if (this.binding.radioGroupH.getCheckedRadioButtonId() == -1) {
                    this.binding.radioButtonH40.setError(getString(R.string.required_field));
                }
                if (this.binding.radioGroupR.getCheckedRadioButtonId() == -1) {
                    this.binding.radioButtonR8.setError(getString(R.string.required_field));
                }
                if (this.binding.radioGroupSBP.getCheckedRadioButtonId() == -1) {
                    this.binding.radioButtonSBP90.setError(getString(R.string.required_field));
                }
                if (this.binding.radioGroupSO.getCheckedRadioButtonId() == -1) {
                    this.binding.radioButtonSO91.setError(getString(R.string.required_field));
                }
                if (this.binding.radioGroupT.getCheckedRadioButtonId() == -1) {
                    this.binding.radioButtonT35.setError(getString(R.string.required_field));
                }
            }
        });
    }

    public interface ScaleNEWSDialogListener {
        void onScaleNEWSDialogOk(NEWSScale scale);
    }

    public static class NEWSScale implements Serializable {
        int respiratoryId;
        boolean o2supChecked;
        int systolicId;
        int conscienceId;
        int spo2Id;
        int tempId;
        int heartId;
        int value;

        public NEWSScale(int respiratoryId, boolean o2supChecked, int systolicId, int conscienceId, int spo2Id, int tempId, int heartId) {
            this.respiratoryId = respiratoryId;
            this.o2supChecked = o2supChecked;
            this.systolicId = systolicId;
            this.conscienceId = conscienceId;
            this.spo2Id = spo2Id;
            this.tempId = tempId;
            this.heartId = heartId;
            calcValue();
        }

        @SuppressLint("NonConstantResourceId")
        private void calcValue() {
            this.value = 0;
            if (this.o2supChecked) {
                this.value += 2;
            }
            if (this.conscienceId == R.id.radioButtonCN) {
                this.value += 3;
            }
            switch (this.respiratoryId) {
                case R.id.radioButtonR25:
                case R.id.radioButtonR8:
                    this.value += 3;
                    break;
                case R.id.radioButtonR21:
                    this.value += 2;
                    break;
                case R.id.radioButtonR9:
                    this.value += 1;
                    break;
            }
            switch (this.systolicId) {
                case R.id.radioButtonSBP220:
                case R.id.radioButtonSBP90:
                    this.value += 3;
                    break;
                case R.id.radioButtonSBP101:
                    this.value += 1;
                    break;
                case R.id.radioButtonSBP91:
                    this.value += 2;
                    break;
            }
            switch (this.spo2Id) {
                case R.id.radioButtonSO94:
                    this.value += 1;
                    break;
                case R.id.radioButtonSO92:
                    this.value += 2;
                    break;
                case R.id.radioButtonSO91:
                    this.value += 3;
                    break;
            }
            switch (this.tempId) {
                case R.id.radioButtonT39_1:
                    this.value += 2;
                    break;
                case R.id.radioButtonT38_1:
                case R.id.radioButtonT35_1:
                    this.value += 1;
                    break;
                case R.id.radioButtonT35:
                    this.value += 3;
                    break;
            }
            switch (this.heartId) {
                case R.id.radioButtonH131:
                case R.id.radioButtonH40:
                    this.value += 3;
                    break;
                case R.id.radioButtonH111:
                    this.value += 2;
                    break;
                case R.id.radioButtonH91:
                case R.id.radioButtonH41:
                    this.value += 1;
                    break;
            }
        }

        public int getValue() {
            return this.value;
        }
    }
}
