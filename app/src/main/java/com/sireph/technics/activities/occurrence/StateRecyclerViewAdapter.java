package com.sireph.technics.activities.occurrence;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sireph.technics.R;
import com.sireph.technics.databinding.FragmentStateBinding;
import com.sireph.technics.models.occurrence.OccurrenceState;

import java.io.Serializable;
import java.util.List;

public class StateRecyclerViewAdapter extends RecyclerView.Adapter<StateRecyclerViewAdapter.ViewHolder> {
    private final List<OccurrenceState> states;
    private final boolean isActive;
    OnStateClickListener listener;

    public StateRecyclerViewAdapter(List<OccurrenceState> states, boolean isActive, OnStateClickListener listener) {
        this.states = states;
        this.isActive = isActive;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentStateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position != this.states.size()) {
            OccurrenceState state = this.states.get(position);
            holder.stateName.setText(state.getState().toString() + " - " + state.getDate_time().format("HH:mm"));
        } else {
            holder.stateName.setText(R.string.add_status);
            holder.itemView.setOnClickListener(v -> listener.onStateClick());
        }
    }

    @Override
    public int getItemCount() {
        if (this.isActive) {
            return this.states.size() + 1;
        }
        return this.states.size();
    }

    public interface OnStateClickListener extends Serializable {
        void onStateClick();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView stateName;

        public ViewHolder(FragmentStateBinding binding) {
            super(binding.getRoot());
            stateName = binding.content;
        }
    }
}
