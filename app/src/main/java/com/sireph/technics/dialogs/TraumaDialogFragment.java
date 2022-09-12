package com.sireph.technics.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sireph.technics.R;
import com.sireph.technics.databinding.DialogAddTraumaBinding;
import com.sireph.technics.models.enums.BodyPart;
import com.sireph.technics.models.enums.BurnDegree;
import com.sireph.technics.models.enums.TypeOfInjury;
import com.sireph.technics.models.procedures.Trauma;

public class TraumaDialogFragment extends DialogFragment {
    private DialogAddTraumaBinding binding;
    private final BodyPart bodyPart;
    private final TraumaDialogListener listener;

    public TraumaDialogFragment(BodyPart bodyPart, TraumaDialogListener listener) {
        this.bodyPart = bodyPart;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        this.binding = DialogAddTraumaBinding.inflate(requireActivity().getLayoutInflater());

        builder.setView(this.binding.getRoot())
                .setTitle(R.string.add_trauma)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                });
        return builder.create();
    }

    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    @Override
    public void onStart() {
        super.onStart();

        AlertDialog dialog = (AlertDialog) getDialog();

        this.binding.bodyPart.setText(this.bodyPart.toString());

        this.binding.radioGroupTT.setOnCheckedChangeListener((group, checkedId) -> {
            this.binding.radioButtonP.setError(null);
            if (checkedId == R.id.radioButtonB) {
                for (int i = 0; i < this.binding.radioGroupBD.getChildCount(); i++) {
                    this.binding.radioGroupBD.getChildAt(i).setEnabled(true);
                }
            } else {
                for (int i = 0; i < this.binding.radioGroupBD.getChildCount(); i++) {
                    this.binding.radioGroupBD.getChildAt(i).setEnabled(false);
                }
            }
        });
        this.binding.radioGroupBD.setOnCheckedChangeListener((group, checkedId) -> this.binding.radioButtonG3.setError(null));
        assert dialog != null;
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            TypeOfInjury type;
            BurnDegree degree = null;
            switch (this.binding.radioGroupTT.getCheckedRadioButtonId()) {
                case R.id.radioButtonF:
                    type = TypeOfInjury.FRACTURE;
                    break;
                case R.id.radioButtonC:
                    type = TypeOfInjury.CONTUSION;
                    break;
                case R.id.radioButtonW:
                    type = TypeOfInjury.WOUND;
                    break;
                case R.id.radioButtonH:
                    type = TypeOfInjury.HAEMORRHAGE;
                    break;
                case R.id.radioButtonP:
                    type = TypeOfInjury.PAIN;
                    break;
                case R.id.radioButtonB:
                    type = TypeOfInjury.BURN;
                    switch (this.binding.radioGroupBD.getCheckedRadioButtonId()) {
                        case R.id.radioButtonG1:
                            degree = BurnDegree.G1;
                            break;
                        case R.id.radioButtonG2:
                            degree = BurnDegree.G2;
                            break;
                        case R.id.radioButtonG3:
                            degree = BurnDegree.G3;
                            break;
                        default:
                            this.binding.radioButtonG3.setError(getString(R.string.required_field));
                            return;
                    }
                    break;
                default:
                    this.binding.radioButtonP.setError(getString(R.string.required_field));
                    return;
            }
            this.listener.onTraumaDialogOk(new Trauma(!this.binding.checkBoxPenetrating.isChecked(), this.bodyPart, type, degree));
            dialog.dismiss();
        });
    }

    public interface TraumaDialogListener {
        void onTraumaDialogOk(Trauma trauma);
    }
}