package com.sireph.technics.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sireph.technics.R;
import com.sireph.technics.databinding.DialogTransportBinding;
import com.sireph.technics.models.Hospital;
import com.sireph.technics.models.enums.NonTransportReason;
import com.sireph.technics.models.enums.TypeOfTransport;
import com.sireph.technics.models.victim.Victim;
import com.sireph.technics.utils.EditTextString;
import com.sireph.technics.utils.RemoveAccents;
import com.sireph.technics.utils.TextChangedWatcher;

import java.util.List;

public class TransportDialog extends DialogFragment {
    private final Victim victim;
    private final List<Hospital> hospitals;
    private final boolean isActive;
    TransportDialogListener listener;
    private DialogTransportBinding binding;

    public TransportDialog(Victim victim, List<Hospital> hospitals, boolean isActive, TransportDialogListener listener) {
        this.victim = victim;
        this.isActive = isActive;
        this.hospitals = hospitals;
        this.listener = listener;
    }

    @SuppressLint({"InflateParams", "DefaultLocale"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        this.binding = DialogTransportBinding.inflate(requireActivity().getLayoutInflater());

        if (this.isActive) {
            ArrayAdapter<Hospital> adapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_item, this.hospitals);
            this.binding.transportDestination.setThreshold(1);
            this.binding.transportDestination.setAdapter(adapter);
            this.binding.transportDestination.addTextChangedListener(new TextChangedWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() > binding.transportDestination.getThreshold() && !binding.transportDestination.isPerformingCompletion() && !binding.transportDestination.isPopupShowing()) {
                        binding.transportDestination.setError(getString(R.string.invalid_hospital));
                    } else {
                        binding.transportDestination.setError(null);
                    }
                }
            });
        } else {
            this.binding.transportDestination.setEnabled(false);
            this.binding.medicalFollowup.setEnabled(false);
            for (int i = 0; i < this.binding.radioGroupTransportType.getChildCount(); i++) {
                this.binding.radioGroupTransportType.getChildAt(i).setEnabled(false);
            }
            for (int i = 0; i < this.binding.radioGroupNoTransport.getChildCount(); i++) {
                this.binding.radioGroupNoTransport.getChildAt(i).setEnabled(false);
            }
        }

        builder.setView(this.binding.getRoot())
                .setTitle(R.string.transport)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                });
        return builder.create();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onStart() {
        super.onStart();

        this.binding.radioGroupTransportType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonNoTransport) {
                this.binding.transportDestination.setEnabled(false);
                this.binding.episodeNumber.setEnabled(false);
                this.binding.medicalFollowup.setEnabled(false);
                for (int i = 0; i < this.binding.radioGroupNoTransport.getChildCount(); i++) {
                    this.binding.radioGroupNoTransport.getChildAt(i).setEnabled(true);
                }
            } else {
                this.binding.transportDestination.setEnabled(true);
                this.binding.episodeNumber.setEnabled(true);
                this.binding.medicalFollowup.setEnabled(true);
                for (int i = 0; i < this.binding.radioGroupNoTransport.getChildCount(); i++) {
                    this.binding.radioGroupNoTransport.getChildAt(i).setEnabled(false);
                }
            }
        });

        if (this.victim.getHospital() != null) {
            this.binding.transportDestination.setText(this.victim.getHospital().toString());
            binding.transportDestination.setError(null);
        }
        if (this.victim.getEpisode_number() != null) {
            EditTextString.editTextString(this.binding.episodeNumber, this.victim.getEpisode_number().toString(), this.isActive);
        }
        this.binding.medicalFollowup.setChecked(this.victim.getMedical_followup());
        if (this.victim.getType_of_transport() != null) {
            switch (this.victim.getType_of_transport()) {
                case PRIMARY:
                    this.binding.radioButtonPrimary.setChecked(true);
                    break;
                case SECONDARY:
                    this.binding.radioButtonSecondary.setChecked(true);
                    break;
                case NO_TRANSPORT:
                    this.binding.radioButtonNoTransport.setChecked(true);
                    if (this.victim.getNon_transport_reason() != null) {
                        switch (this.victim.getNon_transport_reason()) {
                            case ABANDONED:
                                this.binding.radioButtonAbandoned.setChecked(true);
                                break;
                            case MEDIC:
                                this.binding.radioButtonMedic.setChecked(true);
                                break;
                            case DEATH:
                                this.binding.radioButtonDeath.setChecked(true);
                                break;
                            case REFUSED_SIGNED:
                                this.binding.radioButtonRefusedS.setChecked(true);
                                break;
                            case REFUSED_NOT_SIGNED:
                                this.binding.radioButtonRefusedNS.setChecked(true);
                                break;
                            case DEACTIVATION:
                                this.binding.radioButtonDeativation.setChecked(true);
                        }
                    }
            }
        }

        AlertDialog dialog = (AlertDialog) getDialog();

        if (isActive) {
            assert dialog != null;
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                int typeId = this.binding.radioGroupTransportType.getCheckedRadioButtonId();
                if (typeId != -1) {
                    if (typeId != R.id.radioButtonNoTransport) {
                        String hospitalName = RemoveAccents.stripAccents(binding.transportDestination.getText().toString().toLowerCase());
                        Hospital hospital = null;
                        for (Hospital h : this.hospitals) {
                            if (RemoveAccents.stripAccents(h.toString().toLowerCase()).equals(hospitalName)) {
                                hospital = h;
                                break;
                            }
                        }
                        if (hospital != null) {
                            TypeOfTransport type = TypeOfTransport.fromId(typeId == R.id.radioButtonPrimary ? 1 : 2);
                            boolean medical = this.binding.medicalFollowup.isChecked();
                            Integer episode;
                            String episodeText = this.binding.episodeNumber.getText().toString();
                            if (episodeText.equals("")) {
                                episode = null;
                            } else {
                                try {
                                    episode = Integer.valueOf(episodeText);
                                } catch (NumberFormatException e) {
                                    this.binding.episodeNumber.setError(getString(R.string.invalid_nr_episode));
                                    return;
                                }
                            }
                            this.listener.onTransportDialogOk(hospital, type, null, episode, medical);
                            dialog.dismiss();
                        } else {
                            this.binding.transportDestination.setError(getString(R.string.invalid_hospital));
                        }
                    } else {
                        NonTransportReason reason;
                        switch (this.binding.radioGroupNoTransport.getCheckedRadioButtonId()) {
                            case R.id.radioButtonAbandoned:
                                reason = NonTransportReason.ABANDONED;
                                break;
                            case R.id.radioButtonMedic:
                                reason = NonTransportReason.MEDIC;
                                break;
                            case R.id.radioButtonDeath:
                                reason = NonTransportReason.DEATH;
                                break;
                            case R.id.radioButtonRefusedS:
                                reason = NonTransportReason.REFUSED_SIGNED;
                                break;
                            case R.id.radioButtonRefusedNS:
                                reason = NonTransportReason.REFUSED_NOT_SIGNED;
                                break;
                            case R.id.radioButtonDeativation:
                                reason = NonTransportReason.DEACTIVATION;
                                break;
                            default:
                                this.binding.radioButtonDeativation.setError(getString(R.string.required_field));
                                return;
                        }
                        this.listener.onTransportDialogOk(null, TypeOfTransport.NO_TRANSPORT, reason, null, false);
                        dialog.dismiss();
                    }
                }
            });
        }
    }

    public interface TransportDialogListener {
        void onTransportDialogOk(Hospital hospital, TypeOfTransport type, NonTransportReason reason, Integer episode, boolean followup);
    }
}
