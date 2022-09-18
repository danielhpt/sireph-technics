package com.sireph.technics.dialogs.scales;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sireph.technics.R;
import com.sireph.technics.databinding.DialogScaleProacsBinding;
import com.sireph.technics.utils.TextChangedWatcher;
import com.sireph.technics.utils.Validation;

import java.io.Serializable;

public class ScalePROACSDialog extends DialogFragment {
    private final ScalePROACSDialogListener listener;
    private final PROACSScale scale;
    private DialogScaleProacsBinding binding;

    public ScalePROACSDialog(PROACSScale scale, ScalePROACSDialogListener listener) {
        this.listener = listener;
        this.scale = scale;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        this.binding = DialogScaleProacsBinding.inflate(requireActivity().getLayoutInflater());

        this.binding.editKillip.addTextChangedListener(new TextChangedWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Validation.validateInt(s.toString(), 1, 4, false)) {
                    binding.editKillip.setError(getString(R.string.required_field));
                } else {
                    binding.editKillip.setError(null);
                }
            }
        });

        builder.setView(this.binding.getRoot())
                .setTitle(R.string.proacs_scale)
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
            this.binding.checkBoxProacsAge.setChecked(this.scale.age);
            this.binding.checkBoxProacsTas.setChecked(this.scale.tas);
            this.binding.checkBoxProacsST.setChecked(this.scale.st);
            this.binding.editKillip.setText(Integer.toString(this.scale.killip));
        }

        assert dialog != null;
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String killip = this.binding.editKillip.getText().toString();
            if (!Validation.validateInt(killip, 1, 4, false)) {
                this.listener.onScalePROACSDialogOk(new PROACSScale(this.binding.checkBoxProacsAge.isChecked(),
                        this.binding.checkBoxProacsTas.isChecked(), this.binding.checkBoxProacsST.isChecked(), Integer.parseInt(killip)));
                dialog.dismiss();
            } else {
                this.binding.editKillip.setError(getString(R.string.required_field));
            }
        });
    }

    public interface ScalePROACSDialogListener {
        void onScalePROACSDialogOk(PROACSScale scale);
    }

    public static class PROACSScale implements Serializable {
        boolean age;
        boolean tas;
        boolean st;
        int killip;
        int value;

        public PROACSScale(boolean age, boolean tas, boolean st, int killip) {
            this.age = age;
            this.tas = tas;
            this.st = st;
            this.killip = killip;
            calcValue();
        }

        private void calcValue() {
            this.value = 0;
            switch (killip) {
                case 2:
                case 3:
                    value++;
                    break;
                case 4:
                    value += 3;
            }
            if (age) value++;
            if (tas) value++;
            if (st) value++;
        }

        public int getValue() {
            return value;
        }
    }
}
