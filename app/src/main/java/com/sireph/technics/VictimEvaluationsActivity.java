package com.sireph.technics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.databinding.ActivityVictimEvaluationsBinding;
import com.sireph.technics.dialogs.scales.ScaleGCSDialogFragment;
import com.sireph.technics.models.Technician;
import com.sireph.technics.models.Victim;
import com.sireph.technics.models.enums.AVDS;
import com.sireph.technics.models.enums.Pupils;
import com.sireph.technics.models.enums.Skin;
import com.sireph.technics.utils.DateTimeInput;
import com.sireph.technics.utils.EditTextString;

public class VictimEvaluationsActivity extends AppCompatActivity {
    public static String ARG_TOKEN = "1", ARG_TECHNICIAN = "2", ARG_VICTIM = "3", ARG_ACTIVE = "4";
    private String token;
    private Technician technician;
    private Victim victim;
    private boolean isActive;
    private ActivityVictimEvaluationsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityVictimEvaluationsBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        Intent intent = getIntent();
        this.token = intent.getStringExtra(ARG_TOKEN);
        this.technician = (Technician) intent.getSerializableExtra(ARG_TECHNICIAN);
        this.victim = (Victim) intent.getSerializableExtra(ARG_VICTIM);
        this.isActive = intent.getBooleanExtra(ARG_ACTIVE, false);

        EditTextString.editTextString(this.binding.evaluationVent, "", this.isActive);
        EditTextString.editTextString(this.binding.evaluationSpO2, "", this.isActive);
        EditTextString.editTextString(this.binding.evaluationO2Sup, "", this.isActive);
        EditTextString.editTextString(this.binding.evaluationEtCO2, "", this.isActive);
        EditTextString.editTextString(this.binding.evaluationPulse, "", this.isActive);
        EditTextString.editTextString(this.binding.evaluationTemperature, "", this.isActive);
        EditTextString.editTextString(this.binding.evaluationSystolic, "", this.isActive);
        EditTextString.editTextString(this.binding.evaluationDiastolic, "", this.isActive);
        EditTextString.editTextString(this.binding.evaluationPain, "", this.isActive);
        EditTextString.editTextString(this.binding.evaluationGlycemia, "", this.isActive);

        DateTimeInput.setupTimeInput(this.binding.evaluationTime.getRoot(), this, this.isActive, true, null, false);

        ArrayAdapter<AVDS> avdsArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, AVDS.values());
        avdsArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        this.binding.evaluationAVDS.setAdapter(avdsArrayAdapter);
        this.binding.evaluationAVDS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // todo
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<Skin> skinArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, Skin.values());
        skinArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        this.binding.evaluationSkin.setAdapter(skinArrayAdapter);
        this.binding.evaluationSkin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // todo
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<Pupils> pupilsArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, Pupils.values());
        pupilsArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        this.binding.evaluationPupils.setAdapter(pupilsArrayAdapter);
        this.binding.evaluationPupils.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // todo
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        this.binding.evaluationGCS.setOnClickListener(v -> {
            ScaleGCSDialogFragment fragment = new ScaleGCSDialogFragment();
            fragment.show(getSupportFragmentManager(), "ScaleGCSDialogFragment");
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}