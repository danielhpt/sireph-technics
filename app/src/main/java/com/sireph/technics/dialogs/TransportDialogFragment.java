package com.sireph.technics.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sireph.technics.R;

public class TransportDialogFragment extends DialogFragment {
    public interface TransportDialogListener {
        void showTransportDialog();
        void onTransportDialogOk(DialogFragment dialog);
        void onTransportDialogCancel(DialogFragment dialog);
    }

    TransportDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (TransportDialogListener) context;
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
                .setPositiveButton(R.string.ok, (dialog, id) -> listener.onTransportDialogOk(TransportDialogFragment.this))
                .setNegativeButton(R.string.cancel, (dialog, id) -> listener.onTransportDialogCancel(TransportDialogFragment.this));
        return builder.create();
    }
}
