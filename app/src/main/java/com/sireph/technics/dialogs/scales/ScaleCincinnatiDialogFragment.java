package com.sireph.technics.dialogs.scales;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sireph.technics.R;
import com.sireph.technics.databinding.DialogScaleCincinnatiBinding;

import java.io.Serializable;

public class ScaleCincinnatiDialogFragment extends DialogFragment {
    private final ScaleCincinnatiDialogListener listener;
    private final CincinnatiScale scale;
    private DialogScaleCincinnatiBinding binding;

    public ScaleCincinnatiDialogFragment(CincinnatiScale scale, ScaleCincinnatiDialogListener listener) {
        this.listener = listener;
        this.scale = scale;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        this.binding = DialogScaleCincinnatiBinding.inflate(requireActivity().getLayoutInflater());

        builder.setView(this.binding.getRoot())
                .setTitle(R.string.cincinnati_scale)
                .setPositiveButton(R.string.ok, (dialog, id) -> listener.onScaleCincinnatiDialogOk(
                        new CincinnatiScale(this.binding.checkBoxFacialParalysis.isChecked(), this.binding.checkBoxFallUpperLimb.isChecked(),
                                this.binding.checkBoxSpeechAlteration.isChecked())))
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                });
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (this.scale != null) {
            this.binding.checkBoxFacialParalysis.setChecked(this.scale.paralysis);
            this.binding.checkBoxFallUpperLimb.setChecked(this.scale.fall);
            this.binding.checkBoxSpeechAlteration.setChecked(this.scale.speech);
        }
    }

    public interface ScaleCincinnatiDialogListener {
        void onScaleCincinnatiDialogOk(CincinnatiScale scale);
    }

    public static class CincinnatiScale implements Serializable {
        boolean paralysis;
        boolean fall;
        boolean speech;
        int value;

        public CincinnatiScale(boolean paralysis, boolean fall, boolean speech) {
            this.paralysis = paralysis;
            this.fall = fall;
            this.speech = speech;
            calcValue();
        }

        private void calcValue() {
            this.value = 0;
            if (paralysis) value++;
            if (fall) value++;
            if (speech) value++;
        }

        public int getValue() {
            return value;
        }
    }
}
