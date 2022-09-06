package com.sireph.technics.dialogs.scales.race;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sireph.technics.R;

public class ScaleRaceDialogFragment extends DialogFragment {
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_scale_race, null);

        ScaleRaceAdapter raceAdapter = new ScaleRaceAdapter(getParentFragmentManager(), getLifecycle());
        ViewPager2 raceViewPager = view.findViewById(R.id.raceViewPager);
        raceViewPager.setAdapter(raceAdapter);

        TabLayout raceTabs = view.findViewById(R.id.raceTabs);
        new TabLayoutMediator(raceTabs, raceViewPager, ((tab, position) -> {
            if (position == 0) {
                tab.setText(R.string.race_left);
            } else {
                tab.setText(R.string.race_right);
            }
        })).attach();

        builder.setView(view)
                .setTitle(R.string.race_scale)
                .setPositiveButton(R.string.ok, (dialog, id) -> { })
                .setNegativeButton(R.string.cancel, (dialog, id) -> { });
        return builder.create();
    }
}
