package com.sireph.technics.models.procedures;

import com.sireph.technics.models._BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ProcedureProtocol extends _BaseModel {
    private boolean immobilization;
    private boolean TEPH;
    private boolean SIV;
    private boolean VV_AVC;
    private boolean VV_coronary;
    private boolean VV_sepsis;
    private boolean VV_trauma;
    private boolean VV_PCR;

    public ProcedureProtocol(JSONObject json) throws JSONException {
        super(json);
        this.immobilization = json.getBoolean("immobilization");
        this.TEPH = json.getBoolean("TEPH");
        this.SIV = json.getBoolean("SIV");
        this.VV_AVC = json.getBoolean("VV_AVC");
        this.VV_coronary = json.getBoolean("VV_coronary");
        this.VV_sepsis = json.getBoolean("VV_sepsis");
        this.VV_trauma = json.getBoolean("VV_trauma");
        this.VV_PCR = json.getBoolean("VV_PCR");
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
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
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

    public boolean isImmobilization() {
        return immobilization;
    }

    public void setImmobilization(boolean immobilization) {
        this.immobilization = immobilization;
    }

    public boolean isTEPH() {
        return TEPH;
    }

    public void setTEPH(boolean TEPH) {
        this.TEPH = TEPH;
    }

    public boolean isSIV() {
        return SIV;
    }

    public void setSIV(boolean SIV) {
        this.SIV = SIV;
    }

    public boolean isVV_AVC() {
        return VV_AVC;
    }

    public void setVV_AVC(boolean VV_AVC) {
        this.VV_AVC = VV_AVC;
    }

    public boolean isVV_coronary() {
        return VV_coronary;
    }

    public void setVV_coronary(boolean VV_coronary) {
        this.VV_coronary = VV_coronary;
    }

    public boolean isVV_sepsis() {
        return VV_sepsis;
    }

    public void setVV_sepsis(boolean VV_sepsis) {
        this.VV_sepsis = VV_sepsis;
    }

    public boolean isVV_trauma() {
        return VV_trauma;
    }

    public void setVV_trauma(boolean VV_trauma) {
        this.VV_trauma = VV_trauma;
    }

    public boolean isVV_PCR() {
        return VV_PCR;
    }

    public void setVV_PCR(boolean VV_PCR) {
        this.VV_PCR = VV_PCR;
    }
}
