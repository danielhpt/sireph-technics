package com.sireph.technics.models.procedures;

import static com.sireph.technics.utils.ValueFromJson.intFromJson;

import com.sireph.technics.models._BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ProcedureScale extends _BaseModel {
    private Integer cincinatti;
    private Integer PROACS;
    private Integer RTS;
    private Integer MGAP;
    private Integer RACE;

    public ProcedureScale(JSONObject json) {
        super(json);
        this.cincinatti = intFromJson(json, "cincinatti", null);
        this.PROACS = intFromJson(json, "PROACS", null);
        this.RTS = intFromJson(json, "RTS", null);
        this.MGAP = intFromJson(json, "MGAP", null);
        this.RACE = intFromJson(json, "RACE", null);
    }

    public ProcedureScale(Integer cincinatti, Integer PROACS, Integer RTS, Integer MGAP, Integer RACE) {
        this.cincinatti = cincinatti;
        this.PROACS = PROACS;
        this.RTS = RTS;
        this.MGAP = MGAP;
        this.RACE = RACE;
    }

    public ProcedureScale() {
        this.cincinatti = null;
        this.PROACS = null;
        this.RTS = null;
        this.MGAP = null;
        this.RACE = null;
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

    public Integer getCincinatti() {
        return cincinatti;
    }

    public void setCincinatti(Integer cincinatti) {
        this.cincinatti = cincinatti;
    }

    public Integer getPROACS() {
        return PROACS;
    }

    public void setPROACS(Integer PROACS) {
        this.PROACS = PROACS;
    }

    public Integer getRTS() {
        return RTS;
    }

    public void setRTS(Integer RTS) {
        this.RTS = RTS;
    }

    public Integer getMGAP() {
        return MGAP;
    }

    public void setMGAP(Integer MGAP) {
        this.MGAP = MGAP;
    }

    public Integer getRACE() {
        return RACE;
    }

    public void setRACE(Integer RACE) {
        this.RACE = RACE;
    }
}
