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
import com.sireph.technics.databinding.DialogScaleMgapBinding;
import com.sireph.technics.utils.TextChangedWatcher;
import com.sireph.technics.utils.Validation;

import java.io.Serializable;

public class ScaleMGAPDialog extends DialogFragment {
    private final MGAPScale scale;
    private final ScaleMGAPDialogListener listener;
    private DialogScaleMgapBinding binding;

    public ScaleMGAPDialog(MGAPScale scale, ScaleMGAPDialogListener listener) {
        this.scale = scale;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        this.binding = DialogScaleMgapBinding.inflate(requireActivity().getLayoutInflater());

        this.binding.textGCS.addTextChangedListener(new TextChangedWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Validation.validateInt(s.toString(), 3, 15, false)) {
                    binding.textGCS.setError(getString(R.string.required_field));
                } else {
                    binding.textGCS.setError(null);
                }
            }
        });

        builder.setView(this.binding.getRoot())
                .setTitle(R.string.mgap_scale)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                });
        return builder.create();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onStart() {
        super.onStart();

        AlertDialog dialog = (AlertDialog) getDialog();

        if (this.scale != null) {
            ((RadioButton) this.binding.getRoot().findViewById(this.scale.injuryId)).setChecked(true);
            ((RadioButton) this.binding.getRoot().findViewById(this.scale.pasId)).setChecked(true);
            this.binding.checkBoxAge60.setChecked(this.scale.age);
            this.binding.textGCS.setText(Integer.toString(this.scale.gcs));
        }

        this.binding.radioGroupM.setOnCheckedChangeListener((group, checkedId) -> this.binding.radioButtonIC.setError(null));
        this.binding.radioGroupPAS.setOnCheckedChangeListener((group, checkedId) -> this.binding.radioButtonPAS60.setError(null));
        assert dialog != null;
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String gcs = this.binding.textGCS.getText().toString();
            boolean valid = Validation.validateInt(gcs, 3, 15, false);
            if (valid && this.binding.radioGroupM.getCheckedRadioButtonId() != -1 &&
                    this.binding.radioGroupPAS.getCheckedRadioButtonId() != -1) {
                this.listener.onScaleMGAPDialogOk(new MGAPScale(this.binding.radioGroupM.getCheckedRadioButtonId(),
                        this.binding.radioGroupPAS.getCheckedRadioButtonId(), this.binding.checkBoxAge60.isChecked(), Integer.parseInt(gcs)));
                dialog.dismiss();
            } else {
                if (!valid) {
                    this.binding.textGCS.setError(getString(R.string.required_field));
                }
                if (this.binding.radioGroupM.getCheckedRadioButtonId() == -1) {
                    this.binding.radioButtonIC.setError(getString(R.string.required_field));
                }
                if (this.binding.radioGroupPAS.getCheckedRadioButtonId() == -1) {
                    this.binding.radioButtonPAS60.setError(getString(R.string.required_field));
                }
            }
        });
    }

    public interface ScaleMGAPDialogListener {
        void onScaleMGAPDialogOk(MGAPScale scale);
    }

    public static class MGAPScale implements Serializable {
        int injuryId;
        int pasId;
        boolean age;
        int gcs;
        int value;

        public MGAPScale(int injuryId, int pasId, boolean age, int gcs) {
            this.injuryId = injuryId;
            this.pasId = pasId;
            this.age = age;
            this.gcs = gcs;
            calcValue();
        }

        @SuppressLint("NonConstantResourceId")
        private void calcValue() {
            this.value = gcs;
            if (age) value += 5;
            if (injuryId == R.id.radioButtonIC) value += 4;
            switch (pasId) {
                case R.id.radioButtonPAS120:
                    value += 5;
                    break;
                case R.id.radioButtonPAS60_120:
                    value += 3;
                    break;
            }
        }

        public int getValue() {
            return value;
        }
    }
}
