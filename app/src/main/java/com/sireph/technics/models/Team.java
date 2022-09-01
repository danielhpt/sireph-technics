package com.sireph.technics.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Team extends _BaseModel {
    private boolean active;
    private Central central;
    private List<Technician> technicians;

/*    public Team(JSONObject json) throws JSONException {
        super(json);
        this.active = json.optBoolean("active", false);
        this.central = new Central(json.getJSONObject("central"));
        this.technicians = new ArrayList<>();
        for (int i = 0; i < json.getJSONArray("technicians").length(); i++) {
            this.technicians.add(new Technician(json.getJSONArray("technicians").getJSONObject(i), this.central));
        }
    }*/

    public Team(JSONObject json, Technician technician) throws JSONException {
        super(json);
        this.active = json.optBoolean("active", false);
        this.central = technician.getCentral();
        this.technicians = new ArrayList<>();
        for (int i = 0; i < json.getJSONArray("technicians").length(); i++) {
            if (json.getJSONArray("technicians").getJSONObject(i).getInt("id") == technician.getId()) {
                technician.setTeam_leader(json.getJSONArray("technicians").getJSONObject(i).getBoolean("team_leader"));
                this.technicians.add(technician);
            } else {
                this.technicians.add(new Technician(json.getJSONArray("technicians").getJSONObject(i), this.central));
            }
        }
    }

    public Team(Central central, List<Technician> technicians) {
        this.active = true;
        this.central = central;
        this.technicians = technicians;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("active", this.active);
        json.put("central", this.central.toJson());
        JSONArray technicians = new JSONArray();
        for (int i = 0; i < this.technicians.size(); i++) {
            technicians.put(this.technicians.get(i).toJson());
        }
        json.put("technicians", technicians);
        return json;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Central getCentral() {
        return central;
    }

    public void setCentral(Central central) {
        this.central = central;
    }

    public List<Technician> getTechnicians() {
        return technicians;
    }

    public void setTechnicians(List<Technician> technicians) {
        this.technicians = technicians;
    }
}
