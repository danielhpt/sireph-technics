package com.sireph.technics.dialogs.scales.race;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sireph.technics.R;

public class ScaleRaceFragment extends Fragment {
    public static String ARG_IS_LEFT = "isLeft";
    private boolean isLeft;

    @SuppressWarnings("unused")
    public static ScaleRaceFragment newInstance(boolean isLeft) {
        ScaleRaceFragment fragment = new ScaleRaceFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_IS_LEFT, isLeft);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.isLeft = getArguments().getBoolean(ARG_IS_LEFT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_race, container, false);
        if (this.isLeft) {
            view.findViewById(R.id.linearLayoutLeft).setVisibility(View.VISIBLE);
            view.findViewById(R.id.linearLayoutRight).setVisibility(View.GONE);
            view.findViewById(R.id.linearLayoutAgnosia).setVisibility(View.VISIBLE);
            view.findViewById(R.id.linearLayoutAfasia).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.linearLayoutLeft).setVisibility(View.GONE);
            view.findViewById(R.id.linearLayoutRight).setVisibility(View.VISIBLE);
            view.findViewById(R.id.linearLayoutAgnosia).setVisibility(View.GONE);
            view.findViewById(R.id.linearLayoutAfasia).setVisibility(View.VISIBLE);
        }
        return view;
    }
}
