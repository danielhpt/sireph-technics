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
import com.sireph.technics.databinding.DialogScaleGlasgowBinding;
import com.sireph.technics.models.procedures.GlasgowScale;

public class ScaleGCSDialogFragment extends DialogFragment {
    private DialogScaleGlasgowBinding binding;
    private final ScaleGCSDialogListener listener;
    private final GlasgowScale scale;

    public ScaleGCSDialogFragment(GlasgowScale scale, ScaleGCSDialogListener listener) {
        this.listener = listener;
        this.scale = scale;
    }

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onStart() {
        super.onStart();

        AlertDialog dialog = (AlertDialog) getDialog();

        if (this.scale != null) {
            ((RadioButton) this.binding.groupGcsEyes.getChildAt(4 - this.scale.getEyes())).setChecked(true);
            ((RadioButton) this.binding.groupGcsVerbal.getChildAt(5 - this.scale.getEyes())).setChecked(true);
            ((RadioButton) this.binding.groupGcsMotor.getChildAt(6 - this.scale.getEyes())).setChecked(true);
        }

        this.binding.groupGcsEyes.setOnCheckedChangeListener((group, checkedId) -> this.binding.buttonEyesAbsent.setError(null));
        this.binding.groupGcsVerbal.setOnCheckedChangeListener((group, checkedId) -> this.binding.buttonVerbalAbsent.setError(null));
        this.binding.groupGcsMotor.setOnCheckedChangeListener((group, checkedId) -> this.binding.buttonMotorAbsent.setError(null));
        assert dialog != null;
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            if (this.binding.groupGcsEyes.getCheckedRadioButtonId() != -1 && this.binding.groupGcsVerbal.getCheckedRadioButtonId() != -1 &&
                    this.binding.groupGcsMotor.getCheckedRadioButtonId() != -1) {
                int eyes, verbal, motor;
                switch (this.binding.groupGcsEyes.getCheckedRadioButtonId()) {
                    case R.id.buttonEyesSpontaneous:
                        eyes = 4;
                        break;
                    case R.id.buttonEyesSound:
                        eyes = 3;
                        break;
                    case R.id.buttonEyesPressure:
                        eyes = 2;
                        break;
                    case R.id.buttonEyesAbsent:
                        eyes = 1;
                        break;
                    default:
                        eyes = 0;
                }
                switch (this.binding.groupGcsVerbal.getCheckedRadioButtonId()) {
                    case R.id.buttonVerbalOriented:
                        verbal = 5;
                        break;
                    case R.id.buttonVerbalConfused:
                        verbal = 4;
                        break;
                    case R.id.buttonVerbalWords:
                        verbal = 3;
                        break;
                    case R.id.buttonVerbalSounds:
                        verbal = 2;
                        break;
                    case R.id.buttonVerbalAbsent:
                        verbal = 1;
                        break;
                    default:
                        verbal = 0;
                }
                switch (this.binding.groupGcsMotor.getCheckedRadioButtonId()) {
                    case R.id.buttonMotorOrders:
                        motor = 6;
                        break;
                    case R.id.buttonMotorLocator:
                        motor = 5;
                        break;
                    case R.id.buttonMotorNormalFlexion:
                        motor = 4;
                        break;
                    case R.id.buttonMotorAbnormalFlexion:
                        motor = 3;
                        break;
                    case R.id.buttonMotorExtension:
                        motor = 2;
                        break;
                    case R.id.buttonMotorAbsent:
                        motor = 1;
                        break;
                    default:
                        motor = 0;
                }
                this.listener.onScaleGCSDialogOk(new GlasgowScale(eyes, verbal, motor));
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

    public interface ScaleGCSDialogListener {
        void onScaleGCSDialogOk(GlasgowScale scale);
    }
}
