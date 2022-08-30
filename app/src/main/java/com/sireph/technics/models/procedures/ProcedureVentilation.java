package com.sireph.technics.models.procedures;

import com.sireph.technics.models._BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ProcedureVentilation extends _BaseModel {
    private boolean clearance;
    private boolean oropharyngeal;
    private boolean laryngeal_tube;
    private boolean endotracheal;
    private boolean laryngeal_mask;
    private boolean mechanical_ventilation;
    private boolean cpap;

    public ProcedureVentilation(JSONObject json) throws JSONException {
        super(json);
        this.clearance = json.getBoolean("clearance");
        this.oropharyngeal = json.getBoolean("oropharyngeal");
        this.laryngeal_tube = json.getBoolean("laryngeal_tube");
        this.endotracheal = json.getBoolean("endotracheal");
        this.laryngeal_mask = json.getBoolean("laryngeal_mask");
        this.mechanical_ventilation = json.getBoolean("mechanical_ventilation");
        this.cpap = json.getBoolean("cpap");
    }

    public ProcedureVentilation(boolean clearance, boolean oropharyngeal, boolean laryngeal_tube, boolean endotracheal, boolean laryngeal_mask,
                                boolean mechanical_ventilation, boolean cpap) {
        this.clearance = clearance;
        this.oropharyngeal = oropharyngeal;
        this.laryngeal_tube = laryngeal_tube;
        this.endotracheal = endotracheal;
        this.laryngeal_mask = laryngeal_mask;
        this.mechanical_ventilation = mechanical_ventilation;
        this.cpap = cpap;
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

    public boolean isClearance() {
        return clearance;
    }

    public void setClearance(boolean clearance) {
        this.clearance = clearance;
    }

    public boolean isOropharyngeal() {
        return oropharyngeal;
    }

    public void setOropharyngeal(boolean oropharyngeal) {
        this.oropharyngeal = oropharyngeal;
    }

    public boolean isLaryngeal_tube() {
        return laryngeal_tube;
    }

    public void setLaryngeal_tube(boolean laryngeal_tube) {
        this.laryngeal_tube = laryngeal_tube;
    }

    public boolean isEndotracheal() {
        return endotracheal;
    }

    public void setEndotracheal(boolean endotracheal) {
        this.endotracheal = endotracheal;
    }

    public boolean isLaryngeal_mask() {
        return laryngeal_mask;
    }

    public void setLaryngeal_mask(boolean laryngeal_mask) {
        this.laryngeal_mask = laryngeal_mask;
    }

    public boolean isMechanical_ventilation() {
        return mechanical_ventilation;
    }

    public void setMechanical_ventilation(boolean mechanical_ventilation) {
        this.mechanical_ventilation = mechanical_ventilation;
    }

    public boolean isCpap() {
        return cpap;
    }

    public void setCpap(boolean cpap) {
        this.cpap = cpap;
    }
}
