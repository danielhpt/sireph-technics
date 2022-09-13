package com.sireph.technics.home.history;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sireph.technics.OccurrenceActivity;
import com.sireph.technics.databinding.FragmentOccurrenceBinding;
import com.sireph.technics.models.Occurrence;
import com.sireph.technics.utils.statics.Args;

import java.io.Serializable;
import java.util.ArrayList;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> implements Serializable {
    private final ArrayList<Occurrence> occurrences;

    public HistoryRecyclerViewAdapter(ArrayList<Occurrence> occurrences) {
        this.occurrences = occurrences;
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
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, OccurrenceActivity.class);
            intent.putExtra(Args.ARG_OCCURRENCE, occurrence);
            intent.putExtra(Args.ARG_ACTIVE, false);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (occurrences == null) {
            return 0;
        }
        return occurrences.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements Serializable {
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