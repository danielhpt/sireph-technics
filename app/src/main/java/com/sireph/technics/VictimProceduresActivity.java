package com.sireph.technics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.databinding.ActivityVictimProceduresBinding;
import com.sireph.technics.models.Victim;
import com.sireph.technics.models.date.DateTime;
import com.sireph.technics.utils.DateTimeInput;
import com.sireph.technics.utils.EditTextString;
import com.sireph.technics.utils.TextChangedWatcher;
import com.sireph.technics.utils.Validation;
import com.sireph.technics.utils.statics.Args;

public class VictimProceduresActivity extends AppCompatActivity {
    private String token;
    private Victim victim;
    private boolean isActive;
    private ActivityVictimProceduresBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityVictimProceduresBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        Intent intent = getIntent();
        this.token = intent.getStringExtra(Args.ARG_TOKEN);
        this.victim = (Victim) intent.getSerializableExtra(Args.ARG_VICTIM);
        this.isActive = intent.getBooleanExtra(Args.ARG_ACTIVE, false);
        setTitle(intent.getStringExtra(Args.ARG_TITLE) + " > " + getString(R.string.procedures));

        DateTimeInput.setupTimeInput(binding.includedRCPSBV.getRoot(), this, isActive, false, victim.getProcedureRCP().getSBV_DAE(), true);
        DateTimeInput.setupTimeInput(binding.includedRCPSIV.getRoot(), this, isActive, false, victim.getProcedureRCP().getSIV_SAV(), true);
        DateTimeInput.setupTimeInput(binding.includedRCPRecovery.getRoot(), this, isActive, false, victim.getProcedureRCP().getRecovery(), true);
        DateTimeInput.setupTimeInput(binding.includedRCPDowntime.getRoot(), this, isActive, false, victim.getProcedureRCP().getDowntime(), true);

