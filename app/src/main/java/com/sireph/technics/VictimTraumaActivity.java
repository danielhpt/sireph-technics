package com.sireph.technics;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.async.post.AsyncPostTrauma;
import com.sireph.technics.databinding.ActivityVictimTraumaBinding;
import com.sireph.technics.dialogs.TraumaDialogFragment;
import com.sireph.technics.models.enums.BodyPart;
import com.sireph.technics.models.procedures.Symptom;
import com.sireph.technics.models.procedures.Trauma;
import com.sireph.technics.utils.statics.Args;

public class VictimTraumaActivity extends AppCompatActivity implements TraumaDialogFragment.TraumaDialogListener {
    private String token;
    private boolean isActive;
    private Symptom symptom;
    private int victim_id;
    private ActivityVictimTraumaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityVictimTraumaBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        Intent intent = getIntent();
        this.token = intent.getStringExtra(Args.ARG_TOKEN);
        this.symptom = (Symptom) intent.getSerializableExtra(Args.ARG_SYMPTOM);
        this.isActive = intent.getBooleanExtra(Args.ARG_ACTIVE, false);
        this.victim_id = intent.getIntExtra(Args.ARG_VICTIM_ID, -1);
        setTitle(intent.getStringExtra(Args.ARG_TITLE) + " > " + getString(R.string.traumas));

        setBurnArea();

        binding.buttonSkull.setEnabled(isActive);
        binding.buttonFace.setEnabled(isActive);
        binding.buttonCervical.setEnabled(isActive);
        binding.buttonThoraxAR.setEnabled(isActive);
        binding.buttonLimbSR.setEnabled(isActive);
        binding.buttonHypochondriumR.setEnabled(isActive);
        binding.buttonFlankR.setEnabled(isActive);
        binding.buttonIliacR.setEnabled(isActive);
        binding.buttonGenitals.setEnabled(isActive);
        binding.buttonLimbIR.setEnabled(isActive);
        binding.buttonThoraxAL.setEnabled(isActive);
        binding.buttonLimbSL.setEnabled(isActive);
        binding.buttonEpigastrium.setEnabled(isActive);
        binding.buttonHypochondriumL.setEnabled(isActive);
        binding.buttonMesogastrium.setEnabled(isActive);
        binding.buttonFlankL.setEnabled(isActive);
        binding.buttonIliacL.setEnabled(isActive);
        binding.buttonHypogastrium.setEnabled(isActive);
        binding.buttonLimbIL.setEnabled(isActive);
        binding.buttonThoraxP.setEnabled(isActive);
        binding.buttonLumbar.setEnabled(isActive);
        binding.buttonSacral.setEnabled(isActive);
        binding.buttonPelvis.setEnabled(isActive);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        if (this.isActive) {
            intent.putExtra(Args.ARG_SYMPTOM, this.symptom);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @SuppressLint("SetTextI18n")
    private void setBurnArea() {
        if (this.symptom.getTotal_burn_area() > 0) {
            this.binding.totalBurnArea.setText(Double.toString(this.symptom.getTotal_burn_area()));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    public void addTrauma(View view) {
        BodyPart bodyPart;
        switch (view.getId()) {
            case R.id.buttonSkull:
                bodyPart = BodyPart.SKULL;
                break;
            case R.id.buttonFace:
                bodyPart = BodyPart.FACE;
                break;
            case R.id.buttonCervical:
                bodyPart = BodyPart.CERVICAL;
                break;
            case R.id.buttonThoraxAR:
                bodyPart = BodyPart.THORAX_A_R;
                break;
            case R.id.buttonLimbSR:
                bodyPart = BodyPart.LIMB_S_R;
                break;
            case R.id.buttonHypochondriumR:
                bodyPart = BodyPart.HYPOCHONDRIUM_R;
                break;
            case R.id.buttonFlankR:
                bodyPart = BodyPart.FLANK_R;
                break;
            case R.id.buttonIliacR:
                bodyPart = BodyPart.ILIAC_FOSSA_R;
                break;
            case R.id.buttonGenitals:
                bodyPart = BodyPart.GENITALS;
                break;
            case R.id.buttonLimbIR:
                bodyPart = BodyPart.LIMB_I_R;
                break;
            case R.id.buttonThoraxAL:
                bodyPart = BodyPart.THORAX_A_L;
                break;
            case R.id.buttonLimbSL:
                bodyPart = BodyPart.LIMB_S_L;
                break;
            case R.id.buttonEpigastrium:
                bodyPart = BodyPart.EPIGASTRIUM;
                break;
            case R.id.buttonHypochondriumL:
                bodyPart = BodyPart.HYPOCHONDRIUM_L;
                break;
            case R.id.buttonMesogastrium:
                bodyPart = BodyPart.MESOGASTRIUM;
                break;
            case R.id.buttonFlankL:
                bodyPart = BodyPart.FLANK_L;
                break;
            case R.id.buttonIliacL:
                bodyPart = BodyPart.ILIAC_FOSSA_L;
                break;
            case R.id.buttonHypogastrium:
                bodyPart = BodyPart.HYPOGASTRIUM;
                break;
            case R.id.buttonLimbIL:
                bodyPart = BodyPart.LIMB_I_L;
                break;
            case R.id.buttonThoraxP:
                bodyPart = BodyPart.THORAX_P;
                break;
            case R.id.buttonLumbar:
                bodyPart = BodyPart.LUMBAR;
                break;
            case R.id.buttonSacral:
                bodyPart = BodyPart.SACRAL;
                break;
            case R.id.buttonPelvis:
                bodyPart = BodyPart.PELVIS;
                break;
            default:
                bodyPart = BodyPart.EMPTY;
        }
        TraumaDialogFragment dialog = new TraumaDialogFragment(bodyPart, this);
        dialog.show(getSupportFragmentManager(), "TraumaDialogFragment");
    }

    @Override
    public void onTraumaDialogOk(Trauma trauma) {
        new AsyncPostTrauma(result -> { }).execute(token, victim_id, trauma);
        this.symptom.addTrauma(trauma);
        setBurnArea();
    }
}