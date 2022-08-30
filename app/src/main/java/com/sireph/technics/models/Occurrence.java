package com.sireph.technics.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Occurrence extends _BaseModel {
    private final int occurrence_number;
    private final String local;
    private final String gps_coordinates;
    private final String parish;  //freguesia
    private final String municipality;
    private final boolean alert_mode;
    private final LocalDateTime created_on;
    private final Team team;
    private final Central central;
    private final List<OccurrenceState> states;
    private final List<Victim> victims;
    private String entity;
    private String mean_of_assistance;
    private String motive;
    private int number_of_victims;
    private boolean active;

    public Occurrence(JSONObject json) throws JSONException {
        super(json);
        this.occurrence_number = json.getInt("occurrence_number");
        this.entity = json.getString("entity");
        this.mean_of_assistance = json.getString("mean_of_assistance");
        this.motive = json.getString("motive");
        this.number_of_victims = json.getInt("number_of_victims");
        this.local = json.getString("local");
        this.gps_coordinates = json.getString("gps_coordinates");
        this.parish = json.getString("parish");
        this.municipality = json.getString("municipality");
        this.active = json.getBoolean("active");
        this.alert_mode = json.getBoolean("alert_mode");
        this.created_on = LocalDateTime.parse(json.getString("created_on"));

        this.team = new Team(json.getJSONObject("team"));
        this.central = new Central(json.getJSONObject("central"));

        this.states = new ArrayList<>();
        for (int i = 0; i < json.getJSONArray("states").length(); i++) {
            this.states.add(new OccurrenceState(json.getJSONArray("states").getJSONObject(i)));
        }
        this.victims = new ArrayList<>();
        for (int i = 0; i < json.getJSONArray("victims").length(); i++) {
            this.victims.add(new Victim(json.getJSONArray("victims").getJSONObject(i)));
        }
    }

    public Occurrence(JSONObject json, Team team) throws JSONException {
        super(json);
        this.occurrence_number = json.getInt("occurrence_number");
        this.entity = json.getString("entity");
        this.mean_of_assistance = json.getString("mean_of_assistance");
        this.motive = json.getString("motive");
        this.number_of_victims = json.getInt("number_of_victims");
        this.local = json.getString("local");
        this.gps_coordinates = json.getString("gps_coordinates");
        this.parish = json.getString("parish");
        this.municipality = json.getString("municipality");
        this.active = json.getBoolean("active");
        this.alert_mode = json.getBoolean("alert_mode");
        this.created_on = LocalDateTime.parse(json.getString("created_on"));

        this.team = team;
        if (this.team.getCentral().getId() == json.getJSONObject("central").getInt("id")) {
            this.central = this.team.getCentral();
        } else {
            this.central = new Central(json.getJSONObject("central"));
        }

        this.states = new ArrayList<>();
        for (int i = 0; i < json.getJSONArray("states").length(); i++) {
            this.states.add(new OccurrenceState(json.getJSONArray("states").getJSONObject(i)));
        }
        this.victims = new ArrayList<>();
        for (int i = 0; i < json.getJSONArray("victims").length(); i++) {
            this.victims.add(new Victim(json.getJSONArray("victims").getJSONObject(i)));
        }
    }

    public Occurrence(JSONObject json, Team team, Technician technician) throws JSONException {
        super(json);
        this.occurrence_number = json.getInt("occurrence_number");
        this.entity = json.getString("entity");
        this.mean_of_assistance = json.getString("mean_of_assistance");
        this.motive = json.getString("motive");
        this.number_of_victims = json.getInt("number_of_victims");
        this.local = json.getString("local");
        this.gps_coordinates = json.getString("gps_coordinates");
        this.parish = json.getString("parish");
        this.municipality = json.getString("municipality");
        this.active = json.getBoolean("active");
        this.alert_mode = json.getBoolean("alert_mode");
        this.created_on = LocalDateTime.parse(json.getString("created_on"));

        if (team.getId() == json.getJSONObject("team").getInt("id")) {
            this.team = team;
        } else {
            this.team = new Team(json.getJSONObject("team"), technician);
        }
        if (this.team.getCentral().getId() == json.getJSONObject("central").getInt("id")) {
            this.central = this.team.getCentral();
        } else {
            this.central = new Central(json.getJSONObject("central"));
        }

        this.states = new ArrayList<>();
        for (int i = 0; i < json.getJSONArray("states").length(); i++) {
            this.states.add(new OccurrenceState(json.getJSONArray("states").getJSONObject(i)));
        }
        this.victims = new ArrayList<>();
        for (int i = 0; i < json.getJSONArray("victims").length(); i++) {
            this.victims.add(new Victim(json.getJSONArray("victims").getJSONObject(i)));
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

    public LocalDateTime getCreated_on() {
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
}
