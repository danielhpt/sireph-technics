package com.sireph.technics;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sireph.technics.databinding.ActivityVictimInformationBinding;
import com.sireph.technics.models.Technician;
import com.sireph.technics.models.Victim;

public class VictimInformationActivity extends AppCompatActivity {
    public static String ARG_TOKEN = "1", ARG_TECHNICIAN = "2", ARG_VICTIM = "3", ARG_ACTIVE = "4";
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
        this.token = intent.getStringExtra(ARG_TOKEN);
        this.technician = (Technician) intent.getSerializableExtra(ARG_TECHNICIAN);
        this.victim = (Victim) intent.getSerializableExtra(ARG_VICTIM);
        this.isActive = intent.getBooleanExtra(ARG_ACTIVE, false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}