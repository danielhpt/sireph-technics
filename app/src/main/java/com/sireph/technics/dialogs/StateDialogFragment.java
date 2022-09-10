package com.sireph.technics.dialogs;

import static com.sireph.technics.utils.DateTimeInput.setupTimeInput;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sireph.technics.R;
import com.sireph.technics.models.OccurrenceState;
import com.sireph.technics.models.date.DateTime;
import com.sireph.technics.models.enums.State;
import com.sireph.technics.utils.GPS;

public class StateDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {
    StateDialogListener listener;
    private State state;
    private Context context;

    public StateDialogFragment(State state, StateDialogListener listener) {
        this.state = state;
        this.listener = listener;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @SuppressLint({"InflateParams", "DefaultLocale"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_add_state, null);
        Spinner spinner = view.findViewById(R.id.stateSpinner);
        ArrayAdapter<State> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, State.values());
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getPosition(this.state));
        spinner.setOnItemSelectedListener(this);

        EditText time = setupTimeInput(view.findViewById(R.id.includedStateTime), context, true, true, null, false);

        builder.setView(view)
                .setTitle(R.string.add_status)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    DateTime dateTime = DateTime.now();
                    dateTime.setTime(time.getText().toString());

                    Location location = new GPS(this.context).getLocation();
                    OccurrenceState state = new OccurrenceState(this.state, location, dateTime);

                    this.listener.onStateDialogOk(state);
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> this.listener.onStateDialogCancel());
        return builder.create();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.state = (State) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public interface StateDialogListener {
        void onStateDialogOk(OccurrenceState state);

        void onStateDialogCancel();
    }
}
