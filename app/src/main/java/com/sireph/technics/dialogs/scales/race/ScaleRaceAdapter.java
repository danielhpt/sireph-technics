package com.sireph.technics.dialogs.scales.race;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ScaleRaceAdapter extends FragmentStateAdapter {
    public ScaleRaceAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new ScaleRaceFragment();
        Bundle args = new Bundle();
        args.putSerializable(ScaleRaceFragment.ARG_IS_LEFT, position == 0);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
