package com.sireph.technics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;

import com.sireph.technics.dialogs.StatusDialogFragment;
import com.sireph.technics.dialogs.TransportDialogFragment;

public class OccurrenceActivity extends AppCompatActivity implements StatusDialogFragment.StatusDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occurrence);
    }

    @Override
    public void showStatusDialog() {
        StatusDialogFragment dialog = new StatusDialogFragment();
        dialog.show(getSupportFragmentManager(), "StatusDialogFragment");
    }

    @Override
    public void onStatusDialogOk(DialogFragment dialog) {

    }

    @Override
    public void onStatusDialogCancel(DialogFragment dialog) {}
}