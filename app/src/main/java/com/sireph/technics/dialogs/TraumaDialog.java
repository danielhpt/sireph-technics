package com.sireph.technics.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sireph.technics.R;
import com.sireph.technics.databinding.DialogAddTraumaBinding;
import com.sireph.technics.models.enums.BodyPart;
import com.sireph.technics.models.enums.BurnDegree;
import com.sireph.technics.models.victim.symptom.CompiledTrauma;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TraumaDialog extends DialogFragment {
    private final BodyPart bodyPart;
    private final TraumaDialogListener listener;
    private DialogAddTraumaBinding binding;

    public TraumaDialog(BodyPart bodyPart, TraumaDialogListener listener) {
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

        AtomicInteger nChecked = new AtomicInteger();

        List<CheckBox> typesOfInjury = Arrays.asList(binding.checkBoxFracture, binding.checkBoxContusion, binding.checkBoxWound,
                binding.checkBoxHaemorrhage, binding.checkBoxPain);

        for (CheckBox c : typesOfInjury) {
            c.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    nChecked.getAndIncrement();
                    binding.checkBoxPain.setError(null);
                } else {
                    nChecked.getAndDecrement();
                    if (nChecked.get() == 0) {
                        binding.checkBoxPain.setError(getString(R.string.required_field));
                    }
                }
            });
        }

        this.binding.checkBoxBurn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                nChecked.getAndIncrement();
                binding.checkBoxPain.setError(null);
                for (int i = 0; i < this.binding.radioGroupBD.getChildCount(); i++) {
                    this.binding.radioGroupBD.getChildAt(i).setEnabled(true);
                }
            } else {
                nChecked.getAndDecrement();
                if (nChecked.get() == 0) {
                    binding.checkBoxPain.setError(getString(R.string.required_field));
                }
                for (int i = 0; i < this.binding.radioGroupBD.getChildCount(); i++) {
                    this.binding.radioGroupBD.getChildAt(i).setEnabled(false);
                }
            }
        });

        this.binding.checkBoxPenetrating.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.checkBoxWound.setChecked(true);
                binding.checkBoxWound.setClickable(false);
            } else {
                binding.checkBoxWound.setClickable(true);
            }
        });

        this.binding.radioGroupBD.setOnCheckedChangeListener((group, checkedId) -> this.binding.radioButtonG3.setError(null));
        assert dialog != null;
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            if (nChecked.get() == 0) {
                binding.checkBoxPain.setError(getString(R.string.required_field));
                return;
            }

            BurnDegree degree = BurnDegree.EMPTY;
            if (binding.checkBoxBurn.isChecked()) {
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
            }
            this.listener.onTraumaDialogOk(new CompiledTrauma(bodyPart, binding.checkBoxPenetrating.isChecked(), binding.checkBoxFracture.isChecked(),
                    binding.checkBoxContusion.isChecked(), binding.checkBoxWound.isChecked(), binding.checkBoxHaemorrhage.isChecked(),
                    binding.checkBoxBurn.isChecked(), binding.checkBoxPain.isChecked(), degree));
            dialog.dismiss();
        });
    }

    public interface TraumaDialogListener {
        void onTraumaDialogOk(CompiledTrauma compiledTrauma);
    }
}