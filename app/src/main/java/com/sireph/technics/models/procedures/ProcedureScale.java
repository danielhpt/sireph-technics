package com.sireph.technics.models.procedures;

import static com.sireph.technics.utils.ValueFromJson.intFromJson;

import com.sireph.technics.models._BaseModel;
import com.sireph.technics.utils.statics.Flag;
import com.sireph.technics.utils.statics.TypeOfJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ProcedureScale extends _BaseModel<ProcedureScale> {
    private Integer cincinatti;
    private Integer PROACS;
    private Integer RTS;
    private Integer MGAP;
    private Integer RACE;

    public ProcedureScale(JSONObject json) {
        this.id = intFromJson(json, "victim", -1);
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
    public ArrayList<Flag> update(ProcedureScale updated) {
        ArrayList<Flag> flags = new ArrayList<>();
        if (this.id == null && updated.id != null) {
            this.id = updated.id;
            flags.add(Flag.UPDATED_SCALE);
        }
        if (!Objects.equals(this.cincinatti, updated.cincinatti)) {
            this.cincinatti = updated.cincinatti;
            flags.add(Flag.UPDATED_SCALE);
        }
        if (!Objects.equals(this.PROACS, updated.PROACS)) {
            this.PROACS = updated.PROACS;
            flags.add(Flag.UPDATED_SCALE);
        }
        if (!Objects.equals(this.RTS, updated.RTS)) {
            this.RTS = updated.RTS;
            flags.add(Flag.UPDATED_SCALE);
        }
        if (!Objects.equals(this.MGAP, updated.MGAP)) {
            this.MGAP = updated.MGAP;
            flags.add(Flag.UPDATED_SCALE);
        }
        if (!Objects.equals(this.RACE, updated.RACE)) {
            this.RACE = updated.RACE;
            flags.add(Flag.UPDATED_SCALE);
        }
        return flags;
    }

    @Override
    public JSONObject toJson(TypeOfJson type) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("cincinatti", this.cincinatti == null ? JSONObject.NULL : cincinatti);
        json.put("PROACS", this.PROACS == null ? JSONObject.NULL : PROACS);
        json.put("RTS", this.RTS == null ? JSONObject.NULL : RTS);
        json.put("MGAP", this.MGAP == null ? JSONObject.NULL : MGAP);
        json.put("RACE", this.RACE == null ? JSONObject.NULL : RACE);
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
