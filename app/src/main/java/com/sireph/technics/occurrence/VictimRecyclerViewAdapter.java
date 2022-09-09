package com.sireph.technics.occurrence;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sireph.technics.R;
import com.sireph.technics.databinding.FragmentVictimBinding;
import com.sireph.technics.models.Victim;

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
            holder.victimName.setText(victim.getName() + " (" + victim.getGender() + " - " + victim.getAge() + ")");
            holder.itemView.setOnClickListener(v -> {
                listener.onVictimClick(victim);
            });
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
        void onVictimClick(Victim victim);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView victimNumber;
        public final TextView victimName;

        public ViewHolder(FragmentVictimBinding binding) {
            super(binding.getRoot());
            victimNumber = binding.itemNumber;
            victimName = binding.content;
        }
    }
}
