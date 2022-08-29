package com.sireph.technics.models.procedures;

import com.sireph.technics.models._BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ProcedureScale extends _BaseModel {
    private int cincinatti;
    private int PROACS;
    private int RTS;
    private int MGAP;
    private int RACE;

    public ProcedureScale(JSONObject json) throws JSONException {
        super(json);
        this.cincinatti = json.getInt("cincinatti");
        this.PROACS = json.getInt("PROACS");
        this.RTS = json.getInt("RTS");
        this.MGAP = json.getInt("MGAP");
        this.RACE = json.getInt("RACE");
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("cincinatti", this.cincinatti);
        json.put("PROACS", this.PROACS);
        json.put("RTS", this.RTS);
        json.put("MGAP", this.MGAP);
        json.put("RACE", this.RACE);
        return json;
    }

    public int getCincinatti() {
        return cincinatti;
    }

    public void setCincinatti(int cincinatti) {
        this.cincinatti = cincinatti;
    }

    public int getPROACS() {
        return PROACS;
    }

    public void setPROACS(int PROACS) {
        this.PROACS = PROACS;
    }

    public int getRTS() {
        return RTS;
    }

    public void setRTS(int RTS) {
        this.RTS = RTS;
    }

    public int getMGAP() {
        return MGAP;
    }

    public void setMGAP(int MGAP) {
        this.MGAP = MGAP;
    }

    public int getRACE() {
        return RACE;
    }

    public void setRACE(int RACE) {
        this.RACE = RACE;
    }
}