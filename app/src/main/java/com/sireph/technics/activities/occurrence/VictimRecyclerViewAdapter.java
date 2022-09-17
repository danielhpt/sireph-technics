package com.sireph.technics.activities.occurrence;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sireph.technics.R;
import com.sireph.technics.databinding.FragmentVictimBinding;
import com.sireph.technics.models.enums.Gender;
import com.sireph.technics.models.victim.Victim;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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
            String s;
            if (victim.getName() != null && !Objects.equals(victim.getName(), "")) {
                s = victim.getName();
            } else {
                s = holder.itemView.getContext().getString(R.string.victim) + " " + n;
            }
            if (victim.getGender() != Gender.EMPTY || victim.getAge() != null) {
                s += " (";
                if (victim.getGender() != Gender.EMPTY) {
                    s += victim.getGender();
                }
                if (victim.getGender() != Gender.EMPTY && victim.getAge() != null) {
                    s += " - ";
                }
                if (victim.getAge() != null) {
                    s += victim.getAge();
                }
                s += ")";
            }
            holder.victimName.setText(s);
            String finalS = s;
            holder.itemView.setOnClickListener(v -> listener.onVictimClick(victim, victim.getName() != null && !Objects.equals(victim.getName(), "") ? null : finalS));
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

        void onVictimClick(Victim victim, String tempName);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView victimName;

        public ViewHolder(FragmentVictimBinding binding) {
            super(binding.getRoot());
            victimName = binding.content;
        }
    }
}
