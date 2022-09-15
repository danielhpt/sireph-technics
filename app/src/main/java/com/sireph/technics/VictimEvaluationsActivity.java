package com.sireph.technics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.async.post.AsyncPostEvaluation;
import com.sireph.technics.databinding.ActivityVictimEvaluationsBinding;
import com.sireph.technics.dialogs.scales.ScaleGCSDialogFragment;
import com.sireph.technics.dialogs.scales.ScaleNEWSDialogFragment;
import com.sireph.technics.models.Victim;
import com.sireph.technics.models.date.DateTime;
import com.sireph.technics.models.enums.AVDS;
import com.sireph.technics.models.enums.Pupils;
import com.sireph.technics.models.enums.Skin;
import com.sireph.technics.models.procedures.Evaluation;
import com.sireph.technics.models.procedures.GlasgowScale;
import com.sireph.technics.table.components.RowHeader;
import com.sireph.technics.table.generators.EvaluationsToTable;
import com.sireph.technics.utils.DateTimeInput;
import com.sireph.technics.utils.EditTextString;
import com.sireph.technics.utils.TextChangedWatcher;
import com.sireph.technics.utils.Validation;
import com.sireph.technics.utils.statics.Args;

import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Objects;

public class VictimEvaluationsActivity extends AppCompatActivity implements ScaleGCSDialogFragment.ScaleGCSDialogListener,
        ScaleNEWSDialogFragment.ScaleNEWSDialogListener {
    private String token;
    private Victim victim;
    private boolean isActive;
    private ActivityVictimEvaluationsBinding binding;
    private GlasgowScale glasgowScale;
    private ScaleNEWSDialogFragment.NEWSScale newsScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityVictimEvaluationsBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
        setSupportActionBar(binding.included.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        this.token = intent.getStringExtra(Args.ARG_TOKEN);
        this.victim = (Victim) intent.getSerializableExtra(Args.ARG_VICTIM);
        this.isActive = intent.getBooleanExtra(Args.ARG_ACTIVE, false);
        binding.included.toolbar.setTitle(intent.getStringExtra(Args.ARG_TITLE) + " > " + getString(R.string.evaluations));

        this.glasgowScale = null;
        this.newsScale = null;

        EditTextString.editTextString(this.binding.evaluationVent, "", this.isActive); // 0-50 //0
        this.binding.evaluationVent.addTextChangedListener(new TextChangedWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Validation.validateInt(s.toString(), 0, 50, true)) {
                    binding.evaluationVent.setError(null);
                } else {
                    binding.evaluationVent.setError(getString(R.string.invalid_value));
                }
            }
        });
        EditTextString.editTextString(this.binding.evaluationSpO2, "", this.isActive); // 0-100
        this.binding.evaluationSpO2.addTextChangedListener(new TextChangedWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Validation.validateInt(s.toString(), 0, 100, true)) {
                    binding.evaluationSpO2.setError(null);
                } else {
                    binding.evaluationSpO2.setError(getString(R.string.invalid_value));
                }
            }
        });
        EditTextString.editTextString(this.binding.evaluationO2Sup, "", this.isActive); // 0-100
        this.binding.evaluationO2Sup.addTextChangedListener(new TextChangedWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Validation.validateInt(s.toString(), 0, 100, true)) {
                    binding.evaluationO2Sup.setError(null);
                } else {
                    binding.evaluationO2Sup.setError(getString(R.string.invalid_value));
                }
            }
        });
        EditTextString.editTextString(this.binding.evaluationEtCO2, "", this.isActive); // 0-100
        this.binding.evaluationEtCO2.addTextChangedListener(new TextChangedWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Validation.validateInt(s.toString(), 0, 100, true)) {
                    binding.evaluationEtCO2.setError(null);
                } else {
                    binding.evaluationEtCO2.setError(getString(R.string.invalid_value));
                }
            }
        });
        EditTextString.editTextString(this.binding.evaluationPulse, "", this.isActive); // 0-300 //0
        this.binding.evaluationPulse.addTextChangedListener(new TextChangedWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Validation.validateInt(s.toString(), 0, 300, true)) {
                    binding.evaluationPulse.setError(null);
                } else {
                    binding.evaluationPulse.setError(getString(R.string.invalid_value));
                }
            }
        });
        EditTextString.editTextString(this.binding.evaluationTemperature, "", this.isActive); // 20-50
        this.binding.evaluationTemperature.addTextChangedListener(new TextChangedWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Validation.validateDouble(s.toString(), 20, 50, true)) {
                    binding.evaluationTemperature.setError(null);
                } else {
                    binding.evaluationTemperature.setError(getString(R.string.invalid_value));
                }
            }
        });
        EditTextString.editTextString(this.binding.evaluationSystolic, "", this.isActive); // 0-250 //0
        this.binding.evaluationSystolic.addTextChangedListener(new TextChangedWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Validation.validateInt(s.toString(), 0, 250, true)) {
                    binding.evaluationSystolic.setError(null);
                } else {
                    binding.evaluationSystolic.setError(getString(R.string.invalid_value));
                }
            }
        });
        EditTextString.editTextString(this.binding.evaluationDiastolic, "", this.isActive); // 0-200 //0
        this.binding.evaluationDiastolic.addTextChangedListener(new TextChangedWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Validation.validateInt(s.toString(), 0, 200, true)) {
                    binding.evaluationDiastolic.setError(null);
                } else {
                    binding.evaluationDiastolic.setError(getString(R.string.invalid_value));
                }
            }
        });
        EditTextString.editTextString(this.binding.evaluationPain, "", this.isActive); //0-10
        this.binding.evaluationPain.addTextChangedListener(new TextChangedWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Validation.validateInt(s.toString(), 0, 10, true)) {
                    binding.evaluationPain.setError(null);
                } else {
                    binding.evaluationPain.setError(getString(R.string.invalid_value));
                }
            }
        });
        EditTextString.editTextString(this.binding.evaluationGlycemia, "", this.isActive); // 0-600
        this.binding.evaluationGlycemia.addTextChangedListener(new TextChangedWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Validation.validateInt(s.toString(), 0, 600, true)) {
                    binding.evaluationGlycemia.setError(null);
                } else {
                    binding.evaluationGlycemia.setError(getString(R.string.invalid_value));
                }
            }
        });

        DateTimeInput.setupTimeInput(this.binding.evaluationTime.getRoot(), this, this.isActive, true, null, false);

        ArrayAdapter<AVDS> avdsArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, AVDS.values()); // S
        avdsArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        this.binding.evaluationAVDS.setAdapter(avdsArrayAdapter);
        this.binding.evaluationAVDS.setSelection(0);
        this.binding.evaluationAVDS.setEnabled(isActive);

        ArrayAdapter<Skin> skinArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, Skin.values());
        skinArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        this.binding.evaluationSkin.setAdapter(skinArrayAdapter);
        this.binding.evaluationSkin.setSelection(0);
        this.binding.evaluationSkin.setEnabled(isActive);

        ArrayAdapter<Pupils> pupilsArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, Pupils.values());
        pupilsArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        this.binding.evaluationPupils.setAdapter(pupilsArrayAdapter);
        this.binding.evaluationPupils.setSelection(0);
        this.binding.evaluationPupils.setEnabled(isActive);

        this.binding.evaluationGCS.setOnClickListener(v -> { // 3
            ScaleGCSDialogFragment fragment = new ScaleGCSDialogFragment(this.glasgowScale, this);
            fragment.show(getSupportFragmentManager(), "ScaleGCSDialogFragment");
        });

        this.binding.evaluationNEWS.setOnClickListener(v -> {
            ScaleNEWSDialogFragment fragment = new ScaleNEWSDialogFragment(this.newsScale, this);
            fragment.show(getSupportFragmentManager(), "ScaleNEWSDialogFragment");
        });
        this.binding.evaluationNEWS.setEnabled(isActive);

        binding.evaluationPCR.setEnabled(isActive);
        binding.evaluationAdd.setEnabled(isActive);
        binding.evaluationECG.setEnabled(isActive);
        binding.evaluationGCS.setEnabled(isActive);

        new EvaluationsToTable().setupTable(binding.contentContainer, victim.getEvaluations(), this);
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
                        // todo
                    })
                    .setNegativeButton(R.string.no, null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        if (this.isActive) {
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

    public void victimPCR(View view) {
        this.binding.evaluationAVDS.setSelection(4);
        onScaleGCSDialogOk(new GlasgowScale(1, 1, 1));
        this.binding.evaluationVent.setText("0");
        this.binding.evaluationPulse.setText("0");
        this.binding.evaluationSystolic.setText("0");
        this.binding.evaluationDiastolic.setText("0");
    }

    @SuppressLint("DefaultLocale")
    public void addEvaluation(View view) {
        DateTime dateTime = DateTime.now();
        try {
            dateTime.setTime(this.binding.evaluationTime.timeText.getText().toString());
        } catch (DateTimeParseException e) {
            this.binding.evaluationTime.timeText.setError(getString(R.string.invalid_time));
            return;
        }
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        this.binding.evaluationTime.timeText.setText(String.format("%02d:%02d", currentHour, currentMinute));

        AVDS avds = (AVDS) this.binding.evaluationAVDS.getSelectedItem();
        this.binding.evaluationAVDS.setSelection(0);
        Skin skin = (Skin) this.binding.evaluationSkin.getSelectedItem();
        this.binding.evaluationSkin.setSelection(0);
        Pupils pupils = (Pupils) this.binding.evaluationPupils.getSelectedItem();
        this.binding.evaluationPupils.setSelection(0);

        Integer vent = EditTextString.getInt(this.binding.evaluationVent);
        this.binding.evaluationVent.setText("");
        Integer spo2 = EditTextString.getInt(this.binding.evaluationSpO2);
        this.binding.evaluationSpO2.setText("");
        Integer o2sup = EditTextString.getInt(this.binding.evaluationO2Sup);
        this.binding.evaluationO2Sup.setText("");
        Integer etco2 = EditTextString.getInt(this.binding.evaluationEtCO2);
        this.binding.evaluationEtCO2.setText("");
        Integer pulse = EditTextString.getInt(this.binding.evaluationPulse);
        this.binding.evaluationPulse.setText("");
        Integer systolic = EditTextString.getInt(this.binding.evaluationSystolic);
        this.binding.evaluationSystolic.setText("");
        Integer diastolic = EditTextString.getInt(this.binding.evaluationDiastolic);
        this.binding.evaluationDiastolic.setText("");
        Integer pain = EditTextString.getInt(this.binding.evaluationPain);
        this.binding.evaluationPain.setText("");
        Integer glycemia = EditTextString.getInt(this.binding.evaluationGlycemia);
        this.binding.evaluationGlycemia.setText("");

        Integer news = EditTextString.getInt(this.binding.evaluationNEWS);
        this.binding.evaluationNEWS.setText("");
        this.newsScale = null;

        Double temp = EditTextString.getDouble(this.binding.evaluationTemperature);
        this.binding.evaluationTemperature.setText("");

        boolean ecg = this.binding.evaluationECG.isChecked();
        this.binding.evaluationECG.setChecked(false);

        Evaluation evaluation = new Evaluation(dateTime, avds, vent, spo2, o2sup, etco2, pulse, systolic, diastolic, pain, glycemia, news, ecg, skin,
                temp, pupils, this.glasgowScale);
        this.binding.evaluationGCS.setText("");
        this.glasgowScale = null;
        new AsyncPostEvaluation(result -> {
        }).execute(token, victim.getId(), evaluation);

        this.victim.addEvaluation(evaluation);

        //noinspection unchecked
        Objects.requireNonNull(binding.contentContainer.getAdapter()).addRow(victim.getEvaluations().size() - 1,
                new RowHeader(Integer.toString(victim.getEvaluations().size())), evaluation.toCellList());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onScaleGCSDialogOk(GlasgowScale scale) {
        this.glasgowScale = scale;
        this.binding.evaluationGCS.setText(this.glasgowScale.getTotal().toString());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onScaleNEWSDialogOk(ScaleNEWSDialogFragment.NEWSScale scale) {
        this.newsScale = scale;
        this.binding.evaluationNEWS.setText(Integer.toString(this.newsScale.getValue()));
    }
}