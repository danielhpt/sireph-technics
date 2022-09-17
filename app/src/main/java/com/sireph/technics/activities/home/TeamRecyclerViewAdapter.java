package com.sireph.technics.activities.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sireph.technics.databinding.FragmentTechnicianBinding;
import com.sireph.technics.models.Technician;

import java.util.List;

public class TeamRecyclerViewAdapter extends RecyclerView.Adapter<TeamRecyclerViewAdapter.ViewHolder> {
    private final List<Technician> technicians;
    private final boolean canRemove;
    TeamRecyclerViewAdapterListener listener;

    public TeamRecyclerViewAdapter(List<Technician> technicians, boolean canRemove, TeamRecyclerViewAdapterListener listener) {
        this.technicians = technicians;
        this.canRemove = canRemove;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentTechnicianBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Technician technician = this.technicians.get(position);
        holder.teamLeader.setVisibility(technician.getTeam_leader() ? View.VISIBLE : View.GONE);
        holder.technicianName.setText(technician.getUser().getFullName());
        if (this.canRemove && !technician.getTeam_leader()) {
            holder.remTechnician.setVisibility(View.VISIBLE);
            holder.remTechnician.setOnClickListener(v -> listener.onTeamRemoveTechnician(position));
        } else {
            holder.remTechnician.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return this.technicians.size();
    }

    public interface TeamRecyclerViewAdapterListener {
        void onTeamRemoveTechnician(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView technicianName;
        public final TextView teamLeader;
        public final Button remTechnician;

        public ViewHolder(FragmentTechnicianBinding binding) {
            super(binding.getRoot());
            technicianName = binding.content;
            teamLeader = binding.imageTeamLeader;
            remTechnician = binding.buttonRemUser;
        }
    }
}
