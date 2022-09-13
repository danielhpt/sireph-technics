package com.sireph.technics;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.async.post.AsyncPostPharmacy;
import com.sireph.technics.async.post.AsyncPostTrauma;
import com.sireph.technics.databinding.ActivityVictimProtocolsBinding;
import com.sireph.technics.dialogs.PharmacyDialogFragment;
import com.sireph.technics.dialogs.scales.ScaleCincinnatiDialogFragment;
import com.sireph.technics.dialogs.scales.ScaleMGAPDialogFragment;
import com.sireph.technics.dialogs.scales.ScalePROACSDialogFragment;
import com.sireph.technics.dialogs.scales.ScaleRTSDialogFragment;
import com.sireph.technics.dialogs.scales.race.ScaleRaceDialogFragment;
import com.sireph.technics.models.Victim;
import com.sireph.technics.models.procedures.Pharmacy;
import com.sireph.technics.models.procedures.Symptom;
import com.sireph.technics.utils.EditTextString;
import com.sireph.technics.utils.statics.Args;
import com.sireph.technics.utils.statics.Flag;

import java.util.List;

public class VictimProtocolsActivity extends AppCompatActivity implements ScaleCincinnatiDialogFragment.ScaleCincinnatiDialogListener,
        ScalePROACSDialogFragment.ScalePROACSDialogListener, ScaleRaceDialogFragment.ScaleRACEDialogListener,
        ScaleRTSDialogFragment.ScaleRTSDialogListener, ScaleMGAPDialogFragment.ScaleMGAPDialogListener, PharmacyDialogFragment.PharmacyDialogListener {
    private String token;
    private Victim victim;
    private boolean isActive;
    private ActivityVictimProtocolsBinding binding;
    private ScaleCincinnatiDialogFragment.CincinnatiScale cincinnati;
    private ScalePROACSDialogFragment.PROACSScale proacs;
    private ScaleRTSDialogFragment.RTSScale rts;
    private ScaleMGAPDialogFragment.MGAPScale mgap;
    private ScaleRaceDialogFragment.RACEScale race;
    private ActivityResultLauncher<Intent> startTrauma = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (this.isActive && result.getResultCode() == RESULT_OK) {
            Intent intent = result.getData();
            assert intent != null;
            List<Flag> flags = victim.getSymptom().update((Symptom) intent.getSerializableExtra(Args.ARG_SYMPTOM));
            // todo
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityVictimProtocolsBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        Intent intent = getIntent();
        this.token = intent.getStringExtra(Args.ARG_TOKEN);
        this.victim = (Victim) intent.getSerializableExtra(Args.ARG_VICTIM);
        this.isActive = intent.getBooleanExtra(Args.ARG_ACTIVE, false);
        setTitle(intent.getStringExtra(Args.ARG_TITLE) + " > " + getString(R.string.protocols_therapeutics));

        binding.checkBoxImmobilization.setChecked(victim.getProcedureProtocol().getImmobilization());
        binding.checkBoxTEPH.setChecked(victim.getProcedureProtocol().getTEPH());
        binding.checkBoxSIV.setChecked(victim.getProcedureProtocol().getSIV());
        binding.checkBoxAVC.setChecked(victim.getProcedureProtocol().getVV_AVC());
        binding.checkBoxCoronary.setChecked(victim.getProcedureProtocol().getVV_coronary());
        binding.checkBoxSepsis.setChecked(victim.getProcedureProtocol().getVV_sepsis());
        binding.checkBoxTrauma.setChecked(victim.getProcedureProtocol().getVV_trauma());
        binding.checkBoxPCR.setChecked(victim.getProcedureProtocol().getVV_PCR());

        binding.checkBoxImmobilization.setEnabled(isActive);
        binding.checkBoxTEPH.setEnabled(isActive);
        binding.checkBoxSIV.setEnabled(isActive);
        binding.checkBoxAVC.setEnabled(isActive);
        binding.checkBoxCoronary.setEnabled(isActive);
        binding.checkBoxSepsis.setEnabled(isActive);
        binding.checkBoxTrauma.setEnabled(isActive);
        binding.checkBoxPCR.setEnabled(isActive);

        Integer i = victim.getProcedureScale().getCincinatti();
        EditTextString.editTextString(binding.textCincinnati, i == null ? null : Integer.toString(i), isActive);
        i = victim.getProcedureScale().getPROACS();
        EditTextString.editTextString(binding.textPROACS, i == null ? null : Integer.toString(i), isActive);
        i = victim.getProcedureScale().getRTS();
        EditTextString.editTextString(binding.textRTS, i == null ? null : Integer.toString(i), isActive);
        i = victim.getProcedureScale().getMGAP();
        EditTextString.editTextString(binding.textMGAP, i == null ? null : Integer.toString(i), isActive);
        i = victim.getProcedureScale().getRACE();
        EditTextString.editTextString(binding.textRACE, i == null ? null : Integer.toString(i), isActive);

        EditTextString.editTextString(binding.textSignsSymptoms, victim.getSymptom().getComments(), isActive);

        cincinnati = null;
        proacs = null;
        rts = null;
        mgap = null;
        race = new ScaleRaceDialogFragment.RACEScale();

        this.binding.textCincinnati.setOnClickListener(v -> {
            ScaleCincinnatiDialogFragment fragment = new ScaleCincinnatiDialogFragment(this.cincinnati, this);
            fragment.show(getSupportFragmentManager(), "ScaleCincinnatiDialogFragment");
        });
        this.binding.textPROACS.setOnClickListener(v -> {
            ScalePROACSDialogFragment fragment = new ScalePROACSDialogFragment(this.proacs, this);
            fragment.show(getSupportFragmentManager(), "ScalePROACSDialogFragment");
        });
        this.binding.textRTS.setOnClickListener(v -> {
            ScaleRTSDialogFragment fragment = new ScaleRTSDialogFragment(this.rts, this);
            fragment.show(getSupportFragmentManager(), "ScaleRTSDialogFragment");
        });
        this.binding.textMGAP.setOnClickListener(v -> {
            ScaleMGAPDialogFragment fragment = new ScaleMGAPDialogFragment(this.mgap, this);
            fragment.show(getSupportFragmentManager(), "ScaleMGAPDialogFragment");
        });
        this.binding.textRACE.setOnClickListener(v -> {
            ScaleRaceDialogFragment fragment = new ScaleRaceDialogFragment(this.race, this);
            fragment.show(getSupportFragmentManager(), "ScaleRaceDialogFragment");
        });

        binding.buttonAddPharmacy.setEnabled(isActive);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        if (this.isActive) {
            victim.getProcedureProtocol().setImmobilization(binding.checkBoxImmobilization.isChecked());
            victim.getProcedureProtocol().setTEPH(binding.checkBoxTEPH.isChecked());
            victim.getProcedureProtocol().setSIV(binding.checkBoxSIV.isChecked());
            victim.getProcedureProtocol().setVV_AVC(binding.checkBoxAVC.isChecked());
            victim.getProcedureProtocol().setVV_coronary(binding.checkBoxCoronary.isChecked());
            victim.getProcedureProtocol().setVV_sepsis(binding.checkBoxSepsis.isChecked());
            victim.getProcedureProtocol().setVV_trauma(binding.checkBoxTrauma.isChecked());
            victim.getProcedureProtocol().setVV_PCR(binding.checkBoxPCR.isChecked());

            String s = binding.textCincinnati.getText().toString();
            if (!s.equals("")) {
                victim.getProcedureScale().setCincinatti(Integer.parseInt(s));
            }
            s = binding.textPROACS.getText().toString();
            if (!s.equals("")) {
                victim.getProcedureScale().setPROACS(Integer.parseInt(s));
            }
            s = binding.textRTS.getText().toString();
            if (!s.equals("")) {
                victim.getProcedureScale().setRTS(Integer.parseInt(s));
            }
            s = binding.textMGAP.getText().toString();
            if (!s.equals("")) {
                victim.getProcedureScale().setMGAP(Integer.parseInt(s));
            }
            s = binding.textRACE.getText().toString();
            if (!s.equals("")) {
                victim.getProcedureScale().setRACE(Integer.parseInt(s));
            }

            victim.getSymptom().setComments(binding.textSignsSymptoms.getText().toString());

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

    public void addPharmacy(View view) {
        PharmacyDialogFragment dialog = new PharmacyDialogFragment(this);
        dialog.show(getSupportFragmentManager(), "PharmacyDialogFragment");
    }

    public void gotoTraumas(View view) {
        Intent intent = new Intent(this, VictimTraumaActivity.class);
        intent.putExtra(Args.ARG_TOKEN, this.token);
        intent.putExtra(Args.ARG_SYMPTOM, this.victim.getSymptom());
        intent.putExtra(Args.ARG_VICTIM_ID, this.victim.getId());
        intent.putExtra(Args.ARG_TITLE, getTitle());
        intent.putExtra(Args.ARG_ACTIVE, this.isActive);
        startTrauma.launch(intent);
    }

    @Override
    public void onPharmacyDialogOk(Pharmacy pharmacy) {
        new AsyncPostPharmacy(result -> { }).execute(token, victim.getId(), pharmacy);
        victim.addPharmacy(pharmacy);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onScaleCincinnatiDialogOk(ScaleCincinnatiDialogFragment.CincinnatiScale scale) {
        cincinnati = scale;
        binding.textCincinnati.setText(Integer.toString(cincinnati.getValue()));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onScaleMGAPDialogOk(ScaleMGAPDialogFragment.MGAPScale scale) {
        mgap = scale;
        binding.textMGAP.setText(Integer.toString(mgap.getValue()));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onScalePROACSDialogOk(ScalePROACSDialogFragment.PROACSScale scale) {
        proacs = scale;
        binding.textPROACS.setText(Integer.toString(proacs.getValue()));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onScaleRTSDialogOk(ScaleRTSDialogFragment.RTSScale scale) {
        rts = scale;
        binding.textRTS.setText(Integer.toString(rts.getValue()));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onScaleRACEDialogOk(ScaleRaceDialogFragment.RACEScale scale) {
        race = scale;
        binding.textRACE.setText(Integer.toString(race.value));
    }
}