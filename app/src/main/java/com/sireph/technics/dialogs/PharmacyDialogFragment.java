package com.sireph.technics.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sireph.technics.R;
import com.sireph.technics.models.procedures.Pharmacy;

public class PharmacyDialogFragment extends DialogFragment {
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_add_pharmacy, null);

        builder.setView(view)
                .setTitle(R.string.add_pharmacy)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                });
        return builder.create();
    }

    public interface PharmacyDialogListener {
        void onPharmacyDialogOk(Pharmacy pharmacy);
    }
}
