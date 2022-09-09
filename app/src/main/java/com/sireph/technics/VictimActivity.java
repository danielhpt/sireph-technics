package com.sireph.technics;

import static com.sireph.technics.utils.DateTimeInput.setupDateInput;
import static com.sireph.technics.utils.EditTextString.editTextString;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.databinding.ActivityVictimBinding;
import com.sireph.technics.models.Hospital;
import com.sireph.technics.models.Technician;
import com.sireph.technics.models.Victim;
import com.sireph.technics.models.enums.Gender;
import com.sireph.technics.models.enums.State;

import java.util.ArrayList;

public class VictimActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static String ARG_TOKEN = "1", ARG_TECHNICIAN = "2", ARG_VICTIM = "3", ARG_ACTIVE = "4", ARG_HOSPITALS = "5";
    private String token;
    private Technician technician;
    private Victim victim;
    private ArrayList<Hospital> hospitals;
    private boolean isActive;
    private ActivityVictimBinding binding;
    private EditText birthdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityVictimBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        this.token = intent.getStringExtra(ARG_TOKEN);
        this.technician = (Technician) intent.getSerializableExtra(ARG_TECHNICIAN);
        this.victim = (Victim) intent.getSerializableExtra(ARG_VICTIM);
        this.isActive = intent.getBooleanExtra(ARG_ACTIVE, false);
        //noinspection unchecked
        this.hospitals = (ArrayList<Hospital>) intent.getSerializableExtra(ARG_HOSPITALS);

        editTextString(binding.editVictimName, victim.getName(), isActive);
        editTextString(binding.editVictimAddress, victim.getAddress(), isActive);
        editTextString(binding.editVictimDocument, victim.getIdentity_number(), isActive);
        editTextString(binding.editVictimAge, victim.getAge().toString(), isActive);
        editTextString(binding.editVictimComments, victim.getComments(), isActive);

        this.birthdate = setupDateInput(binding.includedVictimBirthdate.getRoot(), this, isActive, false, victim.getBirthdate());

        Spinner spinner = binding.spinnerVictimGender;
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
        // todo
    }

    public void gotoInformation(View view) {
        // todo
    }

    public void gotoProcedures(View view) {
        // todo
    }

    public void gotoProtocols(View view) {
        // todo
    }

    public void openTransport(View view) {
        // todo
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.victim.setGender((Gender) parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}