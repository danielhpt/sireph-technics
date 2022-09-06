package com.sireph.technics.models.procedures;

import static com.sireph.technics.utils.ValueFromJson.boolFromJson;
import static com.sireph.technics.utils.ValueFromJson.intFromJson;
import static com.sireph.technics.utils.ValueFromJson.stringFromJson;

import com.sireph.technics.models._BaseModel;
import com.sireph.technics.models.date.DateTime;

import org.json.JSONException;
import org.json.JSONObject;

public class ProcedureRCP extends _BaseModel {
    private Boolean witnessed;
    private DateTime SBV_DAE;
    private DateTime SIV_SAV;
    private String first_rhythm;
    private Integer nr_shocks;
    private DateTime recovery;
    private DateTime downtime;
    private Integer mechanical_compressions;
    private Boolean performed;

    public ProcedureRCP(JSONObject json) {
        super(json);
        this.witnessed = boolFromJson(json, "witnessed", false);
        this.SBV_DAE = DateTime.fromJson(json, "SBV_DAE");
        this.SIV_SAV = DateTime.fromJson(json, "SIV_SAV");
        this.first_rhythm = stringFromJson(json, "first_rhythm", "");
        this.nr_shocks = intFromJson(json, "nr_shocks", null);
        this.recovery = DateTime.fromJson(json, "recovery");
        this.downtime = DateTime.fromJson(json, "downtime");
        this.mechanical_compressions = intFromJson(json, "mechanical_compressions", null);
        this.performed = boolFromJson(json, "performed", false);
    }

    public ProcedureRCP(Boolean witnessed, DateTime SBV_DAE, DateTime SIV_SAV, String first_rhythm, Integer nr_shocks, DateTime recovery,
                        DateTime downtime, Integer mechanical_compressions, Boolean performed) {
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
        this.mechanical_compressions = null;
        this.performed = false;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("witnessed", this.witnessed);
        json.put("SBV_DAE", this.SBV_DAE.toString());
        json.put("SIV_SAV", this.SIV_SAV.toString());
        json.put("first_rhythm", this.first_rhythm);
        json.put("nr_shocks", this.nr_shocks);
        json.put("recovery", this.recovery.toString());
        json.put("downtime", this.downtime.toString());
        json.put("mechanical_compressions", this.mechanical_compressions);
        json.put("performed", this.performed);
        return json;
    }

    public Boolean getWitnessed() {
        return witnessed;
    }

    public void setWitnessed(Boolean witnessed) {
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

    public Integer getMechanical_compressions() {
        return mechanical_compressions;
    }

    public void setMechanical_compressions(Integer mechanical_compressions) {
        this.mechanical_compressions = mechanical_compressions;
    }

    public Boolean getPerformed() {
        return performed;
    }

    public void setPerformed(Boolean performed) {
        this.performed = performed;
    }
}
