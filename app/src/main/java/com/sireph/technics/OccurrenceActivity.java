package com.sireph.technics;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sireph.technics.databinding.ActivityOccurrenceBinding;
import com.sireph.technics.dialogs.StateDialogFragment;
import com.sireph.technics.models.Hospital;
import com.sireph.technics.models.Occurrence;
import com.sireph.technics.models.OccurrenceState;
import com.sireph.technics.models.Technician;
import com.sireph.technics.models.Victim;
import com.sireph.technics.models.enums.State;
import com.sireph.technics.occurrence.StateRecyclerViewAdapter;
import com.sireph.technics.occurrence.VictimRecyclerViewAdapter;
import com.sireph.technics.utils.statics.Args;
import com.sireph.technics.utils.EditTextString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OccurrenceActivity extends AppCompatActivity implements StateDialogFragment.StateDialogListener,
        StateRecyclerViewAdapter.OnStateClickListener, VictimRecyclerViewAdapter.OnVictimClickListener {
    private String token;
    private Technician technician;
    private Occurrence occurrence;
    private boolean isActive;
    private ArrayList<Hospital> hospitals;
    private ActivityOccurrenceBinding binding;
    private final ActivityResultLauncher<Intent> startVictim = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (this.isActive && result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    // todo
                }
            });

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityOccurrenceBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        Intent intent = getIntent();
        this.token = intent.getStringExtra(Args.ARG_TOKEN);
        this.technician = (Technician) intent.getSerializableExtra(Args.ARG_TECHNICIAN);
        this.occurrence = (Occurrence) intent.getSerializableExtra(Args.ARG_OCCURRENCE);
        this.isActive = intent.getBooleanExtra(Args.ARG_ACTIVE, false);
        //noinspection unchecked
        this.hospitals = (ArrayList<Hospital>) intent.getSerializableExtra(Args.ARG_HOSPITALS);

        this.binding.occurrenceTitle.setText(getString(R.string.occurrence) + " #" + this.occurrence.getOccurrence_number());

        EditTextString.editTextString(this.binding.occurrenceMotive, this.occurrence.getMotive(), this.isActive);
        EditTextString.editTextString(this.binding.occurrenceEntity, this.occurrence.getEntity(), this.isActive);
        EditTextString.editTextString(this.binding.occurrenceMean, this.occurrence.getMean_of_assistance(), this.isActive);
        EditTextString.editTextString(this.binding.occurrenceLocal, this.occurrence.getLocal(), this.isActive);
        EditTextString.editTextString(this.binding.occurrenceMunicipality, this.occurrence.getMunicipality(), this.isActive);
        EditTextString.editTextString(this.binding.occurrenceParish, this.occurrence.getParish(), this.isActive);

        setVictimNumber();

        this.binding.stateList.setLayoutManager(new LinearLayoutManager(this));
        this.binding.stateList.setAdapter(new StateRecyclerViewAdapter(this.occurrence.getStates(), this.isActive, this));

        this.binding.victimList.setLayoutManager(new LinearLayoutManager(this));
        this.binding.victimList.setAdapter(new VictimRecyclerViewAdapter(this.occurrence.getVictims(), this.isActive, this));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @SuppressLint("SetTextI18n")
    private void setVictimNumber() {
        int n = this.occurrence.getVictims().size();
        this.binding.occurrenceVictimsNumber.setText(getString(R.string.victims) + " (" + n + ")");
        if (n != this.occurrence.getNumber_of_victims()) {
            this.occurrence.setNumber_of_victims(n);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        if (this.isActive) {
            intent.putExtra(Args.ARG_OCCURRENCE, this.occurrence);
        }
        setResult(RESULT_OK, intent);
        finish();
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
        Objects.requireNonNull(this.binding.stateList.getAdapter()).notifyDataSetChanged();
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
        intent.putExtra(Args.ARG_TOKEN, this.token);
        intent.putExtra(Args.ARG_TECHNICIAN, this.technician);
        intent.putExtra(Args.ARG_VICTIM, victim);
        intent.putExtra(Args.ARG_ACTIVE, this.isActive);
        intent.putExtra(Args.ARG_HOSPITALS, this.hospitals);
        this.startVictim.launch(intent);
    }
}