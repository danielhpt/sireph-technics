package com.sireph.technics.models.procedures;

import static com.sireph.technics.utils.ValueFromJson.boolFromJson;

import com.sireph.technics.models._BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ProcedureProtocol extends _BaseModel {
    private Boolean immobilization;
    private Boolean TEPH;
    private Boolean SIV;
    private Boolean VV_AVC;
    private Boolean VV_coronary;
    private Boolean VV_sepsis;
    private Boolean VV_trauma;
    private Boolean VV_PCR;

    public ProcedureProtocol(JSONObject json) {
        super(json);
        this.immobilization = boolFromJson(json, "immobilization", false);
        this.TEPH = boolFromJson(json, "TEPH", false);
        this.SIV = boolFromJson(json, "SIV", false);
        this.VV_AVC = boolFromJson(json, "VV_AVC", false);
        this.VV_coronary = boolFromJson(json, "VV_coronary", false);
        this.VV_sepsis = boolFromJson(json, "VV_sepsis", false);
        this.VV_trauma = boolFromJson(json, "VV_trauma", false);
        this.VV_PCR = boolFromJson(json, "VV_PCR", false);
    }

    public ProcedureProtocol(Boolean immobilization, Boolean TEPH, Boolean SIV, Boolean VV_AVC, Boolean VV_coronary, Boolean VV_sepsis,
                             Boolean VV_trauma, Boolean VV_PCR) {
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

    public Boolean getImmobilization() {
        return immobilization;
    }

    public void setImmobilization(Boolean immobilization) {
        this.immobilization = immobilization;
    }

    public Boolean getTEPH() {
        return TEPH;
    }

    public void setTEPH(Boolean TEPH) {
        this.TEPH = TEPH;
    }

    public Boolean getSIV() {
        return SIV;
    }

    public void setSIV(Boolean SIV) {
        this.SIV = SIV;
    }

    public Boolean getVV_AVC() {
        return VV_AVC;
    }

    public void setVV_AVC(Boolean VV_AVC) {
        this.VV_AVC = VV_AVC;
    }

    public Boolean getVV_coronary() {
        return VV_coronary;
    }

    public void setVV_coronary(Boolean VV_coronary) {
        this.VV_coronary = VV_coronary;
    }

    public Boolean getVV_sepsis() {
        return VV_sepsis;
    }

    public void setVV_sepsis(Boolean VV_sepsis) {
        this.VV_sepsis = VV_sepsis;
    }

    public Boolean getVV_trauma() {
        return VV_trauma;
    }

    public void setVV_trauma(Boolean VV_trauma) {
        this.VV_trauma = VV_trauma;
    }

    public Boolean getVV_PCR() {
        return VV_PCR;
    }

    public void setVV_PCR(Boolean VV_PCR) {
        this.VV_PCR = VV_PCR;
    }
}
