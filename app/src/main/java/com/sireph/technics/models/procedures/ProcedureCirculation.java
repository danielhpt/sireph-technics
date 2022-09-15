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

public class ProcedureCirculation extends _BaseModel<ProcedureCirculation> {
    private boolean temperature_monitoring;
    private boolean compression;
    private boolean tourniquet;
    private boolean pelvic_belt;
    private boolean venous_access;
    private boolean patch;
    private boolean ecg;

    public ProcedureCirculation(JSONObject json) {
        this.id = intFromJson(json, "victim", -1);
        this.temperature_monitoring = boolFromJson(json, "temperature_monitoring", false);
        this.compression = boolFromJson(json, "compression", false);
        this.tourniquet = boolFromJson(json, "tourniquet", false);
        this.pelvic_belt = boolFromJson(json, "pelvic_belt", false);
        this.venous_access = boolFromJson(json, "venous_access", false);
        this.patch = boolFromJson(json, "patch", false);
        this.ecg = boolFromJson(json, "ecg", false);
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
    public ArrayList<Flag> update(@NonNull ProcedureCirculation updated) {
        ArrayList<Flag> flags = new ArrayList<>();
        if (this.id == null && updated.id != null) {
            this.id = updated.id;
            flags.add(Flag.UPDATED_CIRCULATION);
        }
        if (this.temperature_monitoring != updated.temperature_monitoring) {
            this.temperature_monitoring = updated.temperature_monitoring;
            flags.add(Flag.UPDATED_CIRCULATION);
        }
        if (this.compression != updated.compression) {
            this.compression = updated.compression;
            flags.add(Flag.UPDATED_CIRCULATION);
        }
        if (this.tourniquet != updated.tourniquet) {
            this.tourniquet = updated.tourniquet;
            flags.add(Flag.UPDATED_CIRCULATION);
        }
        if (this.pelvic_belt != updated.pelvic_belt) {
            this.pelvic_belt = updated.pelvic_belt;
            flags.add(Flag.UPDATED_CIRCULATION);
        }
        if (this.venous_access != updated.venous_access) {
            this.venous_access = updated.venous_access;
            flags.add(Flag.UPDATED_CIRCULATION);
        }
        if (this.patch != updated.patch) {
            this.patch = updated.patch;
            flags.add(Flag.UPDATED_CIRCULATION);
        }
        if (this.ecg != updated.ecg) {
            this.ecg = updated.ecg;
            flags.add(Flag.UPDATED_CIRCULATION);
        }
        return flags;
    }

    @Override
    public JSONObject toJson(@NonNull TypeOfJson type) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("temperature_monitoring", this.temperature_monitoring);
        json.put("compression", this.compression);
        json.put("tourniquet", this.tourniquet);
        json.put("pelvic_belt", this.pelvic_belt);
        json.put("venous_access", this.venous_access);
        json.put("patch", this.patch);
        json.put("ecg", this.ecg);
        return json;
    }

    public boolean getTemperature_monitoring() {
        return temperature_monitoring;
    }

    public void setTemperature_monitoring(boolean temperature_monitoring) {
        this.temperature_monitoring = temperature_monitoring;
    }

    public boolean getCompression() {
        return compression;
    }

    public void setCompression(boolean compression) {
        this.compression = compression;
    }

    public boolean getTourniquet() {
        return tourniquet;
    }

    public void setTourniquet(boolean tourniquet) {
        this.tourniquet = tourniquet;
    }

    public boolean getPelvic_belt() {
        return pelvic_belt;
    }

    public void setPelvic_belt(boolean pelvic_belt) {
        this.pelvic_belt = pelvic_belt;
    }

    public boolean getVenous_access() {
        return venous_access;
    }

    public void setVenous_access(boolean venous_access) {
        this.venous_access = venous_access;
    }

    public boolean getPatch() {
        return patch;
    }

    public void setPatch(boolean patch) {
        this.patch = patch;
    }

    public boolean getEcg() {
        return ecg;
    }

    public void setEcg(boolean ecg) {
        this.ecg = ecg;
    }
}
