package com.sireph.technics.activities.occurrence;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sireph.technics.R;
import com.sireph.technics.databinding.FragmentVictimBinding;
import com.sireph.technics.models.victim.Victim;
import com.sireph.technics.utils.VictimTitle;

import java.io.Serializable;
import java.util.List;

public class VictimRecyclerViewAdapter extends RecyclerView.Adapter<VictimRecyclerViewAdapter.ViewHolder> {
    private final List<Victim> victims;
    private final boolean isActive;
    OnVictimClickListener listener;

    public VictimRecyclerViewAdapter(List<Victim> victims, boolean isActive, OnVictimClickListener listener) {
        this.victims = victims;
        this.isActive = isActive;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VictimRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VictimRecyclerViewAdapter.ViewHolder(FragmentVictimBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VictimRecyclerViewAdapter.ViewHolder holder, int position) {
        if (position != this.victims.size()) {
            Victim victim = this.victims.get(position);
            int n = position + 1;

            String s = VictimTitle.createTitle(victim, n, holder.itemView.getContext());

            holder.victimName.setText(s);
            holder.itemView.setOnClickListener(v -> listener.onVictimClick(victim, n));
        } else {
            holder.victimName.setText(R.string.add_victim);
            holder.itemView.setOnClickListener(v -> listener.onAddVictimClick());
        }
    }

    @Override
    public int getItemCount() {
        if (this.isActive) {
            return this.victims.size() + 1;
        }
        return this.victims.size();
    }

    public interface OnVictimClickListener extends Serializable {
        void onAddVictimClick();

        void onVictimClick(Victim victim, int n);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView victimName;

        public ViewHolder(FragmentVictimBinding binding) {
            super(binding.getRoot());
            victimName = binding.content;
        }
    }
}
