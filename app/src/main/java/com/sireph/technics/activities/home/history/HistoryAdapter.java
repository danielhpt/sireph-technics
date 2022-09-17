package com.sireph.technics.activities.home.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.sireph.technics.models.occurrence.Occurrence;
import com.sireph.technics.utils.statics.Args;

import java.io.Serializable;
import java.util.List;

public class HistoryAdapter extends FragmentStateAdapter implements Serializable {
    private final List<List<Occurrence>> lists;

    public HistoryAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<List<Occurrence>> lists) {
        super(fragmentManager, lifecycle);
        this.lists = lists;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(Args.ARG_OCCURRENCES, (Serializable) lists.get(position));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return this.lists.size();
    }
}
