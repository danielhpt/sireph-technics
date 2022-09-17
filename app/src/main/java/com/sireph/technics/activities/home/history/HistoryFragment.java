package com.sireph.technics.activities.home.history;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sireph.technics.R;
import com.sireph.technics.models.occurrence.Occurrence;
import com.sireph.technics.utils.statics.Args;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    private ArrayList<Occurrence> occurrences = new ArrayList<>();

    public HistoryFragment() {
    }

    @SuppressWarnings("unused")
    public static HistoryFragment newInstance(ArrayList<Occurrence> occurrences) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(Args.ARG_OCCURRENCES, occurrences);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //noinspection unchecked
            this.occurrences = (ArrayList<Occurrence>) getArguments().getSerializable(Args.ARG_OCCURRENCES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_occurrence_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new HistoryRecyclerViewAdapter(this.occurrences));
        }
        return view;
    }
}