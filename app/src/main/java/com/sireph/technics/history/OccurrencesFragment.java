package com.sireph.technics.history;

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

public class OccurrencesFragment extends Fragment {
    public static final String ARG_OCCURRENCES = "occurrences";
    private ArrayList<Occurrence> occurrences = new ArrayList<>();

    public OccurrencesFragment() {
    }

    @SuppressWarnings("unused")
    public static OccurrencesFragment newInstance(ArrayList<Occurrence> occurrences) {
        OccurrencesFragment fragment = new OccurrencesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_OCCURRENCES, occurrences);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //noinspection unchecked
            this.occurrences = (ArrayList<Occurrence>) getArguments().getSerializable(ARG_OCCURRENCES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_occurrence_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new OccurrencesRecyclerViewAdapter(this.occurrences));
        }
        return view;
    }
}