package com.sireph.technics.models;

import static com.sireph.technics.utils.ValueFromJson.boolFromJson;

import androidx.annotation.NonNull;

import com.sireph.technics.utils.statics.Flag;
import com.sireph.technics.utils.statics.TypeOfJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Technician extends _BaseModel<Technician> {
    private boolean active;
    private final User user;
    private final Central central;
    private boolean team_leader;

    public Technician(JSONObject json) throws JSONException {
        super(json);
        this.user = new User((json.getJSONObject("user")));
        this.active = boolFromJson(json, "active", false);
        this.team_leader = boolFromJson(json, "team_leader", false);
        if (json.isNull("central")) {
            this.central = null;
        } else {
            this.central = new Central(json.getJSONObject("central"));
        }
    }

    public Technician(JSONObject json, Central central) throws JSONException {
        super(json);
        this.user = new User((json.getJSONObject("user")));
        this.active = boolFromJson(json, "active", false);
        this.team_leader = boolFromJson(json, "team_leader", false);
        this.central = central;
    }

    @Override
    public ArrayList<Flag> update(Technician updated) {
        return null;
    }

    @Override
    public JSONObject toJson(TypeOfJson type) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("active", this.active);
        json.put("team_leader", this.team_leader);
        json.put("user", this.user.toJson(TypeOfJson.NORMAL));
        json.put("central", this.central == null ? JSONObject.NULL : central.toJson(type));
        return json;
    }

    public boolean getTeam_leader() {
        return team_leader;
    }

    public void setTeam_leader(boolean team_leader) {
        this.team_leader = team_leader;
    }

    public User getUser() {
        return user;
    }

    public Central getCentral() {
        return central;
    }

    @NonNull
    @Override
    public String toString() {
        return this.user.getUsername();
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
