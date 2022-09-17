package com.sireph.technics.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.R;
import com.sireph.technics.async.post.victim.AsyncPostVictimPharmacy;
import com.sireph.technics.databinding.ActivityVictimProtocolsBinding;
import com.sireph.technics.dialogs.PharmacyDialog;
import com.sireph.technics.dialogs.scales.ScaleCincinnatiDialog;
import com.sireph.technics.dialogs.scales.ScaleMGAPDialog;
import com.sireph.technics.dialogs.scales.ScalePROACSDialog;
import com.sireph.technics.dialogs.scales.ScaleRTSDialog;
import com.sireph.technics.dialogs.scales.race.ScaleRaceDialog;
import com.sireph.technics.models.victim.Pharmacy;
import com.sireph.technics.models.victim.Victim;
import com.sireph.technics.table.components.RowHeader;
import com.sireph.technics.table.generators.PharmaciesToTable;
import com.sireph.technics.utils.EditTextString;
import com.sireph.technics.utils.statics.Args;

import java.util.Objects;

public class VictimProtocolsActivity extends AppCompatActivity implements ScaleCincinnatiDialog.ScaleCincinnatiDialogListener,
        ScalePROACSDialog.ScalePROACSDialogListener, ScaleRaceDialog.ScaleRACEDialogListener,
        ScaleRTSDialog.ScaleRTSDialogListener, ScaleMGAPDialog.ScaleMGAPDialogListener, PharmacyDialog.PharmacyDialogListener {
    private String token;
    private Victim victim;
    private boolean isActive;
    private ActivityVictimProtocolsBinding binding;
    private ScaleCincinnatiDialog.CincinnatiScale cincinnati;
    private ScalePROACSDialog.PROACSScale proacs;
    private ScaleRTSDialog.RTSScale rts;
    private ScaleMGAPDialog.MGAPScale mgap;
    private ScaleRaceDialog.RACEScale race;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityVictimProtocolsBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
        setSupportActionBar(binding.included.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        this.token = intent.getStringExtra(Args.ARG_TOKEN);
        this.victim = (Victim) intent.getSerializableExtra(Args.ARG_VICTIM);
        this.isActive = intent.getBooleanExtra(Args.ARG_IS_ACTIVE, false);
        binding.included.toolbar.setTitle(intent.getStringExtra(Args.ARG_TITLE) + " > " + getString(R.string.protocols_therapeutics));

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
        race = new ScaleRaceDialog.RACEScale();

        this.binding.textCincinnati.setOnClickListener(v -> {
            ScaleCincinnatiDialog fragment = new ScaleCincinnatiDialog(this.cincinnati, this);
            fragment.show(getSupportFragmentManager(), "ScaleCincinnatiDialogFragment");
        });
        this.binding.textPROACS.setOnClickListener(v -> {
            ScalePROACSDialog fragment = new ScalePROACSDialog(this.proacs, this);
            fragment.show(getSupportFragmentManager(), "ScalePROACSDialogFragment");
        });
        this.binding.textRTS.setOnClickListener(v -> {
            ScaleRTSDialog fragment = new ScaleRTSDialog(this.rts, this);
            fragment.show(getSupportFragmentManager(), "ScaleRTSDialogFragment");
        });
        this.binding.textMGAP.setOnClickListener(v -> {
            ScaleMGAPDialog fragment = new ScaleMGAPDialog(this.mgap, this);
            fragment.show(getSupportFragmentManager(), "ScaleMGAPDialogFragment");
        });
        this.binding.textRACE.setOnClickListener(v -> {
            ScaleRaceDialog fragment = new ScaleRaceDialog(this.race, this);
            fragment.show(getSupportFragmentManager(), "ScaleRaceDialogFragment");
        });

        binding.buttonAddPharmacy.setEnabled(isActive);

        new PharmaciesToTable().setupTable(binding.contentContainer, victim.getPharmacies(), this);
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
        PharmacyDialog dialog = new PharmacyDialog(this);
        dialog.show(getSupportFragmentManager(), "PharmacyDialogFragment");
    }

    @Override
    public void onPharmacyDialogOk(Pharmacy pharmacy) {
        new AsyncPostVictimPharmacy(result -> {
        }).execute(token, victim.getId(), pharmacy);
        victim.addPharmacy(pharmacy);
        //noinspection unchecked
        Objects.requireNonNull(binding.contentContainer.getAdapter()).addRow(victim.getPharmacies().size() - 1,
                new RowHeader(Integer.toString(victim.getPharmacies().size())), pharmacy.toCellList());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onScaleCincinnatiDialogOk(ScaleCincinnatiDialog.CincinnatiScale scale) {
        cincinnati = scale;
        binding.textCincinnati.setText(Integer.toString(cincinnati.getValue()));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onScaleMGAPDialogOk(ScaleMGAPDialog.MGAPScale scale) {
        mgap = scale;
        binding.textMGAP.setText(Integer.toString(mgap.getValue()));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onScalePROACSDialogOk(ScalePROACSDialog.PROACSScale scale) {
        proacs = scale;
        binding.textPROACS.setText(Integer.toString(proacs.getValue()));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onScaleRTSDialogOk(ScaleRTSDialog.RTSScale scale) {
        rts = scale;
        binding.textRTS.setText(Integer.toString(rts.getValue()));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onScaleRACEDialogOk(ScaleRaceDialog.RACEScale scale) {
        race = scale;
        binding.textRACE.setText(Integer.toString(race.value));
    }
}