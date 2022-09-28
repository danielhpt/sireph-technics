package com.sireph.technics.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.R;
import com.sireph.technics.databinding.ActivityVictimInformationBinding;
import com.sireph.technics.models.date.DateTime;
import com.sireph.technics.models.victim.Victim;
import com.sireph.technics.test.Test;
import com.sireph.technics.utils.DateTimeInput;
import com.sireph.technics.utils.EditTextString;
import com.sireph.technics.utils.Validation;
import com.sireph.technics.utils.statics.Args;

import java.util.Objects;

public class VictimInformationActivity extends AppCompatActivity {
    private Victim victim;
    private boolean isActive;
    private ActivityVictimInformationBinding binding;
    private EditText lastMealTime;
    private String token;
    private Test test = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityVictimInformationBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
        setSupportActionBar(binding.included.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        this.victim = (Victim) intent.getSerializableExtra(Args.ARG_VICTIM);
        this.isActive = intent.getBooleanExtra(Args.ARG_IS_ACTIVE, false);
        this.token = intent.getStringExtra(Args.ARG_TOKEN);
        binding.included.toolbar.setTitle(intent.getStringExtra(Args.ARG_TITLE) + " > " + getString(R.string.information_gathering));
        if (intent.hasExtra(Args.ARG_TEST)) {
            test = (Test) intent.getSerializableExtra(Args.ARG_TEST);
        }

        EditTextString.editTextString(this.binding.editCircumstances, victim.getCircumstances(), this.isActive);
        EditTextString.editTextString(this.binding.editAllergies, victim.getAllergies(), this.isActive);
        EditTextString.editTextString(this.binding.editLastMeal, victim.getLast_meal(), this.isActive);
        EditTextString.editTextString(this.binding.editDiseaseHistory, victim.getDisease_history(), this.isActive);
        EditTextString.editTextString(this.binding.editUsualMedication, victim.getUsual_medication(), this.isActive);
        EditTextString.editTextString(this.binding.editRiskSituation, victim.getRisk_situation(), this.isActive);

        this.lastMealTime = DateTimeInput.setupTimeInput(this.binding.includedLastMealHour.getRoot(), this, this.isActive, false, this.victim.getLast_meal_time(), true);
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