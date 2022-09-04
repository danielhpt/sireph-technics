package com.sireph.technics.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.sireph.technics.models.Occurrence;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends FragmentStateAdapter {
    private final List<ArrayList<Occurrence>> lists;

    public HistoryAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<ArrayList<Occurrence>> lists) {
        super(fragmentManager, lifecycle);
        this.lists = lists;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new OccurrencesFragment();
        Bundle args = new Bundle();
        args.putSerializable(OccurrencesFragment.ARG_OCCURRENCES, lists.get(position));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return this.lists.size();
    }
}
