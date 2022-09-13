package com.sireph.technics.models.procedures;

import static com.sireph.technics.utils.ValueFromJson.boolFromJson;
import static com.sireph.technics.utils.ValueFromJson.intFromJson;

import com.sireph.technics.models._BaseModel;
import com.sireph.technics.utils.statics.Flag;
import com.sireph.technics.utils.statics.TypeOfJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProcedureVentilation extends _BaseModel<ProcedureVentilation> {
    private boolean clearance;
    private boolean oropharyngeal;
    private boolean laryngeal_tube;
    private boolean endotracheal;
    private boolean laryngeal_mask;
    private boolean mechanical_ventilation;
    private boolean cpap;

    public ProcedureVentilation(JSONObject json) {
        this.id = intFromJson(json, "victim", -1);
        this.clearance = boolFromJson(json, "clearance", false);
        this.oropharyngeal = boolFromJson(json, "oropharyngeal", false);
        this.laryngeal_tube = boolFromJson(json, "laryngeal_tube", false);
        this.endotracheal = boolFromJson(json, "endotracheal", false);
        this.laryngeal_mask = boolFromJson(json, "laryngeal_mask", false);
        this.mechanical_ventilation = boolFromJson(json, "mechanical_ventilation", false);
        this.cpap = boolFromJson(json, "cpap", false);
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
    public ArrayList<Flag> update(ProcedureVentilation updated) {
        ArrayList<Flag> flags = new ArrayList<>();
        if (this.id == null && updated.id != null) {
            this.id = updated.id;
            flags.add(Flag.UPDATED_VENTILATION);
        }
        if (this.clearance != updated.clearance) {
            this.clearance = updated.clearance;
            flags.add(Flag.UPDATED_VENTILATION);
        }
        if (this.oropharyngeal != updated.oropharyngeal) {
            this.oropharyngeal = updated.oropharyngeal;
            flags.add(Flag.UPDATED_VENTILATION);
        }
        if (this.laryngeal_tube != updated.laryngeal_tube) {
            this.laryngeal_tube = updated.laryngeal_tube;
            flags.add(Flag.UPDATED_VENTILATION);
        }
        if (this.endotracheal != updated.endotracheal) {
            this.endotracheal = updated.endotracheal;
            flags.add(Flag.UPDATED_VENTILATION);
        }
        if (this.laryngeal_mask != updated.laryngeal_mask) {
            this.laryngeal_mask = updated.laryngeal_mask;
            flags.add(Flag.UPDATED_VENTILATION);
        }
        if (this.mechanical_ventilation != updated.mechanical_ventilation) {
            this.mechanical_ventilation = updated.mechanical_ventilation;
            flags.add(Flag.UPDATED_VENTILATION);
        }
        if (this.cpap != updated.cpap) {
            this.cpap = updated.cpap;
            flags.add(Flag.UPDATED_VENTILATION);
        }
        return flags;
    }

    @Override
    public JSONObject toJson(TypeOfJson type) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("clearance", this.clearance);
        json.put("oropharyngeal", this.oropharyngeal);
        json.put("laryngeal_tube", this.laryngeal_tube);
        json.put("endotracheal", this.endotracheal);
        json.put("laryngeal_mask", this.laryngeal_mask);
        json.put("mechanical_ventilation", this.mechanical_ventilation);
        json.put("cpap", this.cpap);
        return json;
    }

    public boolean getClearance() {
        return clearance;
    }

    public void setClearance(boolean clearance) {
        this.clearance = clearance;
    }

    public boolean getOropharyngeal() {
        return oropharyngeal;
    }

    public void setOropharyngeal(boolean oropharyngeal) {
        this.oropharyngeal = oropharyngeal;
    }

    public boolean getLaryngeal_tube() {
        return laryngeal_tube;
    }

    public void setLaryngeal_tube(boolean laryngeal_tube) {
        this.laryngeal_tube = laryngeal_tube;
    }

    public boolean getEndotracheal() {
        return endotracheal;
    }

    public void setEndotracheal(boolean endotracheal) {
        this.endotracheal = endotracheal;
    }

    public boolean getLaryngeal_mask() {
        return laryngeal_mask;
    }

    public void setLaryngeal_mask(boolean laryngeal_mask) {
        this.laryngeal_mask = laryngeal_mask;
    }

    public boolean getMechanical_ventilation() {
        return mechanical_ventilation;
    }

    public void setMechanical_ventilation(boolean mechanical_ventilation) {
        this.mechanical_ventilation = mechanical_ventilation;
    }

    public boolean getCpap() {
        return cpap;
    }

    public void setCpap(boolean cpap) {
        this.cpap = cpap;
    }
}
