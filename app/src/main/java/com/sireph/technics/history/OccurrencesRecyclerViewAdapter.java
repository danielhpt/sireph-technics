package com.sireph.technics.history;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sireph.technics.databinding.FragmentOccurrenceBinding;
import com.sireph.technics.models.Occurrence;

import java.util.List;

public class OccurrencesRecyclerViewAdapter extends RecyclerView.Adapter<OccurrencesRecyclerViewAdapter.ViewHolder> {
    private final List<Occurrence> occurrences;

    public OccurrencesRecyclerViewAdapter(List<Occurrence> items) {
        occurrences = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentOccurrenceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Occurrence occurrence = this.occurrences.get(position);
        holder.occurrence = occurrence;
        holder.occurrenceNumber.setText(String.valueOf(occurrence.getOccurrence_number()));
        if (occurrence.getCreated_on() == null) {
            holder.occurrenceDate.setText("--/--/----");
        } else {
            holder.occurrenceDate.setText(occurrence.getCreated_on().format("dd/MM/yyyy"));
        }
    }

    @Override
    public int getItemCount() {
        return occurrences.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView occurrenceNumber;
        public final TextView occurrenceDate;
        public Occurrence occurrence;

        public ViewHolder(FragmentOccurrenceBinding binding) {
            super(binding.getRoot());
            occurrenceNumber = binding.itemNumber;
            occurrenceDate = binding.content;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + occurrenceDate.getText() + "'";
        }
    }
}