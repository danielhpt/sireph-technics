package com.sireph.technics;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.databinding.ActivityVictimInformationBinding;
import com.sireph.technics.models.Victim;
import com.sireph.technics.models.date.DateTime;
import com.sireph.technics.utils.DateTimeInput;
import com.sireph.technics.utils.EditTextString;
import com.sireph.technics.utils.Validation;
import com.sireph.technics.utils.statics.Args;

public class VictimInformationActivity extends AppCompatActivity {
    private Victim victim;
    private boolean isActive;
    private ActivityVictimInformationBinding binding;
    private EditText lastMealTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityVictimInformationBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        Intent intent = getIntent();
        this.victim = (Victim) intent.getSerializableExtra(Args.ARG_VICTIM);
        this.isActive = intent.getBooleanExtra(Args.ARG_ACTIVE, false);
        setTitle(intent.getStringExtra(Args.ARG_TITLE) + " > " + getString(R.string.information_gathering));

        EditTextString.editTextString(this.binding.editCircumstances, victim.getCircumstances(), this.isActive);
        EditTextString.editTextString(this.binding.editAllergies, victim.getAllergies(), this.isActive);
        EditTextString.editTextString(this.binding.editLastMeal, victim.getLast_meal(), this.isActive);
        EditTextString.editTextString(this.binding.editDiseaseHistory, victim.getDisease_history(), this.isActive);
        EditTextString.editTextString(this.binding.editUsualMedication, victim.getUsual_medication(), this.isActive);
        EditTextString.editTextString(this.binding.editRiskSituation, victim.getRisk_situation(), this.isActive);

        this.lastMealTime = DateTimeInput.setupTimeInput(this.binding.includedLastMealHour.getRoot(), this, this.isActive, false, this.victim.getLast_meal_time(), true);
    }

    @Override
    public void onBackPressed() {
        String time = this.lastMealTime.getText().toString();
        if (Validation.validateTime(time, true)) {
            if (!time.equals("")) {
                DateTime dateTime = DateTime.now();
                dateTime.setTime(time);
                this.victim.setLast_meal_time(dateTime);
            } else {
                this.victim.setLast_meal_time(null);
            }
        } else {
            this.lastMealTime.setError(getString(R.string.invalid_time));
            return;
        }

        Intent intent = new Intent();
        if (this.isActive) {
            this.victim.setCircumstances(this.binding.editCircumstances.getText().toString());
            this.victim.setAllergies(this.binding.editAllergies.getText().toString());
            this.victim.setLast_meal(this.binding.editLastMeal.getText().toString());
            this.victim.setDisease_history(this.binding.editDiseaseHistory.getText().toString());
            this.victim.setUsual_medication(this.binding.editUsualMedication.getText().toString());
            this.victim.setRisk_situation(this.binding.editRiskSituation.getText().toString());

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
}