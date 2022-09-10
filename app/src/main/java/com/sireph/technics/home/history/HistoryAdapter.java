package com.sireph.technics.home.history;

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
    private HistoryRecyclerViewAdapter.OnHistoryClickListener listener;

    public HistoryAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<ArrayList<Occurrence>> lists,
                          HistoryRecyclerViewAdapter.OnHistoryClickListener listener) {
        super(fragmentManager, lifecycle);
        this.lists = lists;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(HistoryFragment.ARG_OCCURRENCES, lists.get(position));
        args.putSerializable(HistoryFragment.ARG_LISTENER, this.listener);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return this.lists.size();
    }
}
