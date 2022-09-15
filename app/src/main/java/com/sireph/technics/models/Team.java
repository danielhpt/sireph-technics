package com.sireph.technics.models;

import static com.sireph.technics.utils.ValueFromJson.boolFromJson;

import androidx.annotation.NonNull;

import com.sireph.technics.utils.statics.Flag;
import com.sireph.technics.utils.statics.TypeOfJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Team extends _BaseModel<Team> {
    private boolean active;
    private Central central;
    private List<Technician> technicians;

/*
    public Team(JSONObject json) throws JSONException {
        super(json);
        this.active = boolFromJson(json, "active", false);
        this.central = new Central(json.getJSONObject("central"));
        this.technicians = new ArrayList<>();
        try {
            JSONArray technicians = json.getJSONArray("technicians");
            for (int i = 0; i < technicians.length(); i++) {
                this.technicians.add(new Technician(technicians.getJSONObject(i)));
            }
        } catch (JSONException e) {
            this.technicians = new ArrayList<>();
        }
    }
*/

    public Team(JSONObject json, Technician technician) {
        super(json);
        this.active = boolFromJson(json, "active", false);
        this.central = technician.getCentral();

        this.technicians = new ArrayList<>();
        try {
            JSONArray technicians = json.getJSONArray("technicians");
            for (int i = 0; i < technicians.length(); i++) {
                JSONObject object = technicians.getJSONObject(i);
                if (object.getInt("id") == technician.getId()) {
                    technician.setTeam_leader(boolFromJson(object, "team_leader", false));
                    this.technicians.add(technician);
                } else {
                    this.technicians.add(new Technician(object));
                }
            }
        } catch (JSONException e) {
            this.technicians = new ArrayList<>();
        }
    }

    public Team(Central central, List<Technician> technicians) {
        this.active = true;
        this.central = central;
        this.technicians = technicians;
    }

    @Override
    public JSONObject toJson(@NonNull TypeOfJson type) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("active", this.active);
        json.put("central", this.central.toJson(TypeOfJson.NORMAL));
        JSONArray technicians = new JSONArray();
        for (int i = 0; i < this.technicians.size(); i++) {
            technicians.put(this.technicians.get(i).toJson(TypeOfJson.NORMAL));
        }
        json.put("technicians", technicians);
        return json;
    }

    @Override
    public ArrayList<Flag> update(@NonNull Team updated) {
        return null;
    }

    public boolean getActive() {
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
