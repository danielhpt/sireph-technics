package com.sireph.technics.dialogs;

import static com.sireph.technics.utils.DateTimeInput.setupTimeInput;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sireph.technics.R;
import com.sireph.technics.models.enums.State;

public class StatusDialogFragment extends DialogFragment {
    public interface StatusDialogListener {
        void showStatusDialog();
        void onStatusDialogOk(DialogFragment dialog);
        void onStatusDialogCancel(DialogFragment dialog);
    }

    StatusDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (StatusDialogListener) context;
    }

    @SuppressLint({"InflateParams", "DefaultLocale"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        Context context = getContext();

        View view = inflater.inflate(R.layout.dialog_status, null);
        Spinner spinner = view.findViewById(R.id.statusSpinner);
        ArrayAdapter<State> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, State.values());
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);

        EditText time = view.findViewById(R.id.statusTime);
        setupTimeInput(time, context);

        builder.setView(view)
                .setTitle(R.string.add_status)
                .setPositiveButton(R.string.ok, (dialog, id) -> listener.onStatusDialogOk(StatusDialogFragment.this))
                .setNegativeButton(R.string.cancel, (dialog, id) -> listener.onStatusDialogCancel(StatusDialogFragment.this));
        return builder.create();
    }
}
