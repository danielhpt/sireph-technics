package com.sireph.technics.models.procedures;

import com.sireph.technics.models._BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class ProcedureRCP extends _BaseModel {
    private boolean witnessed;
    private LocalDateTime SBV_DAE;
    private LocalDateTime SIV_SAV;
    private String first_rhythm;
    private int nr_shocks;
    private LocalDateTime recovery;
    private LocalDateTime downtime;
    private int mechanical_compressions;
    private boolean performed;

    public ProcedureRCP(JSONObject json) throws JSONException {
        super(json);
        this.witnessed = json.getBoolean("witnessed");
        this.SBV_DAE = LocalDateTime.parse(json.getString("SBV_DAE"));
        this.SIV_SAV = LocalDateTime.parse(json.getString("SIV_SAV"));
        this.first_rhythm = json.getString("first_rhythm");
        this.nr_shocks = json.getInt("nr_shocks");
        this.recovery = LocalDateTime.parse(json.getString("recovery"));
        this.downtime = LocalDateTime.parse(json.getString("downtime"));
        this.mechanical_compressions = json.getInt("mechanical_compressions");
        this.performed = json.getBoolean("performed");
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

    public boolean isWitnessed() {
        return witnessed;
    }

    public void setWitnessed(boolean witnessed) {
        this.witnessed = witnessed;
    }

    public LocalDateTime getSBV_DAE() {
        return SBV_DAE;
    }

    public void setSBV_DAE(LocalDateTime SBV_DAE) {
        this.SBV_DAE = SBV_DAE;
    }

    public LocalDateTime getSIV_SAV() {
        return SIV_SAV;
    }

    public void setSIV_SAV(LocalDateTime SIV_SAV) {
        this.SIV_SAV = SIV_SAV;
    }

    public String getFirst_rhythm() {
        return first_rhythm;
    }

    public void setFirst_rhythm(String first_rhythm) {
        this.first_rhythm = first_rhythm;
    }

    public int getNr_shocks() {
        return nr_shocks;
    }

    public void setNr_shocks(int nr_shocks) {
        this.nr_shocks = nr_shocks;
    }

    public LocalDateTime getRecovery() {
        return recovery;
    }

    public void setRecovery(LocalDateTime recovery) {
        this.recovery = recovery;
    }

    public LocalDateTime getDowntime() {
        return downtime;
    }

    public void setDowntime(LocalDateTime downtime) {
        this.downtime = downtime;
    }

    public int getMechanical_compressions() {
        return mechanical_compressions;
    }

    public void setMechanical_compressions(int mechanical_compressions) {
        this.mechanical_compressions = mechanical_compressions;
    }

    public boolean isPerformed() {
        return performed;
    }

    public void setPerformed(boolean performed) {
        this.performed = performed;
    }
}
