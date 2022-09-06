package com.sireph.technics.models.procedures;

import static com.sireph.technics.utils.ValueFromJson.boolFromJson;

import com.sireph.technics.models._BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ProcedureCirculation extends _BaseModel {
    private Boolean temperature_monitoring;
    private Boolean compression;
    private Boolean tourniquet;
    private Boolean pelvic_belt;
    private Boolean venous_access;
    private Boolean patch;
    private Boolean ecg;

    public ProcedureCirculation(JSONObject json) {
        super(json);
        this.temperature_monitoring = boolFromJson(json, "temperature_monitoring", false);
        this.compression = boolFromJson(json, "compression", false);
        this.tourniquet = boolFromJson(json, "tourniquet", false);
        this.pelvic_belt = boolFromJson(json, "pelvic_belt", false);
        this.venous_access = boolFromJson(json, "venous_access", false);
        this.patch = boolFromJson(json, "patch", false);
        this.ecg = boolFromJson(json, "ecg", false);
    }

    public ProcedureCirculation(Boolean temperature_monitoring, Boolean compression, Boolean tourniquet, Boolean pelvic_belt, Boolean venous_access,
                                Boolean patch, Boolean ecg) {
        this.temperature_monitoring = temperature_monitoring;
        this.compression = compression;
        this.tourniquet = tourniquet;
        this.pelvic_belt = pelvic_belt;
        this.venous_access = venous_access;
        this.patch = patch;
        this.ecg = ecg;
    }

    public ProcedureCirculation() {
        this.temperature_monitoring = false;
        this.compression = false;
        this.tourniquet = false;
        this.pelvic_belt = false;
        this.venous_access = false;
        this.patch = false;
        this.ecg = false;
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

    public Boolean getTemperature_monitoring() {
        return temperature_monitoring;
    }

    public void setTemperature_monitoring(Boolean temperature_monitoring) {
        this.temperature_monitoring = temperature_monitoring;
    }

    public Boolean getCompression() {
        return compression;
    }

    public void setCompression(Boolean compression) {
        this.compression = compression;
    }

    public Boolean getTourniquet() {
        return tourniquet;
    }

    public void setTourniquet(Boolean tourniquet) {
        this.tourniquet = tourniquet;
    }

    public Boolean getPelvic_belt() {
        return pelvic_belt;
    }

    public void setPelvic_belt(Boolean pelvic_belt) {
        this.pelvic_belt = pelvic_belt;
    }

    public Boolean getVenous_access() {
        return venous_access;
    }

    public void setVenous_access(Boolean venous_access) {
        this.venous_access = venous_access;
    }

    public Boolean getPatch() {
        return patch;
    }

    public void setPatch(Boolean patch) {
        this.patch = patch;
    }

    public Boolean getEcg() {
        return ecg;
    }

    public void setEcg(Boolean ecg) {
        this.ecg = ecg;
    }
}