        EditTextString.editTextString(binding.editRCPRhythm, victim.getProcedureRCP().getFirst_rhythm(), isActive);
        if (victim.getProcedureRCP().getNr_shocks() != null) {
            EditTextString.editTextString(binding.editRCPRhythm, victim.getProcedureRCP().getNr_shocks().toString(), isActive);
        } else {
            EditTextString.editTextString(binding.editRCPRhythm, null, isActive);
        }
        binding.editRCPRhythm.addTextChangedListener(new TextChangedWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Validation.validateInt(s.toString(), 0, 10, true)) {
                    binding.editRCPRhythm.setError(null);
                } else {
                    binding.editRCPRhythm.setError(getString(R.string.invalid_value));
                }
            }
        });

        binding.checkBoxRCPW.setChecked(victim.getProcedureRCP().getWitnessed());
        binding.checkBoxRCPM.setChecked(victim.getProcedureRCP().getMechanical_compressions());
        binding.checkBoxRCPN.setChecked(!victim.getProcedureRCP().getPerformed());

        binding.checkBoxVC.setChecked(victim.getProcedureVentilation().getClearance());
        binding.checkBoxVO.setChecked(victim.getProcedureVentilation().getOropharyngeal());
        binding.checkBoxVLT.setChecked(victim.getProcedureVentilation().getLaryngeal_tube());
        binding.checkBoxVE.setChecked(victim.getProcedureVentilation().getEndotracheal());
        binding.checkBoxVLM.setChecked(victim.getProcedureVentilation().getLaryngeal_mask());
        binding.checkBoxVM.setChecked(victim.getProcedureVentilation().getMechanical_ventilation());
        binding.checkBoxVCpap.setChecked(victim.getProcedureVentilation().getCpap());

        binding.checkBoxCTC.setChecked(victim.getProcedureCirculation().getTemperature_monitoring());
        binding.checkBoxCC.setChecked(victim.getProcedureCirculation().getCompression());
        binding.checkBoxCT.setChecked(victim.getProcedureCirculation().getTourniquet());
        binding.checkBoxCPB.setChecked(victim.getProcedureCirculation().getPelvic_belt());
        binding.checkBoxCV.setChecked(victim.getProcedureCirculation().getVenous_access());
        binding.checkBoxCP.setChecked(victim.getProcedureCirculation().getPatch());
        binding.checkBoxCE.setChecked(victim.getProcedureCirculation().getEcg());

        binding.checkBoxRCPW.setEnabled(isActive);
        binding.checkBoxRCPM.setEnabled(isActive);
        binding.checkBoxRCPN.setEnabled(isActive);

        binding.checkBoxVC.setEnabled(isActive);
        binding.checkBoxVO.setEnabled(isActive);
        binding.checkBoxVLT.setEnabled(isActive);
        binding.checkBoxVE.setEnabled(isActive);
        binding.checkBoxVLM.setEnabled(isActive);
        binding.checkBoxVM.setEnabled(isActive);
        binding.checkBoxVCpap.setEnabled(isActive);

        binding.checkBoxCTC.setEnabled(isActive);
        binding.checkBoxCC.setEnabled(isActive);
        binding.checkBoxCT.setEnabled(isActive);
        binding.checkBoxCPB.setEnabled(isActive);
        binding.checkBoxCV.setEnabled(isActive);
        binding.checkBoxCP.setEnabled(isActive);
        binding.checkBoxCE.setEnabled(isActive);
    }

    @Override
    public void onBackPressed() {
        String shocks = binding.editRCPShocks.getText().toString();
        String sbv = binding.includedRCPSBV.timeText.getText().toString();
        String siv = binding.includedRCPSIV.timeText.getText().toString();
        String recovery = binding.includedRCPRecovery.timeText.getText().toString();
        String downtime = binding.includedRCPDowntime.timeText.getText().toString();

        boolean shocksV = Validation.validateInt(shocks, 0, 10, true);
        boolean sbvV = Validation.validateTime(sbv, true);
        boolean sivV = Validation.validateTime(siv, true);
        boolean recoveryV = Validation.validateTime(recovery, true);
        boolean downtimeV = Validation.validateTime(downtime, true);

        if (shocksV && sbvV && sivV && recoveryV && downtimeV) {
            victim.getProcedureRCP().setNr_shocks(shocks.equals("") ? null : Integer.parseInt(shocks));

            if (!sbv.equals("")) {
                DateTime dateTime = DateTime.now();
                dateTime.setTime(sbv);
                victim.getProcedureRCP().setSBV_DAE(dateTime);
            } else {
                victim.getProcedureRCP().setSBV_DAE(null);
            }
            if (!sbv.equals("")) {
                DateTime dateTime = DateTime.now();
                dateTime.setTime(siv);
                victim.getProcedureRCP().setSIV_SAV(dateTime);
            } else {
                victim.getProcedureRCP().setSIV_SAV(null);
            }
            if (!sbv.equals("")) {
                DateTime dateTime = DateTime.now();
                dateTime.setTime(recovery);
                victim.getProcedureRCP().setRecovery(dateTime);
            } else {
                victim.getProcedureRCP().setRecovery(null);
            }
            if (!sbv.equals("")) {
                DateTime dateTime = DateTime.now();
                dateTime.setTime(downtime);
                victim.getProcedureRCP().setDowntime(dateTime);
            } else {
                victim.getProcedureRCP().setDowntime(null);
            }
        } else {
            if (!shocksV) {
                binding.editRCPShocks.setError(getString(R.string.invalid_value));
            }
            if (!sbvV) {
                binding.includedRCPSBV.timeText.setError(getString(R.string.invalid_time));
            }
            if (!sivV) {
                binding.includedRCPSBV.timeText.setError(getString(R.string.invalid_time));
            }
            if (!recoveryV) {
                binding.includedRCPSBV.timeText.setError(getString(R.string.invalid_time));
            }
            if (!downtimeV) {
                binding.includedRCPSBV.timeText.setError(getString(R.string.invalid_time));
            }
            return;
        }

        Intent intent = new Intent();
        if (this.isActive) {
            String rhythm = binding.editRCPRhythm.getText().toString().trim();
            victim.getProcedureRCP().setFirst_rhythm(rhythm.equals("") ? null : rhythm);

            victim.getProcedureRCP().setWitnessed(binding.checkBoxRCPW.isChecked());
            victim.getProcedureRCP().setMechanical_compressions(binding.checkBoxRCPM.isChecked());
            victim.getProcedureRCP().setPerformed(!binding.checkBoxRCPN.isChecked());

            victim.getProcedureVentilation().setClearance(binding.checkBoxVC.isChecked());
            victim.getProcedureVentilation().setOropharyngeal(binding.checkBoxVO.isChecked());
            victim.getProcedureVentilation().setLaryngeal_tube(binding.checkBoxVLT.isChecked());
            victim.getProcedureVentilation().setEndotracheal(binding.checkBoxVE.isChecked());
            victim.getProcedureVentilation().setLaryngeal_mask(binding.checkBoxVLM.isChecked());
            victim.getProcedureVentilation().setMechanical_ventilation(binding.checkBoxVM.isChecked());
            victim.getProcedureVentilation().setCpap(binding.checkBoxVCpap.isChecked());

            victim.getProcedureCirculation().setTemperature_monitoring(binding.checkBoxCTC.isChecked());
            victim.getProcedureCirculation().setCompression(binding.checkBoxCC.isChecked());
            victim.getProcedureCirculation().setTourniquet(binding.checkBoxCT.isChecked());
            victim.getProcedureCirculation().setPelvic_belt(binding.checkBoxCPB.isChecked());
            victim.getProcedureCirculation().setVenous_access(binding.checkBoxCV.isChecked());
            victim.getProcedureCirculation().setPatch(binding.checkBoxCP.isChecked());
            victim.getProcedureCirculation().setEcg(binding.checkBoxCE.isChecked());

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

    public void gotoTraumas(View view) {
        // todo
    }

    public void addPharmacy(View view) {
        // todo
    }
}