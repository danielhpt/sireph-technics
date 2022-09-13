package com.sireph.technics.models.procedures;

import static com.sireph.technics.utils.ValueFromJson.boolFromJson;
import static com.sireph.technics.utils.ValueFromJson.intFromJson;
import static com.sireph.technics.utils.ValueFromJson.stringFromJson;

import com.sireph.technics.models._BaseModel;
import com.sireph.technics.models.date.DateTime;
import com.sireph.technics.utils.statics.Flag;
import com.sireph.technics.utils.statics.TypeOfJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ProcedureRCP extends _BaseModel<ProcedureRCP> {
    private boolean witnessed;
    private DateTime SBV_DAE;
    private DateTime SIV_SAV;
    private String first_rhythm;
    private Integer nr_shocks;
    private DateTime recovery;
    private DateTime downtime;
    private boolean mechanical_compressions;
    private boolean performed;

    public ProcedureRCP(JSONObject json) {
        this.id = intFromJson(json, "victim", -1);
        this.witnessed = boolFromJson(json, "witnessed", false);
        this.SBV_DAE = DateTime.fromJson(json, "SBV_DAE");
        this.SIV_SAV = DateTime.fromJson(json, "SIV_SAV");
        this.first_rhythm = stringFromJson(json, "first_rhythm", "");
        this.nr_shocks = intFromJson(json, "nr_shocks", null);
        this.recovery = DateTime.fromJson(json, "recovery");
        this.downtime = DateTime.fromJson(json, "downtime");
        this.mechanical_compressions = boolFromJson(json, "mechanical_compressions", false);
        this.performed = boolFromJson(json, "performed", false);
    }

    public ProcedureRCP(boolean witnessed, DateTime SBV_DAE, DateTime SIV_SAV, String first_rhythm, Integer nr_shocks, DateTime recovery,
                        DateTime downtime, boolean mechanical_compressions, boolean performed) {
        this.witnessed = witnessed;
        this.SBV_DAE = SBV_DAE;
        this.SIV_SAV = SIV_SAV;
        this.first_rhythm = first_rhythm;
        this.nr_shocks = nr_shocks;
        this.recovery = recovery;
        this.downtime = downtime;
        this.mechanical_compressions = mechanical_compressions;
        this.performed = performed;
    }

    public ProcedureRCP() {
        this.witnessed = false;
        this.SBV_DAE = null;
        this.SIV_SAV = null;
        this.first_rhythm = "";
        this.nr_shocks = null;
        this.recovery = null;
        this.downtime = null;
        this.mechanical_compressions = false;
        this.performed = false;
    }

    @Override
    public ArrayList<Flag> update(ProcedureRCP updated) {
        ArrayList<Flag> flags = new ArrayList<>();
        if (this.id == null && updated.id != null) {
            this.id = updated.id;
            flags.add(Flag.UPDATED_RCP);
        }
        if (this.witnessed != updated.witnessed) {
            this.witnessed = updated.witnessed;
            flags.add(Flag.UPDATED_RCP);
        }
        if (!Objects.equals(this.SBV_DAE, updated.SBV_DAE)) {
            this.SBV_DAE = updated.SBV_DAE;
            flags.add(Flag.UPDATED_RCP);
        }
        if (!Objects.equals(this.SIV_SAV, updated.SIV_SAV)) {
            this.SIV_SAV = updated.SIV_SAV;
            flags.add(Flag.UPDATED_RCP);
        }
        if (!this.first_rhythm.equals(updated.first_rhythm)) {
            this.first_rhythm = updated.first_rhythm;
            flags.add(Flag.UPDATED_RCP);
        }
        if (!Objects.equals(this.nr_shocks, updated.nr_shocks)) {
            this.nr_shocks = updated.nr_shocks;
            flags.add(Flag.UPDATED_RCP);
        }
        if (!Objects.equals(this.recovery, updated.recovery)) {
            this.recovery = updated.recovery;
            flags.add(Flag.UPDATED_RCP);
        }
        if (!Objects.equals(this.downtime, updated.downtime)) {
            this.downtime = updated.downtime;
            flags.add(Flag.UPDATED_RCP);
        }
        if (this.mechanical_compressions != updated.mechanical_compressions) {
            this.mechanical_compressions = updated.mechanical_compressions;
            flags.add(Flag.UPDATED_RCP);
        }
        if (this.performed != updated.performed) {
            this.performed = updated.performed;
            flags.add(Flag.UPDATED_RCP);
        }
        return flags;
    }

    @Override
    public JSONObject toJson(TypeOfJson type) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("witnessed", this.witnessed);
        json.put("mechanical_compressions", this.mechanical_compressions);
        json.put("performed", this.performed);

        json.put("nr_shocks", this.nr_shocks == null ? JSONObject.NULL : nr_shocks);

        json.put("first_rhythm", Objects.equals(first_rhythm, "") ? JSONObject.NULL : first_rhythm);

        json.put("SBV_DAE", this.SBV_DAE == null ? JSONObject.NULL : SBV_DAE.toString());
        json.put("SIV_SAV", this.SIV_SAV == null ? JSONObject.NULL : SIV_SAV.toString());
        json.put("recovery", this.recovery == null ? JSONObject.NULL : recovery.toString());
        json.put("downtime", this.downtime == null ? JSONObject.NULL : downtime.toString());
        return json;
    }

    public boolean getWitnessed() {
        return witnessed;
    }

    public void setWitnessed(boolean witnessed) {
        this.witnessed = witnessed;
    }

    public DateTime getSBV_DAE() {
        return SBV_DAE;
    }

    public void setSBV_DAE(DateTime SBV_DAE) {
        this.SBV_DAE = SBV_DAE;
    }

    public DateTime getSIV_SAV() {
        return SIV_SAV;
    }

    public void setSIV_SAV(DateTime SIV_SAV) {
        this.SIV_SAV = SIV_SAV;
    }

    public String getFirst_rhythm() {
        return first_rhythm;
    }

    public void setFirst_rhythm(String first_rhythm) {
        this.first_rhythm = first_rhythm;
    }

    public Integer getNr_shocks() {
        return nr_shocks;
    }

    public void setNr_shocks(Integer nr_shocks) {
        this.nr_shocks = nr_shocks;
    }

    public DateTime getRecovery() {
        return recovery;
    }

    public void setRecovery(DateTime recovery) {
        this.recovery = recovery;
    }

    public DateTime getDowntime() {
        return downtime;
    }

    public void setDowntime(DateTime downtime) {
        this.downtime = downtime;
    }

    public boolean getMechanical_compressions() {
        return mechanical_compressions;
    }

    public void setMechanical_compressions(boolean mechanical_compressions) {
        this.mechanical_compressions = mechanical_compressions;
    }

    public boolean getPerformed() {
        return performed;
    }

    public void setPerformed(boolean performed) {
        this.performed = performed;
    }
}
