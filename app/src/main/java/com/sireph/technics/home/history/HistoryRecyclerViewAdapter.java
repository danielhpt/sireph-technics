package com.sireph.technics.home.history;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sireph.technics.databinding.FragmentOccurrenceBinding;
import com.sireph.technics.models.Occurrence;

import java.io.Serializable;
import java.util.List;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {
    private final List<Occurrence> occurrences;
    OnHistoryClickListener listener;

    public HistoryRecyclerViewAdapter(List<Occurrence> occurrences, OnHistoryClickListener listener) {
        this.occurrences = occurrences;
        this.listener = listener;
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
        holder.itemView.setOnClickListener(v -> listener.onHistoryClick(occurrence));
    }

    @Override
    public int getItemCount() {
        if (occurrences == null) {
            return 0;
        }
        return occurrences.size();
    }

    public interface OnHistoryClickListener extends Serializable {
        void onHistoryClick(Occurrence occurrence);
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