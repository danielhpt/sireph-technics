package com.sireph.technics.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.R;
import com.sireph.technics.async.post.victim.AsyncPostVictimSymptom;
import com.sireph.technics.async.post.victim.procedure.AsyncPostProcedureCirculation;
import com.sireph.technics.async.post.victim.procedure.AsyncPostProcedureProtocol;
import com.sireph.technics.async.post.victim.procedure.AsyncPostProcedureRCP;
import com.sireph.technics.async.post.victim.procedure.AsyncPostProcedureScale;
import com.sireph.technics.async.post.victim.procedure.AsyncPostProcedureVentilation;
import com.sireph.technics.async.put.AsyncPutVictimTransport;
import com.sireph.technics.databinding.ActivityVictimBinding;
import com.sireph.technics.dialogs.TransportDialog;
import com.sireph.technics.models.Hospital;
import com.sireph.technics.models.date.Date;
import com.sireph.technics.models.enums.Gender;
import com.sireph.technics.models.enums.NonTransportReason;
import com.sireph.technics.models.enums.TypeOfTransport;
import com.sireph.technics.models.victim.Victim;
import com.sireph.technics.models.victim.symptom.Symptom;
import com.sireph.technics.test.Test;
import com.sireph.technics.utils.DateTimeInput;
import com.sireph.technics.utils.EditTextString;
import com.sireph.technics.utils.TextChangedWatcher;
import com.sireph.technics.utils.Validation;
import com.sireph.technics.utils.VictimTitle;
import com.sireph.technics.utils.statics.Args;
import com.sireph.technics.utils.statics.Flag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VictimActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TransportDialog.TransportDialogListener {
    private String token, oldTile;
    private Victim victim;
    private boolean isActive;
    private final ActivityResultLauncher<Intent> startEvaluations = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (this.isActive && result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    assert intent != null;
                    List<Flag> flags = victim.update((Victim) intent.getSerializableExtra(Args.ARG_VICTIM));
                    // todo
                }
            });
    private final ActivityResultLauncher<Intent> startInformation = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (this.isActive && result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    assert intent != null;
                    List<Flag> flags = victim.update((Victim) intent.getSerializableExtra(Args.ARG_VICTIM));
                    // todo
                }
            });
    private final ActivityResultLauncher<Intent> startProcedures = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (this.isActive && result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    assert intent != null;
                    List<Flag> flags = victim.update((Victim) intent.getSerializableExtra(Args.ARG_VICTIM));
                    if (flags.contains(Flag.UPDATED_RCP)) {
                        new AsyncPostProcedureRCP(rcp -> {
                        }).execute(token, victim.getId(), victim.getProcedureRCP());
                    }
                    if (flags.contains(Flag.UPDATED_VENTILATION)) {
                        new AsyncPostProcedureVentilation(ventilation -> {
                        }).execute(token, victim.getId(), victim.getProcedureVentilation());
                    }
                    if (flags.contains(Flag.UPDATED_CIRCULATION)) {
                        new AsyncPostProcedureCirculation(circulation -> {
                        }).execute(token, victim.getId(), victim.getProcedureCirculation());
                    }
                }
            });
    private final ActivityResultLauncher<Intent> startProtocols = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (this.isActive && result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    assert intent != null;
                    List<Flag> flags = victim.update((Victim) intent.getSerializableExtra(Args.ARG_VICTIM));
                    if (flags.contains(Flag.UPDATED_PROTOCOL)) {
                        new AsyncPostProcedureProtocol(protocol -> {
                        }).execute(token, victim.getId(), victim.getProcedureProtocol());
                    }
                    if (flags.contains(Flag.UPDATED_SCALE)) {
                        new AsyncPostProcedureScale(scale -> {
                        }).execute(token, victim.getId(), victim.getProcedureScale());
                    }
                    if (flags.contains(Flag.UPDATED_SYMPTOM)) {
                        new AsyncPostVictimSymptom(symptom -> {
                        }).execute(token, victim.getId(), victim.getSymptom());
                    }
                }
            });
    private final ActivityResultLauncher<Intent> startTrauma = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (this.isActive && result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    assert intent != null;
                    List<Flag> flags = victim.getSymptom().update((Symptom) intent.getSerializableExtra(Args.ARG_SYMPTOM));
                    if (flags.contains(Flag.UPDATED_SYMPTOM)) {
                        new AsyncPostVictimSymptom(symptom -> {
                        }).execute(token, victim.getId(), victim.getSymptom());
                    }
                }
            });
    private Test test = null;
    private ArrayList<Hospital> hospitals;
    private ActivityVictimBinding binding;
    private EditText birthdate;
    private int n_victim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityVictimBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
        setSupportActionBar(binding.included.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        this.token = intent.getStringExtra(Args.ARG_TOKEN);
        this.n_victim = intent.getIntExtra(Args.ARG_VICTIM_N, -1);
        this.victim = (Victim) intent.getSerializableExtra(Args.ARG_VICTIM);
        this.isActive = intent.getBooleanExtra(Args.ARG_IS_ACTIVE, false);
        //noinspection unchecked
        this.hospitals = (ArrayList<Hospital>) intent.getSerializableExtra(Args.ARG_HOSPITALS);
        this.oldTile = intent.getStringExtra(Args.ARG_TITLE);
        if (intent.hasExtra(Args.ARG_TEST)) {
            test = (Test) intent.getSerializableExtra(Args.ARG_TEST);
        }

        createTitle();

        EditTextString.editTextString(this.binding.victimName, this.victim.getName(), this.isActive);
        EditTextString.editTextString(this.binding.victimAddress, this.victim.getAddress(), this.isActive);
        EditTextString.editTextString(this.binding.victimDocument, this.victim.getIdentity_number(), this.isActive);
        EditTextString.editTextString(this.binding.victimComments, this.victim.getComments(), this.isActive);
        EditTextString.editTextString(this.binding.victimTypeOfEmergency, this.victim.getType_of_emergency(), this.isActive);
        String age;
        if (this.victim.getAge() == null) {
            age = "";
        } else {
            age = this.victim.getAge().toString();
        }
        EditTextString.editTextString(this.binding.victimAge, age, this.isActive);
        this.binding.victimAge.addTextChangedListener(new TextChangedWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Validation.validateInt(s.toString(), 0, 130, true)) {
                    binding.victimAge.setError(getString(R.string.invalid_age));
                } else {
                    binding.victimAge.setError(null);
                }
            }
        });

        this.birthdate = DateTimeInput.setupDateInput(this.binding.includedVictimBirthdate.getRoot(), this, this.isActive, false,
                this.victim.getBirthdate(), true);

        Spinner spinner = this.binding.spinnerVictimGender;
        ArrayAdapter<Gender> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, Gender.values());
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getPosition(this.victim.getGender()));
        spinner.setOnItemSelectedListener(this);
        spinner.setEnabled(isActive);
    }

    private void createTitle() {
        String s;
        if (n_victim == -1) {
            s = getString(R.string.new_victim);
        } else {
            s = VictimTitle.createTitle(victim, n_victim, this);
        }
        binding.included.toolbar.setTitle(oldTile + " > " + s);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        SharedPreferences preferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        menu.findItem(R.id.menuUsername).setTitle(preferences.getString(getString(R.string.sharedPref_key_username), getString(R.string.username)));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuUsername) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.logout)
                    .setMessage(R.string.confirm_logout)
                    .setPositiveButton(R.string.yes, (dialog, id) -> {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.putExtra(Args.ARG_TOKEN, token);
                        intent.putExtra(Args.ARG_IS_LOGOUT, true);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        if (test != null) {
                            intent.putExtra(Args.ARG_TEST, test);
                        }
                        finish();
                        startActivity(intent);
                    })
                    .setNegativeButton(R.string.no, null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        String age = binding.victimAge.getText().toString();
        String date = this.birthdate.getText().toString();
        boolean ageV = Validation.validateInt(age, 0, 130, true);
        boolean dateV = Validation.validateDate(date, true);
        if (ageV && dateV) {
            victim.setAge(age.equals("") ? null : Integer.parseInt(age));
            if (!date.equals("")) {
                Date dateTime = Date.parse(date);
                this.victim.setBirthdate(dateTime);
            } else {
                this.victim.setBirthdate(null);
            }
        } else {
            if (!dateV) {
                this.birthdate.setError(getString(R.string.invalid_date));
            }
            if (!ageV) {
                this.binding.victimAge.setError(getString(R.string.invalid_value));
            }
            return;
        }
        Intent intent = new Intent();
        if (this.isActive) {
            this.victim.setName(this.binding.victimName.getText().toString());
            this.victim.setIdentity_number(this.binding.victimDocument.getText().toString());
            this.victim.setAddress(this.binding.victimAddress.getText().toString());
            this.victim.setComments(this.binding.victimComments.getText().toString());
            this.victim.setGender((Gender) this.binding.spinnerVictimGender.getSelectedItem());
            this.victim.setType_of_emergency(this.binding.victimTypeOfEmergency.getText().toString());
            intent.putExtra(Args.ARG_VICTIM, this.victim);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void putExtras(Intent intent) {
        intent.putExtra(Args.ARG_TOKEN, this.token);
        intent.putExtra(Args.ARG_VICTIM, this.victim);
        intent.putExtra(Args.ARG_IS_ACTIVE, this.isActive);
        intent.putExtra(Args.ARG_TITLE, binding.included.toolbar.getTitle().toString());
    }

    public void gotoEvaluations(View view) {
        Intent intent = new Intent(this, VictimEvaluationsActivity.class);
        putExtras(intent);
        this.startEvaluations.launch(intent);
    }

    public void gotoInformation(View view) {
        Intent intent = new Intent(this, VictimInformationActivity.class);
        putExtras(intent);
        this.startInformation.launch(intent);
    }

    public void gotoProcedures(View view) {
        Intent intent = new Intent(this, VictimProceduresActivity.class);
        putExtras(intent);
        this.startProcedures.launch(intent);
    }

    public void gotoProtocols(View view) {
        Intent intent = new Intent(this, VictimProtocolsActivity.class);
        putExtras(intent);
        this.startProtocols.launch(intent);
    }

    public void gotoTraumas(View view) {
        Intent intent = new Intent(this, VictimTraumaActivity.class);
        intent.putExtra(Args.ARG_TOKEN, this.token);
        intent.putExtra(Args.ARG_SYMPTOM, this.victim.getSymptom());
        intent.putExtra(Args.ARG_VICTIM_ID, this.victim.getId());
        intent.putExtra(Args.ARG_TITLE, getTitle());
        intent.putExtra(Args.ARG_IS_ACTIVE, this.isActive);
        intent.putExtra(Args.ARG_TITLE, binding.included.toolbar.getTitle().toString());
        this.startTrauma.launch(intent);
    }

    public void openTransport(View view) {
        TransportDialog dialog = new TransportDialog(this.victim, this.hospitals, this.isActive, this);
        dialog.show(getSupportFragmentManager(), "TransportDialogFragment");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.victim.setGender((Gender) parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onTransportDialogOk(Hospital hospital, TypeOfTransport type, NonTransportReason reason, Integer episode, boolean followup) {
        victim.setHospital(hospital);
        victim.setType_of_transport(type);
        victim.setNon_transport_reason(reason);
        victim.setEpisode_number(episode);
        victim.setMedical_followup(followup);
        new AsyncPutVictimTransport(transport -> {
        }).execute(token, victim.getId(), victim);
    }
}