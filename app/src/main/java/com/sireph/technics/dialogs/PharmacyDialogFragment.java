package com.sireph.technics.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sireph.technics.R;
import com.sireph.technics.databinding.DialogAddPharmacyBinding;
import com.sireph.technics.models.date.DateTime;
import com.sireph.technics.models.procedures.Pharmacy;
import com.sireph.technics.utils.DateTimeInput;
import com.sireph.technics.utils.TextChangedWatcher;
import com.sireph.technics.utils.Validation;

public class PharmacyDialogFragment extends DialogFragment {
    private final PharmacyDialogListener listener;
    private DialogAddPharmacyBinding binding;
    private EditText time;

    public PharmacyDialogFragment(PharmacyDialogListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        this.binding = DialogAddPharmacyBinding.inflate(requireActivity().getLayoutInflater());

        this.time = DateTimeInput.setupTimeInput(this.binding.includedPharmacyTime.getRoot(), getContext(), true, true, null, false);
        this.binding.editTextPharmacyName.addTextChangedListener(new TextChangedWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    binding.editTextPharmacyName.setError(getString(R.string.required_field));
                } else {
                    binding.editTextPharmacyName.setError(null);
                }
            }
        });
        this.binding.editTextPharmacyVia.addTextChangedListener(new TextChangedWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    binding.editTextPharmacyVia.setError(getString(R.string.required_field));
                } else {
                    binding.editTextPharmacyVia.setError(null);
                }
            }
        });
        this.binding.editTextPharmacyDose.addTextChangedListener(new TextChangedWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    binding.editTextPharmacyDose.setError(getString(R.string.required_field));
                } else {
                    binding.editTextPharmacyDose.setError(null);
                }
            }
        });

        builder.setView(this.binding.getRoot())
                .setTitle(R.string.add_pharmacy)
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
            String timeText = time.getText().toString();
            boolean valid = Validation.validateTime(timeText, false);
            String name = binding.editTextPharmacyName.getText().toString().trim();
            String dose = binding.editTextPharmacyDose.getText().toString().trim();
            String via = binding.editTextPharmacyVia.getText().toString().trim();
            String adv = binding.editTextPharmacyAdverse.getText().toString().trim();
            if (valid && !name.equals("") && !dose.equals("") && !via.equals("")) {
                DateTime dateTime = DateTime.now();
                dateTime.setTime(timeText);
                listener.onPharmacyDialogOk(new Pharmacy(dateTime, name, dose, via, adv.equals("") ? null : adv));
                dialog.dismiss();
            } else {
                if (!valid) {
                    time.setError(getString(R.string.invalid_time));
                }
                if (name.equals("")) {
                    binding.editTextPharmacyName.setError(getString(R.string.required_field));
                }
                if (dose.equals("")) {
                    binding.editTextPharmacyDose.setError(getString(R.string.required_field));
                }
                if (via.equals("")) {
                    binding.editTextPharmacyVia.setError(getString(R.string.required_field));
                }
            }
        });
    }

    public interface PharmacyDialogListener {
        void onPharmacyDialogOk(Pharmacy pharmacy);
    }
}
