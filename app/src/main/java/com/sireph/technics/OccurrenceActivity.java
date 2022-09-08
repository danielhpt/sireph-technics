package com.sireph.technics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.sireph.technics.dialogs.StateDialogFragment;
import com.sireph.technics.home.TeamRecyclerViewAdapter;
import com.sireph.technics.models.Occurrence;
import com.sireph.technics.models.OccurrenceState;
import com.sireph.technics.models.Technician;
import com.sireph.technics.occurrence.StateRecyclerViewAdapter;
import com.sireph.technics.occurrence.VictimRecyclerViewAdapter;

import java.util.Objects;

public class OccurrenceActivity extends AppCompatActivity implements StateDialogFragment.StateDialogListener,
        StateRecyclerViewAdapter.OnStateClickListener, VictimRecyclerViewAdapter.OnVictimClickListener {
    public static String ARG_TOKEN = "1", ARG_TECHNICIAN = "2", ARG_OCCURRENCE = "3", ARG_ACTIVE = "4";
    private String token;
    private Technician technician;
    private Occurrence occurrence;
    private boolean isActive;
    private TextView victimNumber;
    private RecyclerView stateList, victimList;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occurrence);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        this.token = intent.getStringExtra(ARG_TOKEN);
        this.technician = (Technician) intent.getSerializableExtra(ARG_TECHNICIAN);
        this.occurrence = (Occurrence) intent.getSerializableExtra(ARG_OCCURRENCE);
        this.isActive = intent.getBooleanExtra(ARG_ACTIVE, false);

        TextView title = findViewById(R.id.titleOccurrence);
        title.setText(getString(R.string.occurrence) + " #" + this.occurrence.getOccurrence_number());

        EditText motive = findViewById(R.id.occurrenceMotive), entity = findViewById(R.id.occurrenceEntity), mean = findViewById(R.id.occurrenceMean),
                local = findViewById(R.id.occurrenceLocal), municipality = findViewById(R.id.occurrenceMunicipality),
                parish = findViewById(R.id.occurrenceParish);
        editTextSting(motive, this.occurrence.getMotive());
        editTextSting(entity, this.occurrence.getEntity());
        editTextSting(mean, this.occurrence.getMean_of_assistance());
        editTextSting(local, this.occurrence.getLocal());
        editTextSting(municipality, this.occurrence.getMunicipality());
        editTextSting(parish, this.occurrence.getParish());

        this.victimNumber = findViewById(R.id.occurrenceVictimsNumber);
        setVictimNumber();

        this.stateList = findViewById(R.id.stateList);
        this.stateList.setLayoutManager(new LinearLayoutManager(this));
        this.stateList.setAdapter(new StateRecyclerViewAdapter(this.occurrence.getStates(), this.isActive, this));

        this.victimList = findViewById(R.id.victimList);
        this.victimList.setLayoutManager(new LinearLayoutManager(this));
        this.victimList.setAdapter(new VictimRecyclerViewAdapter(this.occurrence.getVictims(), this.isActive, this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.findItem(R.id.menuUsername).setTitle(
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                        .getString(getString(R.string.sharedPref_key_username), getString(R.string.username)));
        return true;
    }

    private void editTextSting(EditText text, String s) {
        if (!Objects.equals(s, "")) {
            text.setText(s);
            text.setEnabled(false);
        }
        if (!this.isActive){
            text.setEnabled(false);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setVictimNumber() {
        int n = this.occurrence.getVictims().size();
        this.victimNumber.setText(getString(R.string.victims) + " (" + n + ")");
        if (n != this.occurrence.getNumber_of_victims()) {
            this.occurrence.setNumber_of_victims(n);
        }
    }

    @Override
    public void onStateClick() {
        StateDialogFragment dialog = new StateDialogFragment();
        dialog.show(getSupportFragmentManager(), "StatusDialogFragment");
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onStateDialogOk(DialogFragment dialog, OccurrenceState state) {
        this.occurrence.addState(state);
        Objects.requireNonNull(this.stateList.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onStateDialogCancel(DialogFragment dialog) {}

    @Override
    public void onVictimClick() {
        // todo
    }
}