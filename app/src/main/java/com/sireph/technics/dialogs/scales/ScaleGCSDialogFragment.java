package com.sireph.technics.dialogs.scales;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sireph.technics.R;
import com.sireph.technics.databinding.DialogScaleGlasgowBinding;

public class ScaleGCSDialogFragment extends DialogFragment {
    private DialogScaleGlasgowBinding binding;

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        this.binding = DialogScaleGlasgowBinding.inflate(requireActivity().getLayoutInflater());

        builder.setView(this.binding.getRoot())
                .setTitle(R.string.gcs_scale)
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

        this.binding.groupGcsEyes.setOnCheckedChangeListener((group, checkedId) -> this.binding.buttonEyesAbsent.setError(null));
        this.binding.groupGcsVerbal.setOnCheckedChangeListener((group, checkedId) -> this.binding.buttonVerbalAbsent.setError(null));
        this.binding.groupGcsMotor.setOnCheckedChangeListener((group, checkedId) -> this.binding.buttonMotorAbsent.setError(null));
        assert dialog != null;
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            if (this.binding.groupGcsEyes.getCheckedRadioButtonId() != -1 && this.binding.groupGcsVerbal.getCheckedRadioButtonId() != -1 &&
                    this.binding.groupGcsMotor.getCheckedRadioButtonId() != -1) {
                dialog.dismiss();
            } else {
                if (this.binding.groupGcsEyes.getCheckedRadioButtonId() == -1) {
                    this.binding.buttonEyesAbsent.setError(getString(R.string.required_field));
                }
                if (this.binding.groupGcsVerbal.getCheckedRadioButtonId() == -1) {
                    this.binding.buttonVerbalAbsent.setError(getString(R.string.required_field));
                }
                if (this.binding.groupGcsMotor.getCheckedRadioButtonId() == -1) {
                    this.binding.buttonMotorAbsent.setError(getString(R.string.required_field));
                }
            }
        });
    }
}
