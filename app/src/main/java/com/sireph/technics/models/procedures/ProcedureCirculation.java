package com.sireph.technics.models.procedures;

import com.sireph.technics.models._BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ProcedureCirculation extends _BaseModel {
    private boolean temperature_monitoring;
    private boolean compression;
    private boolean tourniquet;
    private boolean pelvic_belt;
    private boolean venous_access;
    private boolean patch;
    private boolean ecg;

    public ProcedureCirculation(JSONObject json) throws JSONException {
        super(json);
        this.temperature_monitoring = json.getBoolean("temperature_monitoring");
        this.compression = json.getBoolean("compression");
        this.tourniquet = json.getBoolean("tourniquet");
        this.pelvic_belt = json.getBoolean("pelvic_belt");
        this.venous_access = json.getBoolean("venous_access");
        this.patch = json.getBoolean("patch");
        this.ecg = json.getBoolean("ecg");
    }

    public ProcedureCirculation(boolean temperature_monitoring, boolean compression, boolean tourniquet, boolean pelvic_belt, boolean venous_access,
                                boolean patch, boolean ecg) {
        this.temperature_monitoring = temperature_monitoring;
        this.compression = compression;
        this.tourniquet = tourniquet;
        this.pelvic_belt = pelvic_belt;
        this.venous_access = venous_access;
        this.patch = patch;
        this.ecg = ecg;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("temperature_monitoring", this.temperature_monitoring);
        json.put("compression", this.compression);
        json.put("tourniquet", this.tourniquet);
        json.put("pelvic_belt", this.pelvic_belt);
        json.put("venous_access", this.venous_access);
        json.put("patch", this.patch);
        json.put("ecg", this.ecg);
        return json;
    }

    public boolean isTemperature_monitoring() {
        return temperature_monitoring;
    }

    public void setTemperature_monitoring(boolean temperature_monitoring) {
        this.temperature_monitoring = temperature_monitoring;
    }

    public boolean isCompression() {
        return compression;
    }

    public void setCompression(boolean compression) {
        this.compression = compression;
    }

    public boolean isTourniquet() {
        return tourniquet;
    }

    public void setTourniquet(boolean tourniquet) {
        this.tourniquet = tourniquet;
    }

    public boolean isPelvic_belt() {
        return pelvic_belt;
    }

    public void setPelvic_belt(boolean pelvic_belt) {
        this.pelvic_belt = pelvic_belt;
    }

    public boolean isVenous_access() {
        return venous_access;
    }

    public void setVenous_access(boolean venous_access) {
        this.venous_access = venous_access;
    }

    public boolean isPatch() {
        return patch;
    }

    public void setPatch(boolean patch) {
        this.patch = patch;
    }

    public boolean isEcg() {
        return ecg;
    }

    public void setEcg(boolean ecg) {
        this.ecg = ecg;
    }
}
