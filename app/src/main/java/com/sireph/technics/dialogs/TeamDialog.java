package com.sireph.technics.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sireph.technics.R;
import com.sireph.technics.activities.home.TeamRecyclerViewAdapter;
import com.sireph.technics.models.Team;
import com.sireph.technics.models.Technician;
import com.sireph.technics.utils.TextChangedWatcher;

import java.util.List;
import java.util.Objects;

public class TeamDialog extends DialogFragment {
    private final List<Technician> technicians;
    private final List<Technician> allTechnicians;
    TeamDialogFragmentListener listener;

    public TeamDialog(List<Technician> technicians, List<Technician> allTechnicians, TeamDialogFragmentListener listener) {
        this.technicians = technicians;
        this.allTechnicians = allTechnicians;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_create_team, null);

        ArrayAdapter<Technician> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.select_dialog_item, this.allTechnicians);
        AutoCompleteTextView addUsername = view.findViewById(R.id.textAddUser);
        addUsername.setThreshold(1);
        addUsername.setAdapter(adapter);
        addUsername.addTextChangedListener(new TextChangedWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > addUsername.getThreshold() && !addUsername.isPerformingCompletion() && !addUsername.isPopupShowing()) {
                    addUsername.setError(getString(R.string.invalid_username));
                } else {
                    addUsername.setError(null);
                }
            }
        });

        RecyclerView newTeamList = view.findViewById(R.id.newTeamList);
        newTeamList.setLayoutManager(new LinearLayoutManager(getContext()));
        newTeamList.setAdapter(new TeamRecyclerViewAdapter(this.technicians, true, position -> {
            adapter.add(technicians.get(position));
            technicians.remove(position);
            Objects.requireNonNull(newTeamList.getAdapter()).notifyDataSetChanged();
        }));

        Button addUser = view.findViewById(R.id.buttonAddUser);
        addUser.setOnClickListener(v -> {
            String username = addUsername.getText().toString().toLowerCase();
            boolean find = false;
            for (Technician t : allTechnicians) {
                if (t.toString().toLowerCase().equals(username)) {
                    technicians.add(1, t);
                    adapter.remove(t);
                    t.setTeam_leader(false);
                    Objects.requireNonNull(newTeamList.getAdapter()).notifyDataSetChanged();
                    find = true;
                    break;
                }
            }
            if (!find) {
                Toast.makeText(view.getContext(), getString(R.string.technician_not_found), Toast.LENGTH_SHORT).show();
            }
            addUsername.setText("");
        });

        builder.setView(view)
                .setTitle(R.string.create_team)
                .setPositiveButton(R.string.ok, (dialog, id) -> listener.onTeamCreated(new Team(technicians.get(0).getCentral(), technicians)))
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                });
        return builder.create();
    }

    public interface TeamDialogFragmentListener {
        void onTeamCreated(Team team);
    }
}
