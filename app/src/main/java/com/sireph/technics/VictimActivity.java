package com.sireph.technics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.databinding.ActivityVictimBinding;
import com.sireph.technics.dialogs.TransportDialogFragment;
import com.sireph.technics.models.Hospital;
import com.sireph.technics.models.Technician;
import com.sireph.technics.models.Victim;
import com.sireph.technics.models.enums.Gender;
import com.sireph.technics.models.enums.NonTransportReason;
import com.sireph.technics.models.enums.TypeOfTransport;
import com.sireph.technics.utils.DateTimeInput;
import com.sireph.technics.utils.EditTextString;
import com.sireph.technics.utils.TextChangedWatcher;
import com.sireph.technics.utils.Validation;

import java.util.ArrayList;

public class VictimActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TransportDialogFragment.TransportDialogListener {
    public static String ARG_TOKEN = "1", ARG_TECHNICIAN = "2", ARG_VICTIM = "3", ARG_ACTIVE = "4", ARG_HOSPITALS = "5";
    private String token;
    private Technician technician;
    private Victim victim;
    private boolean isActive;
    private ArrayList<Hospital> hospitals;
    private ActivityVictimBinding binding;
    private EditText birthdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityVictimBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        Intent intent = getIntent();
        this.token = intent.getStringExtra(ARG_TOKEN);
        this.technician = (Technician) intent.getSerializableExtra(ARG_TECHNICIAN);
        this.victim = (Victim) intent.getSerializableExtra(ARG_VICTIM);
        this.isActive = intent.getBooleanExtra(ARG_ACTIVE, false);
        //noinspection unchecked
        this.hospitals = (ArrayList<Hospital>) intent.getSerializableExtra(ARG_HOSPITALS);

        EditTextString.editTextString(this.binding.victimName, this.victim.getName(), this.isActive);
        EditTextString.editTextString(this.binding.victimAddress, this.victim.getAddress(), this.isActive);
        EditTextString.editTextString(this.binding.victimDocument, this.victim.getIdentity_number(), this.isActive);
        EditTextString.editTextString(this.binding.victimComments, this.victim.getComments(), this.isActive);
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
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void gotoEvaluations(View view) {
        Intent intent = new Intent(this, VictimEvaluationsActivity.class);
        intent.putExtra(VictimEvaluationsActivity.ARG_TOKEN, this.token);
        intent.putExtra(VictimEvaluationsActivity.ARG_TECHNICIAN, this.technician);
        intent.putExtra(VictimEvaluationsActivity.ARG_VICTIM, this.victim);
        intent.putExtra(VictimEvaluationsActivity.ARG_ACTIVE, this.isActive);
        startActivity(intent);
    }

    public void gotoInformation(View view) {
        Intent intent = new Intent(this, VictimInformationActivity.class);
        intent.putExtra(VictimInformationActivity.ARG_TOKEN, this.token);
        intent.putExtra(VictimInformationActivity.ARG_TECHNICIAN, this.technician);
        intent.putExtra(VictimInformationActivity.ARG_VICTIM, this.victim);
        intent.putExtra(VictimInformationActivity.ARG_ACTIVE, this.isActive);
        startActivity(intent);
    }

    public void gotoProcedures(View view) {
        Intent intent = new Intent(this, VictimProceduresActivity.class);
        intent.putExtra(VictimProceduresActivity.ARG_TOKEN, this.token);
        intent.putExtra(VictimProceduresActivity.ARG_TECHNICIAN, this.technician);
        intent.putExtra(VictimProceduresActivity.ARG_VICTIM, this.victim);
        intent.putExtra(VictimProceduresActivity.ARG_ACTIVE, this.isActive);
        startActivity(intent);
    }

    public void gotoProtocols(View view) {
        Intent intent = new Intent(this, VictimProtocolsActivity.class);
        intent.putExtra(VictimProtocolsActivity.ARG_TOKEN, this.token);
        intent.putExtra(VictimProtocolsActivity.ARG_TECHNICIAN, this.technician);
        intent.putExtra(VictimProtocolsActivity.ARG_VICTIM, this.victim);
        intent.putExtra(VictimProtocolsActivity.ARG_ACTIVE, this.isActive);
        startActivity(intent);
    }

    public void openTransport(View view) {
        TransportDialogFragment dialog = new TransportDialogFragment(this.victim, this.hospitals, this.isActive, this);
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
        // todo
    }
}