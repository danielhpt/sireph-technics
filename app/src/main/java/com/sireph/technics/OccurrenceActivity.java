package com.sireph.technics;

import static com.sireph.technics.utils.EditTextString.editTextString;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sireph.technics.dialogs.StateDialogFragment;
import com.sireph.technics.models.Hospital;
import com.sireph.technics.models.Occurrence;
import com.sireph.technics.models.OccurrenceState;
import com.sireph.technics.models.Technician;
import com.sireph.technics.models.Victim;
import com.sireph.technics.models.enums.State;
import com.sireph.technics.occurrence.StateRecyclerViewAdapter;
import com.sireph.technics.occurrence.VictimRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OccurrenceActivity extends AppCompatActivity implements StateDialogFragment.StateDialogListener,
        StateRecyclerViewAdapter.OnStateClickListener, VictimRecyclerViewAdapter.OnVictimClickListener {
    public static String ARG_TOKEN = "1", ARG_TECHNICIAN = "2", ARG_OCCURRENCE = "3", ARG_ACTIVE = "4", ARG_HOSPITALS = "5";
    private String token;
    private Technician technician;
    private Occurrence occurrence;
    private boolean isActive;
    private TextView victimNumber;
    private RecyclerView stateList, victimList;
    private ArrayList<Hospital> hospitals;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occurrence);

        Intent intent = getIntent();
        this.token = intent.getStringExtra(ARG_TOKEN);
        this.technician = (Technician) intent.getSerializableExtra(ARG_TECHNICIAN);
        this.occurrence = (Occurrence) intent.getSerializableExtra(ARG_OCCURRENCE);
        this.isActive = intent.getBooleanExtra(ARG_ACTIVE, false);
        //noinspection unchecked
        this.hospitals = (ArrayList<Hospital>) intent.getSerializableExtra(ARG_HOSPITALS);

        TextView title = findViewById(R.id.titleOccurrence);
        title.setText(getString(R.string.occurrence) + " #" + this.occurrence.getOccurrence_number());

        EditText motive = findViewById(R.id.occurrenceMotive), entity = findViewById(R.id.occurrenceEntity), mean = findViewById(R.id.occurrenceMean),
                local = findViewById(R.id.occurrenceLocal), municipality = findViewById(R.id.occurrenceMunicipality),
                parish = findViewById(R.id.occurrenceParish);
        editTextString(motive, this.occurrence.getMotive(), this.isActive);
        editTextString(entity, this.occurrence.getEntity(), this.isActive);
        editTextString(mean, this.occurrence.getMean_of_assistance(), this.isActive);
        editTextString(local, this.occurrence.getLocal(), this.isActive);
        editTextString(municipality, this.occurrence.getMunicipality(), this.isActive);
        editTextString(parish, this.occurrence.getParish(), this.isActive);

        this.victimNumber = findViewById(R.id.occurrenceVictimsNumber);
        setVictimNumber();

        this.stateList = findViewById(R.id.stateList);
        this.stateList.setLayoutManager(new LinearLayoutManager(this));
        this.stateList.setAdapter(new StateRecyclerViewAdapter(this.occurrence.getStates(), this.isActive, this));

        this.victimList = findViewById(R.id.victimList);
        this.victimList.setLayoutManager(new LinearLayoutManager(this));
        this.victimList.setAdapter(new VictimRecyclerViewAdapter(this.occurrence.getVictims(), this.isActive, this));
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onStateClick() {
        List<OccurrenceState> states = this.occurrence.getStates();
        State state;
        if (states.isEmpty()) {
            state = State.WAY_VICTIM;
        } else {
            state = State.fromId(states.get(states.size() - 1).getState().getId() + 1);
        }
        StateDialogFragment dialog = new StateDialogFragment(state, this);
        dialog.show(getSupportFragmentManager(), "StatusDialogFragment");
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onStateDialogOk(OccurrenceState state) {
        this.occurrence.addState(state);
        Objects.requireNonNull(this.stateList.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onStateDialogCancel() {
    }

    @Override
    public void onAddVictimClick() {
        openVictim(new Victim());
    }

    @Override
    public void onVictimClick(Victim victim) {
        openVictim(victim);
    }

    private void openVictim(Victim victim) {
        Intent intent = new Intent(this, VictimActivity.class);
        intent.putExtra(VictimActivity.ARG_TOKEN, this.token);
        intent.putExtra(VictimActivity.ARG_TECHNICIAN, this.technician);
        intent.putExtra(VictimActivity.ARG_VICTIM, victim);
        intent.putExtra(VictimActivity.ARG_ACTIVE, this.isActive);
        intent.putExtra(VictimActivity.ARG_HOSPITALS, this.hospitals);
        startActivity(intent);
    }
}