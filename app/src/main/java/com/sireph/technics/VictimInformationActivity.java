package com.sireph.technics;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.databinding.ActivityVictimInformationBinding;
import com.sireph.technics.models.Technician;
import com.sireph.technics.models.Victim;
import com.sireph.technics.utils.statics.Args;

public class VictimInformationActivity extends AppCompatActivity {
    private String token;
    private Technician technician;
    private Victim victim;
    private boolean isActive;
    private ActivityVictimInformationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityVictimInformationBinding.inflate(getLayoutInflater());
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
}