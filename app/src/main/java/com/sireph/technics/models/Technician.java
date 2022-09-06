package com.sireph.technics.models;

import static com.sireph.technics.utils.ValueFromJson.boolFromJson;

import org.json.JSONException;
import org.json.JSONObject;

public class Technician extends _BaseModel {
    private final Boolean active;
    private final User user;
    private final Central central;
    private Boolean team_leader;

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
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("active", this.active);
        json.put("team_leader", this.team_leader);
        json.put("user", this.user);
        json.put("central", this.central);
        return json;
    }

    public Boolean getTeam_leader() {
        if (team_leader == null){
            return false;
        }
        return team_leader;
    }

    public void setTeam_leader(Boolean team_leader) {
        this.team_leader = team_leader;
    }

    public User getUser() {
        return user;
    }

    public Central getCentral() {
        return central;
    }
}
