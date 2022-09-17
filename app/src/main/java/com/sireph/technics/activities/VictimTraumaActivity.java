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
import com.sireph.technics.async.post.victim.AsyncPostVictimCompiledTrauma;
import com.sireph.technics.databinding.ActivityVictimTraumaBinding;
import com.sireph.technics.dialogs.TraumaDialog;
import com.sireph.technics.models.enums.BodyPart;
import com.sireph.technics.models.victim.symptom.CompiledTrauma;
import com.sireph.technics.models.victim.symptom.Symptom;
import com.sireph.technics.table.TableViewAdapter;
import com.sireph.technics.table.components.Cell;
import com.sireph.technics.table.components.RowHeader;
import com.sireph.technics.table.generators.CompiledTraumasToTable;
import com.sireph.technics.utils.statics.Args;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class VictimTraumaActivity extends AppCompatActivity implements TraumaDialog.TraumaDialogListener {
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
        setSupportActionBar(binding.included.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        this.token = intent.getStringExtra(Args.ARG_TOKEN);
        this.symptom = (Symptom) intent.getSerializableExtra(Args.ARG_SYMPTOM);
        this.isActive = intent.getBooleanExtra(Args.ARG_IS_ACTIVE, false);
        this.victim_id = intent.getIntExtra(Args.ARG_VICTIM_ID, -1);
        binding.included.toolbar.setTitle(intent.getStringExtra(Args.ARG_TITLE) + " > " + getString(R.string.traumas));

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

        new CompiledTraumasToTable().setupTable(binding.contentContainer, symptom.getCompiledTraumas(), this);
        binding.contentContainer.setRowHeaderWidth(0);
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
            intent.putExtra(Args.ARG_SYMPTOM, this.symptom);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void setBurnArea() {
        double d = this.symptom.getTotal_burn_area();
        long l = (long) d;
        this.binding.totalBurnArea.setText((d == l ? String.format("%d", l) : String.format("%s", d)) + " %");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    public void addTrauma(@NonNull View view) {
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
        TraumaDialog dialog = new TraumaDialog(bodyPart, this);
        dialog.show(getSupportFragmentManager(), "TraumaDialogFragment");
    }

    @Override
    public void onTraumaDialogOk(CompiledTrauma compiledTrauma) {
        new AsyncPostVictimCompiledTrauma(result -> {
        }).execute(token, victim_id, compiledTrauma);
        int sizeBefore = symptom.getCompiledTraumas().size();
        this.symptom.addCompiledTrauma(compiledTrauma);
        setBurnArea();
        if (sizeBefore < symptom.getCompiledTraumas().size()) {
            //noinspection unchecked
            Objects.requireNonNull(binding.contentContainer.getAdapter()).addRow(symptom.getCompiledTraumas().size() - 1,
                    new RowHeader(compiledTrauma.getBodyPart().toString()), compiledTrauma.toCellList());
        } else {
            String x = "X";
            String mark = compiledTrauma.isPenetrating() ? "X" : "O";
            TableViewAdapter adapter = (TableViewAdapter) binding.contentContainer.getAdapter();
            assert adapter != null;
            List<Cell> columnItems = adapter.getCellColumnItems(0);
            int i;
            List<Cell> rowItems = new ArrayList<>();
            for (i = 0; i < columnItems.size(); i++) {
                if (Objects.equals(columnItems.get(i).getData(), compiledTrauma.getBodyPart().toString())) {
                    boolean containsX = false;
                    rowItems = adapter.getCellRowItems(i);
                    assert rowItems != null;
                    for (Cell c : rowItems) {
                        if (Objects.equals(c.getData(), x)) {
                            containsX = true;
                            break;
                        }
                    }
                    if (containsX == compiledTrauma.isPenetrating()) {
                        break;
                    }
                }
            }
            boolean[] types = {compiledTrauma.isFracture(), compiledTrauma.isContusion(), compiledTrauma.isWound(), compiledTrauma.isHaemorrhage(),
                    compiledTrauma.isBurn(), compiledTrauma.isPain()};
            String[] marks = {mark, "1", "2", "3"};
            for (int j = 1; j < rowItems.size(); j++) {
                if (types[j - 1] && Arrays.stream(marks).noneMatch(rowItems.get(j).getData()::equals)) {
                    if (j == 5) {
                        String degree = "";
                        switch (compiledTrauma.getBurnDegree()) {
                            case G1:
                                degree = "1";
                                break;
                            case G2:
                                degree = "2";
                                break;
                            case G3:
                                degree = "3";
                                break;
                        }
                        adapter.changeCellItem(j, i, new Cell(degree));
                    } else {
                        adapter.changeCellItem(j, i, new Cell(mark));
                    }
                }
            }
        }
    }
}