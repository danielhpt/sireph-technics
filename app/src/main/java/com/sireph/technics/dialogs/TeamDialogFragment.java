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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sireph.technics.R;
import com.sireph.technics.home.TeamRecyclerViewAdapter;
import com.sireph.technics.models.Technician;

import java.util.ArrayList;

public class TeamDialogFragment extends DialogFragment {
    private final ArrayList<Technician> technicians;

    public TeamDialogFragment(ArrayList<Technician> technicians) {
        this.technicians = technicians;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_create_team, null);

        RecyclerView newTeamList = view.findViewById(R.id.newTeamList);
        newTeamList.setLayoutManager(new LinearLayoutManager(getContext()));
        newTeamList.setAdapter(new TeamRecyclerViewAdapter(this.technicians, true));

        builder.setView(view)
                .setTitle(R.string.create_team)
                .setPositiveButton(R.string.ok, (dialog, id) -> { })
                .setNegativeButton(R.string.cancel, (dialog, id) -> { });
        return builder.create();
    }
}
