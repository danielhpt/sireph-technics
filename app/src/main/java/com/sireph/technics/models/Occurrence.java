package com.sireph.technics.models;

import static com.sireph.technics.utils.ValueFromJson.intFromJson;
import static com.sireph.technics.utils.ValueFromJson.stringFromJson;

import androidx.annotation.NonNull;

import com.sireph.technics.models.date.DateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Occurrence extends _BaseModel {
    private final Team team;
    private final Central central;
    private int occurrence_number;
    private String local;
    private String gps_coordinates;
    private String parish;  //freguesia
    private String municipality;
    private boolean alert_mode;
    private DateTime created_on;
    private List<OccurrenceState> states;
    private List<Victim> victims;
    private String entity;
    private String mean_of_assistance;
    private String motive;
    private int number_of_victims;
    private boolean active;

    public Occurrence(JSONObject json, Team team, Technician technician) throws JSONException {
        super(json);

        this.occurrence_number = intFromJson(json, "occurrence_number", null);
        this.motive = stringFromJson(json, "motive", "");
        this.number_of_victims = intFromJson(json, "number_of_victims", null);
        this.local = stringFromJson(json, "local", "");
        this.entity = stringFromJson(json, "entity", "");
        this.mean_of_assistance = stringFromJson(json, "mean_of_assistance", "");
        this.gps_coordinates = stringFromJson(json, "gps_coordinates", "");
        this.parish = stringFromJson(json, "parish", "");
        this.municipality = stringFromJson(json, "municipality", "");
        this.active = json.optBoolean("active", false);
        this.alert_mode = json.optBoolean("alert_mode", false);
        this.created_on = DateTime.fromJson(json, "created_on");

        this.states = new ArrayList<>();
        try {
            JSONArray states = json.getJSONArray("states");
            for (int i = 0; i < states.length(); i++) {
                this.states.add(new OccurrenceState(states.getJSONObject(i)));
            }
        } catch (JSONException e) {
            this.states = new ArrayList<>();
        }

        this.victims = new ArrayList<>();
        try {
            JSONArray victims = json.getJSONArray("victims");
            for (int i = 0; i < victims.length(); i++) {
                this.victims.add(new Victim(victims.getJSONObject(i)));
            }
        } catch (JSONException e) {
            this.victims = new ArrayList<>();
        }

        if (team != null && team.getId() == json.getJSONObject("team").getInt("id")) {
            this.team = team;
        } else {
            this.team = new Team(json.getJSONObject("team"), technician);
        }
        if (this.team.getCentral().getId() == json.getJSONObject("central").getInt("id")) {
            this.central = this.team.getCentral();
        } else {
            this.central = new Central(json.getJSONObject("central"));
        }
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("occurrence_number", this.occurrence_number);
        json.put("entity", this.entity);
        json.put("mean_of_assistance", this.mean_of_assistance);
        json.put("motive", this.motive);
        json.put("number_of_victims", this.number_of_victims);
        json.put("local", this.local);
        json.put("gps_coordinates", this.gps_coordinates);
        json.put("parish", this.parish);
        json.put("municipality", this.municipality);
        json.put("active", this.active);
        json.put("alert_mode", this.alert_mode);
        json.put("created_on", this.created_on.toString());

        json.put("team", this.team.toJson());
        json.put("central", this.central.toJson());

        JSONArray states = new JSONArray();
        for (int i = 0; i < this.states.size(); i++) {
            states.put(this.states.get(i).toJson());
        }
        json.put("states", states);
        JSONArray victims = new JSONArray();
        for (int i = 0; i < this.victims.size(); i++) {
            victims.put(this.victims.get(i).toJson());
        }
        json.put("victims", victims);
        return json;
    }

    public int getOccurrence_number() {
        return occurrence_number;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getMean_of_assistance() {
        return mean_of_assistance;
    }

    public void setMean_of_assistance(String mean_of_assistance) {
        this.mean_of_assistance = mean_of_assistance;
    }

    public String getMotive() {
        return motive;
    }

    public void setMotive(String motive) {
        this.motive = motive;
    }

    public int getNumber_of_victims() {
        return number_of_victims;
    }

    public void setNumber_of_victims(int number_of_victims) {
        this.number_of_victims = number_of_victims;
    }

    public String getLocal() {
        return local;
    }

    public String getParish() {
        return parish;
    }

    public String getMunicipality() {
        return municipality;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public DateTime getCreated_on() {
        return created_on;
    }

    public List<OccurrenceState> getStates() {
        return states;
    }

    public void addState(OccurrenceState state) {
        this.states.add(state);
    }

    public List<Victim> getVictims() {
        return victims;
    }

    public void addVictim(Victim victim) {
        this.victims.add(victim);
    }

    public Team getTeam() {
        return this.team;
    }
}
