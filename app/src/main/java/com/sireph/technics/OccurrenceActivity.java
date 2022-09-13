package com.sireph.technics;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sireph.technics.async.post.AsyncPostOccurrenceState;
import com.sireph.technics.async.post.AsyncPostVictim;
import com.sireph.technics.async.post.AsyncPutVictim;
import com.sireph.technics.databinding.ActivityOccurrenceBinding;
import com.sireph.technics.dialogs.StateDialogFragment;
import com.sireph.technics.models.Hospital;
import com.sireph.technics.models.Occurrence;
import com.sireph.technics.models.OccurrenceState;
import com.sireph.technics.models.Victim;
import com.sireph.technics.models.enums.State;
import com.sireph.technics.occurrence.StateRecyclerViewAdapter;
import com.sireph.technics.occurrence.VictimRecyclerViewAdapter;
import com.sireph.technics.utils.EditTextString;
import com.sireph.technics.utils.statics.Args;
import com.sireph.technics.utils.statics.Flag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OccurrenceActivity extends AppCompatActivity implements StateDialogFragment.StateDialogListener,
        StateRecyclerViewAdapter.OnStateClickListener, VictimRecyclerViewAdapter.OnVictimClickListener {
    private String token, title;
    private Occurrence occurrence;
    private boolean isActive;
    @SuppressLint("NotifyDataSetChanged")
    private final ActivityResultLauncher<Intent> startVictim = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (this.isActive && result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    assert intent != null;
                    Victim v = (Victim) intent.getSerializableExtra(Args.ARG_VICTIM);
                    if (v.getId() != null) {
                        List<Flag> flags = new ArrayList<>();
                        for (Victim old : occurrence.getVictims()) {
                            if (Objects.equals(old.getId(), v.getId())) {
                                flags = old.update(v);
                                flags.add(Flag.FOUND);
                                break;
                            }
                        }
                        if (flags.isEmpty()) {
                            occurrence.addVictim(v);
                            new AsyncPostVictim(victim -> { }).execute(token, occurrence.getId(), v);
                        } else {
                            if (flags.contains(Flag.UPDATED_VICTIM)) {
                                new AsyncPutVictim(victim -> { }).execute(token, v);
                            }
                        }
                    } else {
                        occurrence.addVictim(v);
                        new AsyncPostVictim(victim -> { }).execute(token, occurrence.getId(), v);
                    }
                    Objects.requireNonNull(this.binding.victimList.getAdapter()).notifyDataSetChanged();
                }
            });
    private ArrayList<Hospital> hospitals;
    private ActivityOccurrenceBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityOccurrenceBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        Intent intent = getIntent();
        this.occurrence = (Occurrence) intent.getSerializableExtra(Args.ARG_OCCURRENCE);
        this.isActive = intent.getBooleanExtra(Args.ARG_ACTIVE, false);
        if (this.isActive) {
            this.token = intent.getStringExtra(Args.ARG_TOKEN);
            //noinspection unchecked
            this.hospitals = (ArrayList<Hospital>) intent.getSerializableExtra(Args.ARG_HOSPITALS);
        } else {
            this.token = "";
            this.hospitals = new ArrayList<>();
        }

        title = getString(R.string.occurrence) + " #" + this.occurrence.getOccurrence_number();
        setTitle(title);

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
            this.occurrence.setMotive(this.binding.occurrenceMotive.getText().toString());
            this.occurrence.setEntity(this.binding.occurrenceEntity.getText().toString());
            this.occurrence.setMean_of_assistance(this.binding.occurrenceMean.getText().toString());
            this.occurrence.setLocal(this.binding.occurrenceLocal.getText().toString());
            this.occurrence.setParish(this.binding.occurrenceParish.getText().toString());
            this.occurrence.setMunicipality(this.binding.occurrenceMunicipality.getText().toString());
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
        new AsyncPostOccurrenceState(result -> { }).execute(token, occurrence.getId(), state);
        this.occurrence.addState(state);
        Objects.requireNonNull(this.binding.stateList.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onAddVictimClick() {
        new AsyncPostVictim(victim -> {
            if (victim != null) {
                occurrence.addVictim(victim);
                openVictim(victim, getString(R.string.new_victim));
            }
        }).execute(token, occurrence.getId(), new Victim());
    }

    @Override
    public void onVictimClick(Victim victim, String tempName) {
        openVictim(victim, tempName);
    }

    private void openVictim(Victim victim, String tempName) {
        Intent intent = new Intent(this, VictimActivity.class);
        intent.putExtra(Args.ARG_TOKEN, this.token);
        intent.putExtra(Args.ARG_VICTIM, victim);
        intent.putExtra(Args.ARG_TITLE, title);
        if (tempName != null) {
            intent.putExtra(Args.ARG_TEMP_NAME, tempName);
        }
        intent.putExtra(Args.ARG_ACTIVE, this.isActive);
        intent.putExtra(Args.ARG_HOSPITALS, this.hospitals);
        this.startVictim.launch(intent);
    }
}