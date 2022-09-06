package com.sireph.technics.home.history;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sireph.technics.R;
import com.sireph.technics.models.Occurrence;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    public static final String ARG_OCCURRENCES = "occurrences";
    public static final String ARG_LISTENER = "listener";
    private ArrayList<Occurrence> occurrences = new ArrayList<>();
    private HistoryRecyclerViewAdapter.OnHistoryClickListener listener;

    public HistoryFragment() {
    }

    @SuppressWarnings("unused")
    public static HistoryFragment newInstance(ArrayList<Occurrence> occurrences, HistoryRecyclerViewAdapter.OnHistoryClickListener listener) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_OCCURRENCES, occurrences);
        args.putSerializable(ARG_LISTENER, listener);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //noinspection unchecked
            this.occurrences = (ArrayList<Occurrence>) getArguments().getSerializable(ARG_OCCURRENCES);
            this.listener = (HistoryRecyclerViewAdapter.OnHistoryClickListener) getArguments().getSerializable(ARG_LISTENER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_occurrence_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new HistoryRecyclerViewAdapter(this.occurrences, this.listener));
        }
        return view;
    }
}