package com.sireph.technics.models.procedures;

import static com.sireph.technics.utils.ValueFromJson.boolFromJson;
import static com.sireph.technics.utils.ValueFromJson.intFromJson;

import androidx.annotation.NonNull;

import com.sireph.technics.models._BaseModel;
import com.sireph.technics.utils.statics.Flag;
import com.sireph.technics.utils.statics.TypeOfJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProcedureProtocol extends _BaseModel<ProcedureProtocol> {
    private boolean immobilization;
    private boolean TEPH;
    private boolean SIV;
    private boolean VV_AVC;
    private boolean VV_coronary;
    private boolean VV_sepsis;
    private boolean VV_trauma;
    private boolean VV_PCR;

    public ProcedureProtocol(JSONObject json) {
        this.id = intFromJson(json, "victim", -1);
        this.immobilization = boolFromJson(json, "immobilization", false);
        this.TEPH = boolFromJson(json, "TEPH", false);
        this.SIV = boolFromJson(json, "SIV", false);
        this.VV_AVC = boolFromJson(json, "VV_AVC", false);
        this.VV_coronary = boolFromJson(json, "VV_coronary", false);
        this.VV_sepsis = boolFromJson(json, "VV_sepsis", false);
        this.VV_trauma = boolFromJson(json, "VV_trauma", false);
        this.VV_PCR = boolFromJson(json, "VV_PCR", false);
    }

    public ProcedureProtocol(boolean immobilization, boolean TEPH, boolean SIV, boolean VV_AVC, boolean VV_coronary, boolean VV_sepsis,
                             boolean VV_trauma, boolean VV_PCR) {
        this.immobilization = immobilization;
        this.TEPH = TEPH;
        this.SIV = SIV;
        this.VV_AVC = VV_AVC;
        this.VV_coronary = VV_coronary;
        this.VV_sepsis = VV_sepsis;
        this.VV_trauma = VV_trauma;
        this.VV_PCR = VV_PCR;
    }

    public ProcedureProtocol() {
        this.immobilization = false;
        this.TEPH = false;
        this.SIV = false;
        this.VV_AVC = false;
        this.VV_coronary = false;
        this.VV_sepsis = false;
        this.VV_trauma = false;
        this.VV_PCR = false;
    }

    @Override
    public JSONObject toJson(@NonNull TypeOfJson type) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("immobilization", this.immobilization);
        json.put("TEPH", this.TEPH);
        json.put("SIV", this.SIV);
        json.put("VV_AVC", this.VV_AVC);
        json.put("VV_coronary", this.VV_coronary);
        json.put("VV_sepsis", this.VV_sepsis);
        json.put("VV_trauma", this.VV_trauma);
        json.put("VV_PCR", this.VV_PCR);
        return json;
    }

    @Override
    public ArrayList<Flag> update(@NonNull ProcedureProtocol updated) {
        ArrayList<Flag> flags = new ArrayList<>();
        if (this.id == null && updated.id != null) {
            this.id = updated.id;
            flags.add(Flag.UPDATED_PROTOCOL);
        }
        if (this.immobilization != updated.immobilization) {
            this.immobilization = updated.immobilization;
            flags.add(Flag.UPDATED_PROTOCOL);
        }
        if (this.TEPH != updated.TEPH) {
            this.TEPH = updated.TEPH;
            flags.add(Flag.UPDATED_PROTOCOL);
        }
        if (this.SIV != updated.SIV) {
            this.SIV = updated.SIV;
            flags.add(Flag.UPDATED_PROTOCOL);
        }
        if (this.VV_AVC != updated.VV_AVC) {
            this.VV_AVC = updated.VV_AVC;
            flags.add(Flag.UPDATED_PROTOCOL);
        }
        if (this.VV_coronary != updated.VV_coronary) {
            this.VV_coronary = updated.VV_coronary;
            flags.add(Flag.UPDATED_PROTOCOL);
        }
        if (this.VV_sepsis != updated.VV_sepsis) {
            this.VV_sepsis = updated.VV_sepsis;
            flags.add(Flag.UPDATED_PROTOCOL);
        }
        if (this.VV_trauma != updated.VV_trauma) {
            this.VV_trauma = updated.VV_trauma;
            flags.add(Flag.UPDATED_PROTOCOL);
        }
        if (this.VV_PCR != updated.VV_PCR) {
            this.VV_PCR = updated.VV_PCR;
            flags.add(Flag.UPDATED_PROTOCOL);
        }
        return flags;
    }

    public boolean getImmobilization() {
        return immobilization;
    }

    public void setImmobilization(boolean immobilization) {
        this.immobilization = immobilization;
    }

    public boolean getTEPH() {
        return TEPH;
    }

    public void setTEPH(boolean TEPH) {
        this.TEPH = TEPH;
    }

    public boolean getSIV() {
        return SIV;
    }

    public void setSIV(boolean SIV) {
        this.SIV = SIV;
    }

    public boolean getVV_AVC() {
        return VV_AVC;
    }

    public void setVV_AVC(boolean VV_AVC) {
        this.VV_AVC = VV_AVC;
    }

    public boolean getVV_coronary() {
        return VV_coronary;
    }

    public void setVV_coronary(boolean VV_coronary) {
        this.VV_coronary = VV_coronary;
    }

    public boolean getVV_sepsis() {
        return VV_sepsis;
    }

    public void setVV_sepsis(boolean VV_sepsis) {
        this.VV_sepsis = VV_sepsis;
    }

    public boolean getVV_trauma() {
        return VV_trauma;
    }

    public void setVV_trauma(boolean VV_trauma) {
        this.VV_trauma = VV_trauma;
    }

    public boolean getVV_PCR() {
        return VV_PCR;
    }

    public void setVV_PCR(boolean VV_PCR) {
        this.VV_PCR = VV_PCR;
    }
}
