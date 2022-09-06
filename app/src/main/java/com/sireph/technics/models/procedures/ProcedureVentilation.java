package com.sireph.technics.models.procedures;

import static com.sireph.technics.utils.ValueFromJson.boolFromJson;

import com.sireph.technics.models._BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ProcedureVentilation extends _BaseModel {
    private Boolean clearance;
    private Boolean oropharyngeal;
    private Boolean laryngeal_tube;
    private Boolean endotracheal;
    private Boolean laryngeal_mask;
    private Boolean mechanical_ventilation;
    private Boolean cpap;

    public ProcedureVentilation(JSONObject json) {
        super(json);
        this.clearance = boolFromJson(json, "clearance", false);
        this.oropharyngeal = boolFromJson(json, "oropharyngeal", false);
        this.laryngeal_tube = boolFromJson(json, "laryngeal_tube", false);
        this.endotracheal = boolFromJson(json, "endotracheal", false);
        this.laryngeal_mask = boolFromJson(json, "laryngeal_mask", false);
        this.mechanical_ventilation = boolFromJson(json, "mechanical_ventilation", false);
        this.cpap = boolFromJson(json, "cpap", false);
    }

    public ProcedureVentilation(Boolean clearance, Boolean oropharyngeal, Boolean laryngeal_tube, Boolean endotracheal, Boolean laryngeal_mask,
                                Boolean mechanical_ventilation, Boolean cpap) {
        this.clearance = clearance;
        this.oropharyngeal = oropharyngeal;
        this.laryngeal_tube = laryngeal_tube;
        this.endotracheal = endotracheal;
        this.laryngeal_mask = laryngeal_mask;
        this.mechanical_ventilation = mechanical_ventilation;
        this.cpap = cpap;
    }

    public ProcedureVentilation() {
        this.clearance = false;
        this.oropharyngeal = false;
        this.laryngeal_tube = false;
        this.endotracheal = false;
        this.laryngeal_mask = false;
        this.mechanical_ventilation = false;
        this.cpap = false;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("clearance", this.clearance);
        json.put("oropharyngeal", this.oropharyngeal);
        json.put("laryngeal_tube", this.laryngeal_tube);
        json.put("endotracheal", this.endotracheal);
        json.put("laryngeal_mask", this.laryngeal_mask);
        json.put("mechanical_ventilation", this.mechanical_ventilation);
        json.put("cpap", this.cpap);
        return json;
    }

    public Boolean getClearance() {
        return clearance;
    }

    public void setClearance(Boolean clearance) {
        this.clearance = clearance;
    }

    public Boolean getOropharyngeal() {
        return oropharyngeal;
    }

    public void setOropharyngeal(Boolean oropharyngeal) {
        this.oropharyngeal = oropharyngeal;
    }

    public Boolean getLaryngeal_tube() {
        return laryngeal_tube;
    }

    public void setLaryngeal_tube(Boolean laryngeal_tube) {
        this.laryngeal_tube = laryngeal_tube;
    }

    public Boolean getEndotracheal() {
        return endotracheal;
    }

    public void setEndotracheal(Boolean endotracheal) {
        this.endotracheal = endotracheal;
    }

    public Boolean getLaryngeal_mask() {
        return laryngeal_mask;
    }

    public void setLaryngeal_mask(Boolean laryngeal_mask) {
        this.laryngeal_mask = laryngeal_mask;
    }

    public Boolean getMechanical_ventilation() {
        return mechanical_ventilation;
    }

    public void setMechanical_ventilation(Boolean mechanical_ventilation) {
        this.mechanical_ventilation = mechanical_ventilation;
    }

    public Boolean getCpap() {
        return cpap;
    }

    public void setCpap(Boolean cpap) {
        this.cpap = cpap;
    }
}
