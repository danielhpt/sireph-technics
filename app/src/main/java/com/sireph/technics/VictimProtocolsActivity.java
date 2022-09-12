package com.sireph.technics;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.databinding.ActivityVictimProtocolsBinding;
import com.sireph.technics.dialogs.PharmacyDialogFragment;
import com.sireph.technics.dialogs.scales.ScaleCincinnatiDialogFragment;
import com.sireph.technics.dialogs.scales.ScaleMGAPDialogFragment;
import com.sireph.technics.dialogs.scales.ScalePROACSDialogFragment;
import com.sireph.technics.dialogs.scales.ScaleRTSDialogFragment;
import com.sireph.technics.dialogs.scales.race.ScaleRaceDialogFragment;
import com.sireph.technics.models.Technician;
import com.sireph.technics.models.Victim;
import com.sireph.technics.models.procedures.Pharmacy;
import com.sireph.technics.utils.statics.Args;

public class VictimProtocolsActivity extends AppCompatActivity implements ScaleCincinnatiDialogFragment.ScaleCincinnatiDialogListener,
        ScalePROACSDialogFragment.ScalePROACSDialogListener, ScaleRaceDialogFragment.ScaleRACEDialogListener,
        ScaleRTSDialogFragment.ScaleRTSDialogListener, ScaleMGAPDialogFragment.ScaleMGAPDialogListener, PharmacyDialogFragment.PharmacyDialogListener {
    private String token;
    private Technician technician;
    private Victim victim;
    private boolean isActive;
    private ActivityVictimProtocolsBinding binding;
    private ActivityResultLauncher<Intent> startTrauma = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (this.isActive && result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
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
        this.technician = (Technician) intent.getSerializableExtra(Args.ARG_TECHNICIAN);
        this.victim = (Victim) intent.getSerializableExtra(Args.ARG_VICTIM);
        this.isActive = intent.getBooleanExtra(Args.ARG_ACTIVE, false);
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

    @Override
    public void onPharmacyDialogOk(Pharmacy pharmacy) {
        // todo
    }

    @Override
    public void onScaleCincinnatiDialogOk(ScaleCincinnatiDialogFragment.CincinnatiScale scale) {
        // todo
    }

    @Override
    public void onScaleMGAPDialogOk(ScaleMGAPDialogFragment.MGAPScale scale) {
        // todo
    }

    @Override
    public void onScalePROACSDialogOk(ScalePROACSDialogFragment.PROACSScale scale) {
        // todo
    }

    @Override
    public void onScaleRTSDialogOk(ScaleRTSDialogFragment.RTSScale scale) {
        // todo
    }

    @Override
    public void onScaleRACEDialogOk(ScaleRaceDialogFragment.RACEScale scale) {
        // todo
    }
}