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
import com.sireph.technics.models.Victim;

public class TransportDialogFragment extends DialogFragment {
    private final Victim victim;
    private final boolean isActive;
    TransportDialogListener listener;

    public TransportDialogFragment(Victim victim, boolean isActive, TransportDialogListener listener) {
        this.victim = victim;
        this.isActive = isActive;
        this.listener = listener;
    }

    @SuppressLint({"InflateParams", "DefaultLocale"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_transport, null);

        builder.setView(view)
                .setTitle(R.string.transport)
                .setPositiveButton(R.string.ok, (dialog, id) -> listener.onTransportDialogOk())
                .setNegativeButton(R.string.cancel, (dialog, id) -> listener.onTransportDialogCancel());
        return builder.create();
    }

    public interface TransportDialogListener {
        void onTransportDialogOk();

        void onTransportDialogCancel();
    }
}
